package com.example.mockprojectv3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mockprojectv3.R;
import com.example.mockprojectv3.model.MovieModel;
import com.example.mockprojectv3.utils.Credentials;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_POPULAR = 1;
    private static final int VIEW_TYPE_UPCOMING = 2;

    private List<MovieModel> popularMovies;
    private List<MovieModel> upcomingMovies;
    private OnItemListener onItemListener;

    public MoviesAdapter(List<MovieModel> popularMovies, List<MovieModel> upcomingMovies, OnItemListener onItemListener) {
        this.popularMovies = popularMovies;
        this.upcomingMovies = upcomingMovies;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_POPULAR) {
            View popularView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular, parent, false);
            return new PopularViewHolder(popularView);
        } else if (viewType == VIEW_TYPE_UPCOMING) {
            View upcomingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming, parent, false);
            return new UpcomingViewHolder(upcomingView);
        }
        throw new IllegalArgumentException("Invalid view type");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PopularViewHolder) {
            PopularViewHolder popularViewHolder = (PopularViewHolder) holder;
            MovieModel movie = popularMovies.get(position);
            popularViewHolder.tvTitle.setText(movie.getTitle());
            Glide.with(popularViewHolder.itemView.getContext())
                    .load(Credentials.BASE_URL_IMAGE + movie.getPoster_path())
                    .into(popularViewHolder.ivPop);
        } else if (holder instanceof UpcomingViewHolder) {
            UpcomingViewHolder upcomingViewHolder = (UpcomingViewHolder) holder;
            MovieModel movie = upcomingMovies.get(position - popularMovies.size());
            upcomingViewHolder.tvTitle.setText(movie.getTitle());
            Glide.with(upcomingViewHolder.itemView.getContext())
                    .load(Credentials.BASE_URL_IMAGE + movie.getPoster_path())
                    .into(upcomingViewHolder.ivUpcoming);
        }
    }

    @Override
    public int getItemCount() {
        return popularMovies.size() + upcomingMovies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < popularMovies.size()) {
            return VIEW_TYPE_POPULAR;
        } else {
            return VIEW_TYPE_UPCOMING;
        }
    }

    public MovieModel getSelectedMovie(int position) {
        if (position < popularMovies.size()) {
            return popularMovies.get(position);
        } else {
            return upcomingMovies.get(position - popularMovies.size());
        }
    }

    public void setPopularData(List<MovieModel> list) {
        this.popularMovies = list;
        notifyDataSetChanged();
    }

    public void setUpComingData(List<MovieModel> movieModels) {
        this.upcomingMovies = movieModels;
        notifyDataSetChanged();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private ImageView ivPop;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivPop = itemView.findViewById(R.id.ivPopular);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onPopularClick(getAdapterPosition());
        }
    }

    public class UpcomingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private ImageView ivUpcoming;

        public UpcomingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle_Upcoming);
            ivUpcoming = itemView.findViewById(R.id.ivUpcoming);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemListener.onUpcomingClick(getAdapterPosition());
        }
    }
}
