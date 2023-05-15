package com.example.mockprojectv3.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mockprojectv3.model.MovieModel;
import com.example.mockprojectv3.request.MovieApiClient;

import java.util.List;

public class MovieRepository {
    private static MovieRepository instance;

    private MovieApiClient movieApiClient;




    public static MovieRepository getInstance(){
        if(instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }
    private MovieRepository(){
        movieApiClient = new MovieApiClient().getInstance();
    }

    public MovieApiClient getMovieApiClient() {
        return movieApiClient;
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies() ;
    }
//------------------------------------------2----------------------------------------
    public void searchMovieApi(String query, int pageMuber){
        movieApiClient.searchMoviesApi(query, pageMuber);
    }

}
