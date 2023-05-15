package com.example.mockprojectv3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mockprojectv3.model.MovieModel;
import com.example.mockprojectv3.repositories.MovieRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MovieRepository movieRepository;

    public HomeViewModel(){
        movieRepository = MovieRepository.getInstance();
    }
    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }

//-------------------------------3-----------------------------------------
    public void searchMovieApi(String query, int pageNumber){
        movieRepository.searchMovieApi(query, pageNumber);
    }
    public void searchNextPage(){
        movieRepository.searchNextPage();
    }
}
