package com.example.mockprojectv3.repositories;

import android.util.Log;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mockprojectv3.model.MovieModel;
import com.example.mockprojectv3.request.Servicey;
import com.example.mockprojectv3.response.MovieSearchResponse;
import com.example.mockprojectv3.utils.Credentials;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class MovieRepository {
    //Live Data
    private MutableLiveData<List<MovieModel>> mSearchMovies;
    private MutableLiveData<List<MovieModel>> mTrendingMovies;

    //Singleton
    private static MovieRepository instance;
    public static MovieRepository getInstance(){
        if(instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }

    private CompositeDisposable compositeDisposable;
    private RetrieveMovieRunnable retrieveMovieRunnable;
    public MovieRepository(){
        mSearchMovies = new MutableLiveData<>();
        mTrendingMovies = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
    }

    public LiveData<List<MovieModel>> getSearchMovies(){
        return mSearchMovies;
    }

    public LiveData<List<MovieModel>> getTrendingMovies(){
        return mTrendingMovies;
    }
    public void searchMoviesApi(String query, int pageNumber) {
        if (retrieveMovieRunnable != null) {
            retrieveMovieRunnable = null;
        }

        retrieveMovieRunnable = new RetrieveMovieRunnable(query, pageNumber);

        Single<Response<MovieSearchResponse>> moviesSearchSingle = retrieveMovieRunnable.getSearchMovies();
        Single<Response<MovieSearchResponse>> trendingMoviesSingle = retrieveMovieRunnable.getTrendingMovies();

        compositeDisposable.add(
                Flowable.zip(
                                moviesSearchSingle.toFlowable(),
                                trendingMoviesSingle.toFlowable(),
                                (moviesSearch, moviesTrending) -> {
                                    List<MovieModel> searchResults = new ArrayList<>();
                                    List<MovieModel> trendingResults = new ArrayList<>();
                                    if (moviesSearch.isSuccessful()) {
                                        searchResults.addAll(moviesSearch.body().getMovies());
                                    }
                                    if (moviesTrending.isSuccessful()) {
                                        trendingResults.addAll(moviesTrending.body().getMovies());
                                    }
                                    return new Pair<>(searchResults, trendingResults);
                                }
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.computation())
                        .subscribe(
                                pair -> {
                                    List<MovieModel> searchResults = pair.first;
                                    List<MovieModel> trendingResults = pair.second;
                                    for (MovieModel search: searchResults){
                                        Log.e("search", search.getTitle());
                                    }
                                    for (MovieModel trending: trendingResults){
                                        Log.e("trending", trending.getTitle());
                                    }
                                    mSearchMovies.postValue(searchResults);
                                    mTrendingMovies.postValue(trendingResults);
                                },
                                throwable -> {
                                    // Xử lý lỗi
                                    mSearchMovies.postValue(null);
                                    mTrendingMovies.postValue(null);
                                }
                        )
        );
    }

    private class RetrieveMovieRunnable {
        private String query;
        private int pageNumber;

        public RetrieveMovieRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
        }

        public Single<Response<MovieSearchResponse>> getSearchMovies() {
            return Servicey.getMovieApi().searchMovies(
                    Credentials.API_KEY,
                    query,
                    pageNumber
            );
        }

        public Single<Response<MovieSearchResponse>> getTrendingMovies() {
            return Servicey.getMovieApi().getTrendingMovies(
                    Credentials.API_KEY,
                    pageNumber
            );
        }
    }
}