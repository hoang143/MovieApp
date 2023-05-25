package com.example.mockprojectv3.repositories;

import androidx.lifecycle.LiveData;
import com.example.mockprojectv3.model.MovieModel;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface MovieRepository {
    LiveData<Resource<List<MovieModel>>> getSearchMovies();

    LiveData<Resource<List<MovieModel>>> getPopularMovies();

    LiveData<Resource<List<MovieModel>>> getTrendingMovies();

    LiveData<Resource<MovieModel>> getMovieByID();

    void getMovieByID(int movieID);

    void loadMoviesApi(int pageNumber);

    void searchMovies(String query, int pageNumber);

    void searchNextPage();

    void loadNextPage();

}
