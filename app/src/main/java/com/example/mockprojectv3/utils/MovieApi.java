package com.example.mockprojectv3.utils;

import com.example.mockprojectv3.model.MovieModel;
import com.example.mockprojectv3.response.MovieResponse;
import com.example.mockprojectv3.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    // Search for movies
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    // Search for movies
    @GET("3/movie/{movie_id}?")
    Call<MovieModel> searchSingleMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key
    );
}
