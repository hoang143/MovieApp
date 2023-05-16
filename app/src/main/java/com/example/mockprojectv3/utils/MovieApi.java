package com.example.mockprojectv3.utils;

import com.example.mockprojectv3.model.MovieModel;
import com.example.mockprojectv3.response.MovieResponse;
import com.example.mockprojectv3.response.MovieSearchResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    // Search for movies
    @GET("/3/search/movie")
    Single<Response<MovieSearchResponse>> searchMovies(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    // Get Popular
    @GET("/3/movie/popular")
    Single<Response<MovieSearchResponse>> getPopularMovies(
            @Query("api_key") String key,
            @Query("page") int page
    );

    // Get Trending
    @GET("3/movie/top_rated")
    Single<Response<MovieSearchResponse>> getTrendingMovies(
            @Query("api_key") String key,
            @Query("page") int page
    );
}
