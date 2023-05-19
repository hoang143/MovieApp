package com.example.mockprojectv3.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mockprojectv3.R;
import com.example.mockprojectv3.adapter.CategoryAdapter;
import com.example.mockprojectv3.adapter.MoviesAdapter;
import com.example.mockprojectv3.adapter.OnItemListener;
import com.example.mockprojectv3.databinding.FragmentHomeBinding;
import com.example.mockprojectv3.model.Category;
import com.example.mockprojectv3.model.MovieModel;
import com.example.mockprojectv3.repositories.Resource;
import com.example.mockprojectv3.service.State;
import com.example.mockprojectv3.utils.Credentials;
import com.example.mockprojectv3.viewmodel.HomeViewModel;
import com.example.mockprojectv3.viewmodel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnItemListener {
    ProgressDialog progressDialog;
    private FragmentManager fragmentManager;
    private RecyclerView rcCategories;
    private RecyclerView rcPopular;
    private RecyclerView rcUpcoming;
    private CategoryAdapter categoryAdapter;
    private FragmentHomeBinding binding;
    private MoviesAdapter popularAdapter;
    private MoviesAdapter upcomingAdapter;
    private SearchView searchView;
    private HomeViewModel homeViewModel;
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.setHomeViewModel(homeViewModel);
        binding.setUserViewModel(userViewModel);
        initUI();

        // Categories
        ConfigureCategoryRecyclerView();

        // Popular:
        ConfigurePopularRecyclerView();
        ObservePopularChange();

        // Trending
        ConfigureTrendingRecyclerView();
        ObserveTrendingChange();
        loadMovies(1);
        initListener();

        return view;
    }

    private void ConfigureCategoryRecyclerView() {
        categoryAdapter = new CategoryAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        rcCategories.setLayoutManager(linearLayoutManager);
        rcCategories.setNestedScrollingEnabled(false);
        categoryAdapter.setData(getListCategory());
        rcCategories.setAdapter(categoryAdapter);
    }

    private void initListener() {
        binding.ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userViewModel.navigateTo(fragmentManager, new ProfileFragment());
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            private boolean doubleBackToExitPressedOnce = false;

            @Override
            public void handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    requireActivity().finish();
                } else {
                    doubleBackToExitPressedOnce = true;
                    Toast.makeText(getContext(), "Press back to close", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 3000);
                }
            }
        });
    }

    private void initUI() {
        fragmentManager = requireActivity().getSupportFragmentManager();
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);
        bottomNavigationView.setVisibility(View.VISIBLE);
        searchView = binding.svHome;
        rcCategories = binding.rcCategories;
        rcPopular = binding.rcPopular;
        progressDialog = new ProgressDialog(getContext());
        rcUpcoming = binding.rcUpcoming;

        //Glide
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Uri photoUrl = user.getPhotoUrl();
        Glide.with(getContext()).load(photoUrl).error(R.drawable.img_not_found).into(binding.ivProfile);
        if(TextUtils.isEmpty(user.getDisplayName())){
            binding.tvWelcome.setText("Welcome ");
        }
        binding.tvWelcome.setText("Welcome "+ user.getDisplayName());

        // Set up Search view
        SetupSearchView();
    }

    private void SetupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                homeViewModel.searchMovies(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void ObservePopularChange() {
        homeViewModel.getPopularMovies().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.getStatus()) {
                    case LOADING:
                        progressDialog = ProgressDialog.show(getContext(), "", "Loading...", true);
                        break;
                    case SUCCESS:
                        List<MovieModel> movieModels = resource.getData();
                        if (movieModels != null) {
                            popularAdapter.setPopularData(movieModels);
                        }
                        progressDialog.dismiss();
                        break;
                    case ERROR:
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void ObserveTrendingChange() {
        homeViewModel.getTrendingMovies().observe(getViewLifecycleOwner(), new Observer<Resource<List<MovieModel>>>() {
            @Override
            public void onChanged(Resource<List<MovieModel>> resource) {
                if (resource != null) {
                    switch (resource.getStatus()) {
                        case LOADING:
                            progressDialog = ProgressDialog.show(getContext(), "", "Loading...", true);
                            break;
                        case SUCCESS:
                            List<MovieModel> movieModels = resource.getData();
                            if (movieModels != null) {
                                Toast.makeText(getContext(), "Loading Success", Toast.LENGTH_SHORT).show();
                                upcomingAdapter.setUpComingData(movieModels);
                            }
                            progressDialog.dismiss();
                            break;
                        case ERROR:
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });
    }

    private void loadMovies(int pageNumber) {
        progressDialog.show();
        homeViewModel.loadMovies(pageNumber);
    }

    private void ConfigurePopularRecyclerView() {
        popularAdapter = new MoviesAdapter(new ArrayList<>(), new ArrayList<>(), this);
        rcPopular.setAdapter(popularAdapter);
        rcPopular.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));

        rcPopular.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!rcPopular.canScrollHorizontally(1)) {
                    homeViewModel.loadNextPage();
                }
            }
        });
    }

    private void ConfigureTrendingRecyclerView() {
        upcomingAdapter = new MoviesAdapter(new ArrayList<>(), new ArrayList<>(), this);
        rcUpcoming.setAdapter(upcomingAdapter);
        rcUpcoming.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));

        rcUpcoming.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!rcUpcoming.canScrollHorizontally(1)) {
                    homeViewModel.loadNextPage();
                }
            }
        });
    }

    private List<Category> getListCategory() {
        List<Category> lst = new ArrayList<>();
        lst.add(new Category("Action"));
        lst.add(new Category("Adventure"));
        lst.add(new Category("Comedy"));
        lst.add(new Category("Drama"));
        lst.add(new Category("Fantasy"));
        lst.add(new Category("Horror"));
        lst.add(new Category("Mystery"));
        lst.add(new Category("Romance"));
        lst.add(new Category("Sci-Fi"));
        lst.add(new Category("Thriller"));
        lst.add(new Category("Western"));
        return lst;
    }

    @Override
    public void onPopularClick(int position) {
        MovieModel movieModel = popularAdapter.getSelectedMovie(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movieModel);

        // Tạo instance của MovieDetailsFragment
        MovieDetailFragment movieDetailsFragment = new MovieDetailFragment();
        movieDetailsFragment.setArguments(bundle);

        // Thay thế fragment hiện tại bằng MovieDetailsFragment
        getParentFragmentManager().beginTransaction()
                .replace(R.id.containerFragment, movieDetailsFragment)
                .commit();
    }

    @Override
    public void onUpcomingClick(int position) {
        MovieModel movieModel = popularAdapter.getSelectedMovie(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movieModel);

        // Tạo instance của MovieDetailsFragment
        MovieDetailFragment movieDetailsFragment = new MovieDetailFragment();
        movieDetailsFragment.setArguments(bundle);

        // Thay thế fragment hiện tại bằng MovieDetailsFragment
        getParentFragmentManager().beginTransaction()
                .replace(R.id.containerFragment, movieDetailsFragment)
                .commit();
    }
}
