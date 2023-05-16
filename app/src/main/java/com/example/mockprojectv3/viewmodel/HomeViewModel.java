package com.example.mockprojectv3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mockprojectv3.model.MovieModel;
import com.example.mockprojectv3.repositories.MovieRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MovieRepository movieRepository;
    private String mQuery;
    private int mPageNumber;
    private int mPageNumberSearchMovies;
    public HomeViewModel() {
        movieRepository = MovieRepository.getInstance();
    }
    public LiveData<List<MovieModel>> getSearchMovies() {
        return movieRepository.getSearchMovies();
    }
    public LiveData<List<MovieModel>> getTrendingMovies() {
        return movieRepository.getTrendingMovies();
    }
    public LiveData<List<MovieModel>> getPopularMovies() {
        return movieRepository.getPopularMovies();
    }
    public void loadMovies(int pageNumber) {
        mPageNumber = pageNumber;
        movieRepository.loadMoviesApi(pageNumber);
    }
    public void searchMovies(String query, int pageNumber){
        mQuery = query;
        mPageNumberSearchMovies = pageNumber;
        movieRepository.searchMovies(query, pageNumber);
    }

    public void searchNextPage(){
        movieRepository.searchNextPage();
    }
    public void loadNextPage() {
        movieRepository.loadNextPage();
    }
}
