package com.example.mockprojectv3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mockprojectv3.model.MovieModel;
import com.example.mockprojectv3.repositories.MovieRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    //RxJava
    private MovieRepository movieRepository;
    private String mQuery;
    private int mPageNumber;
    public HomeViewModel(){
        movieRepository = MovieRepository.getInstance();
    }
    public LiveData<List<MovieModel>> getSearchMovies(){
        return movieRepository.getSearchMovies();
    }
    public LiveData<List<MovieModel>> getTrendingMovies(){
        return movieRepository.getTrendingMovies();
    }
    public void searchMovieApi(String query, int pageNumber){
        mQuery = query;
        mPageNumber = pageNumber;
        movieRepository.searchMoviesApi(query, pageNumber);
    }
    public void searchNextPage(){
        movieRepository.searchMoviesApi(mQuery, mPageNumber + 1);
    }
    public void getTrendingMovies(String query, int pageNumber){
        mQuery = query;
        mPageNumber = pageNumber;
        movieRepository.searchMoviesApi(query, pageNumber);
    }
}
