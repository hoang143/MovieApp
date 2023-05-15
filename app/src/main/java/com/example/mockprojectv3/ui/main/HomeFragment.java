package com.example.mockprojectv3.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mockprojectv3.R;
import com.example.mockprojectv3.adapter.CategoryAdapter;
import com.example.mockprojectv3.adapter.OnPopularListener;
import com.example.mockprojectv3.adapter.PopularAdapter;
import com.example.mockprojectv3.model.Category;
import com.example.mockprojectv3.model.MovieModel;
import com.example.mockprojectv3.model.Popular;
import com.example.mockprojectv3.utils.MovieApi;
import com.example.mockprojectv3.request.Servicey;
import com.example.mockprojectv3.response.MovieSearchResponse;
import com.example.mockprojectv3.utils.Credentials;
import com.example.mockprojectv3.viewmodel.HomeViewModel;
import com.example.mockprojectv3.viewmodel.MainActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements OnPopularListener {
    private RecyclerView rcCategories;
    private RecyclerView rcPopular;
    private CategoryAdapter categoryAdapter;
    private PopularAdapter popularAdapter;
    private TextView tvTestApi;
    private SearchView searchView;
    private HomeViewModel homeViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home,container, false);
        tvTestApi = view.findViewById(R.id.tvWelcome);
        searchView = view.findViewById(R.id.svHome);
        
        //Set up searchview
        SetupSearchView();

//        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        //Categories
        rcCategories = view.findViewById(R.id.rcCategories);

        categoryAdapter = new CategoryAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL,false);
        rcCategories.setLayoutManager(linearLayoutManager);
        rcCategories.setNestedScrollingEnabled(false);

        categoryAdapter.setData(getListCategory());
        rcCategories.setAdapter(categoryAdapter);

        // Popular:
        rcPopular = view.findViewById(R.id.rcPopular);
        ConfigureRecyclerView();
        ObserveAnyChange();
//        searchMovieApi("Marvel", 1);
        tvTestApi.setOnClickListener(view1 -> GetRetrofitResponsePopularMovies());

        return view;
    }
    private void SetupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                homeViewModel.searchMovieApi(
                        query,
                        1
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void ObserveAnyChange(){
        homeViewModel.getMovies().observe(getViewLifecycleOwner(), new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observing for any data change
                if(movieModels != null){
                    for(MovieModel movieModel:movieModels){
                        Log.v("Sunday", "Movie: " + movieModel.getTitle());
                        popularAdapter.setData(movieModels);
                    }
                }
            }
        });
    }
    private void GetRetrofitResponse() {
        MovieApi movieApi = Servicey.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApi
                .searchMovie(
                        Credentials.API_KEY,
                        "Jack Reacher",
                        1
                );

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code() == 200){
                    Log.e("Hoang", "The response "+ response.body().toString() );

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for (MovieModel movie: movies) {
                        Log.v("Hoang", "the release date " + movie.getRelease_date());
                    }
                }

                else {
                    try {
                        Log.e("Error", "Error" + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });

    }
    private void GetRetrofitResponseAccordingToID(){
        MovieApi movieApi = Servicey.getMovieApi();
        Call<MovieModel> responseCall = movieApi
                .searchSingleMovie(
                        550,
                        Credentials.API_KEY);
       responseCall.enqueue(new Callback<MovieModel>() {
           @Override
           public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
               if(response.code() == 200){
                   Log.e("Hoang", "The response "+ response.body().getTitle());
               }

               else {
                   try {
                       Log.e("Error", "Error" + response.errorBody().string());
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
               }
           }

           @Override
           public void onFailure(Call<MovieModel> call, Throwable t) {

           }
       });
    }
    private void GetRetrofitResponsePopularMovies(){
        MovieApi movieApi = Servicey.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApi
                .getPopular(
                        Credentials.API_KEY,
                        1
                );
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                Log.e("Sunday", "The response "+ response.body().toString() );

                List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                for (MovieModel movie: movies) {
                    Log.v("Sunday", "the release date " + movie.getTitle());
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }
    private List<Popular> getListPopular() {
        List<Popular> lstPop = new ArrayList<>();
        lstPop.add(new Popular(R.drawable.profile_test, " phim 1"));
        lstPop.add(new Popular(R.drawable.profile_test, " phim 2"));
        lstPop.add(new Popular(R.drawable.profile_test, " phim 3"));
        lstPop.add(new Popular(R.drawable.profile_test, " phim 4"));
        lstPop.add(new Popular(R.drawable.profile_test, " phim 5"));
        lstPop.add(new Popular(R.drawable.profile_test, " phim 6"));
        lstPop.add(new Popular(R.drawable.profile_test, " phim 7"));
        lstPop.add(new Popular(R.drawable.profile_test, " phim 8"));
        return lstPop;
    }
    private List<Category> getListCategory() {
        List<Category> lst = new ArrayList<>();
        lst.add(new Category("Action"));
        lst.add(new Category("Drama"));
        lst.add(new Category("Comedy"));
        lst.add(new Category("Superhero"));
        return lst;
    }

    //----------------------------------------4-----------------------------------
    private void searchMovieApi(String query, int pageNumber){
        homeViewModel.searchMovieApi(query, pageNumber);
    }
    private void ConfigureRecyclerView(){
        popularAdapter = new PopularAdapter( this);
        rcPopular.setAdapter(popularAdapter);
        rcPopular.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL,false));

        rcPopular.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!rcPopular.canScrollHorizontally(1)){
                    homeViewModel.searchNextPage();

                }
            }
        });

    }
    @Override
    public void onPopularClick(int position) {
//        Toast.makeText(this.getContext(), "The position "+ position, Toast.LENGTH_SHORT).show();
//        Navigation.findNavController(getView()).navigate(HomeFragmentDirections.home2details());
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);
        bottomNavigationView.setVisibility(View.GONE);

        MovieModel movieModel = popularAdapter.getSelectedMovie(position);
        Log.e("Sunday", movieModel.getTitle());
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movieModel);
//        Navigation.findNavController(getView()).navigate(HomeFragmentDirections.home2details(bundle));
        Navigation.findNavController(getView()).navigate(HomeFragmentDirections.home2details(movieModel).setMovie(movieModel));

    }
    @Override
    public void onCategoryClick(String category) {
    }
}