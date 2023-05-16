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
import java.util.Collection;
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
    private MutableLiveData<List<MovieModel>> mPopularMovies;
    private List<MovieModel> currentSearchResults;
    private List<MovieModel> currentPopular;
    private List<MovieModel> currentTrending;

    private String mQuery;
    private int mPageNumber;
    private int mPageNumberMovies;


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
    private RetrieveMovies retrieveMovies;
    public MovieRepository(){
        mSearchMovies = new MutableLiveData<>();
        mTrendingMovies = new MutableLiveData<>();
        mPopularMovies = new MutableLiveData<>();
        currentSearchResults = new ArrayList<>();
        currentPopular = new ArrayList<>();
        currentTrending = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
    }
    public LiveData<List<MovieModel>> getSearchMovies(){
        return mSearchMovies;
    }
    public LiveData<List<MovieModel>> getPopularMovies(){
        return mPopularMovies;
    }
    public LiveData<List<MovieModel>> getTrendingMovies(){
        return mTrendingMovies;
    }
    public void loadMoviesApi(int pageNumber) {
        mPageNumber = pageNumber;

        if (retrieveMovies != null) {
            retrieveMovies = null;
        }

        retrieveMovies = new RetrieveMovies(pageNumber);

        Single<Response<MovieSearchResponse>> trendingMoviesSingle = retrieveMovies.getTrendingMovies();
        Single<Response<MovieSearchResponse>> popularMoviesSingle = retrieveMovies.getPopularMovies();

        compositeDisposable.add(
                Flowable.zip(
                                popularMoviesSingle.toFlowable(),
                                trendingMoviesSingle.toFlowable(),
                                (moviesPopular, moviesTrending) -> {
                                    List<MovieModel> popularResults = new ArrayList<>();
                                    List<MovieModel> trendingResults = new ArrayList<>();
                                    if (moviesPopular.isSuccessful()) {
                                        popularResults.addAll(moviesPopular.body().getMovies());
                                    }
                                    if (moviesTrending.isSuccessful()) {
                                        trendingResults.addAll(moviesTrending.body().getMovies());
                                    }
                                    return new Pair<>(popularResults, trendingResults);
                                }
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.computation())
                        .subscribe(
                                pair -> {
                                    List<MovieModel> popularResults = pair.first;
                                    currentPopular.addAll(popularResults);
                                    List<MovieModel> trendingResults = pair.second;
                                    currentTrending.addAll(trendingResults);
                                    mPopularMovies.postValue(currentPopular);
                                    mTrendingMovies.postValue(currentTrending);
                                },
                                throwable -> {
                                    // Xử lý lỗi
                                    mPopularMovies.postValue(null);
                                    mTrendingMovies.postValue(null);
                                }
                        )
        );
    }

    public void searchMovies(String query, int pageNumber) {
        mPageNumber = pageNumber;
        mQuery = query;

        if (retrieveMovieRunnable != null) {
            retrieveMovieRunnable = null;
        }

        retrieveMovieRunnable = new RetrieveMovieRunnable(query, pageNumber);

        Single<Response<MovieSearchResponse>> searchMoviesSingle = retrieveMovieRunnable.getSearchMovies();
        compositeDisposable.add(
                searchMoviesSingle
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.computation())
                        .subscribe(
                                response -> {
                                    List<MovieModel> searchResults = new ArrayList<>();
                                    if (response.isSuccessful()) {
                                        currentSearchResults.addAll(response.body().getMovies());
                                        searchResults.addAll(currentSearchResults);
                                    }
                                    mSearchMovies.postValue(searchResults);
                                },
                                throwable -> {
                                    // Xử lý lỗi
                                    mSearchMovies.postValue(null);
                                }
                        )
        );


    }
    public void searchNextPage(){
        searchMovies(mQuery,mPageNumberMovies + 1);
    }
    public void loadNextPage(){
        loadMoviesApi(mPageNumber + 1);
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
    }
    private class RetrieveMovies {
        private int pageNumber;
        public RetrieveMovies(int pageNumber) {
            this.pageNumber = pageNumber;
        }
        public Single<Response<MovieSearchResponse>> getPopularMovies() {
            return Servicey.getMovieApi().getPopularMovies(
                    Credentials.API_KEY,
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