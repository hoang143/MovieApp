package com.example.mockprojectv3.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mockprojectv3.R;
import com.example.mockprojectv3.model.MovieModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {
    private TextView tvTitle, tvOverView;
    private ImageView ivPoster;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_movie_detail,container, false);
        tvTitle = view.findViewById(R.id.tvTitle_MovieDetails);
        tvOverView = view.findViewById(R.id.tvOverView_MovieDetails);
        ivPoster = view.findViewById(R.id.ivPoster_MovieDetails);


        GetDataFromHome();
        // Inflate the layout for this fragment
        return view;
    }

    private void GetDataFromHome() {
        MovieModel movieModel;
        if (getArguments() != null) {
            MovieDetailFragmentArgs args = MovieDetailFragmentArgs.fromBundle(getArguments());
            movieModel = args.getMovie();
            tvTitle.setText(movieModel.getTitle());
            tvOverView.setText(movieModel.getMovie_overview());
            Glide.with(this.getContext())
                    .load("https://image.tmdb.org/t/p/w500/"
                            +movieModel.getPoster_path())
                    .into(ivPoster);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }
}