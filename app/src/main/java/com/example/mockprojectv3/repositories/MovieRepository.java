package com.example.mockprojectv3.repositories;

import androidx.lifecycle.LiveData;
import com.example.mockprojectv3.model.MovieModel;
import java.util.List;
public interface MovieRepository {
    LiveData<Resource<List<MovieModel>>> getSearchMovies();

    LiveData<Resource<List<MovieModel>>> getPopularMovies();

    LiveData<Resource<List<MovieModel>>> getTrendingMovies();

    void loadMoviesApi(int pageNumber);

    void searchMovies(String query, int pageNumber);

    void searchNextPage();

    void loadNextPage();
}
