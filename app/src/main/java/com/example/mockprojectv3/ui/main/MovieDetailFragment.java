package com.example.mockprojectv3.ui.main;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mockprojectv3.R;
import com.example.mockprojectv3.model.MovieModel;
import com.example.mockprojectv3.utils.Credentials;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {
    private TextView tvTitle, tvOverView, tvReleaseDate;
    private ImageView ivPoster;
    private RatingBar ratingBar;
    View view;
    public static MovieDetailFragment newInstance(Bundle bundle) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_movie_detail,container, false);
        initUI();
        GetDataFromHome();
        initListener();

        // Inflate the layout for this fragment
        return view;
    }

    private void initListener() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.containerFragment, new HomeFragment())
                        .commit();
            }
        });
    }

    private void initUI() {
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);
        bottomNavigationView.setVisibility(View.GONE);
        tvReleaseDate = view.findViewById(R.id.tvReleaseDate);
        ratingBar = view.findViewById(R.id.ratingBar);
        tvTitle = view.findViewById(R.id.tvTitle_MovieDetails);
        tvOverView = view.findViewById(R.id.tvOverView_MovieDetails);
        ivPoster = view.findViewById(R.id.ivPoster_MovieDetails);
    }

    private void GetDataFromHome() {

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("movie")) {
            MovieModel movieModel = bundle.getParcelable("movie");
            tvTitle.setText(movieModel.getTitle());
            tvOverView.setText(movieModel.getMovie_overview());
            Glide.with(getContext())
                    .load(Credentials.BASE_URL_IMAGE + movieModel.getPoster_path())
                    .into(ivPoster);
            tvReleaseDate.setText(movieModel.getRelease_date());
            ratingBar.setRating(movieModel.getVote_average()/2);

        }
    }
}