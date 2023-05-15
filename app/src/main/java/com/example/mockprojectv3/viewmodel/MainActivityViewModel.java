package com.example.mockprojectv3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mockprojectv3.model.MovieModel;
import com.example.mockprojectv3.repositories.MovieRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MovieRepository movieRepository;

    public MainActivityViewModel(){
        movieRepository = MovieRepository.getInstance();
    }
    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }

    public void searchMovieApi(String query, int pageNumber){
        movieRepository.searchMovieApi(query, pageNumber);
    }
}
