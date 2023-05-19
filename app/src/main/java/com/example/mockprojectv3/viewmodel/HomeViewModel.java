package com.example.mockprojectv3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mockprojectv3.model.MovieModel;
import com.example.mockprojectv3.repositories.MovieRepository;
import com.example.mockprojectv3.repositories.MovieRepositoryImplement;
import com.example.mockprojectv3.repositories.Resource;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MovieRepository movieRepository;
    private String mQuery;
    private int mPageNumber;
    private int mPageNumberSearchMovies;
    private final LiveData<Resource<List<MovieModel>>> mSearchMovies;
    private final LiveData<Resource<List<MovieModel>>> mTrendingMovies;
    private final LiveData<Resource<List<MovieModel>>> mPopularMovies;

    public HomeViewModel() {
        movieRepository = new MovieRepositoryImplement();
        mSearchMovies = movieRepository.getSearchMovies();
        mTrendingMovies = movieRepository.getTrendingMovies();
        mPopularMovies = movieRepository.getPopularMovies();
    }

    public LiveData<Resource<List<MovieModel>>> getSearchMovies() {
        return mSearchMovies;
    }

    public LiveData<Resource<List<MovieModel>>> getTrendingMovies() {
        return mTrendingMovies;
    }

    public LiveData<Resource<List<MovieModel>>> getPopularMovies() {
        return mPopularMovies;
    }

    public void loadMovies(int pageNumber) {
        mPageNumber = pageNumber;
        movieRepository.loadMoviesApi(pageNumber);
    }

    public void searchMovies(String query, int pageNumber) {
        mQuery = query;
        mPageNumberSearchMovies = pageNumber;
        movieRepository.searchMovies(query, pageNumber);
    }

    public void searchNextPage() {
        movieRepository.searchNextPage();
    }

    public void loadNextPage() {
        movieRepository.loadNextPage();
    }
}
