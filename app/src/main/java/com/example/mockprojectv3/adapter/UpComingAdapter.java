package com.example.mockprojectv3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mockprojectv3.R;
import com.example.mockprojectv3.databinding.ItemNotificationBinding;
import com.example.mockprojectv3.databinding.ItemUpcomingBinding;
import com.example.mockprojectv3.model.MovieModel;
import com.example.mockprojectv3.response.MovieSearchResponse;
import com.example.mockprojectv3.viewmodel.NotificationViewModel;

import java.util.ArrayList;
import java.util.List;

public class UpComingAdapter  extends RecyclerView.Adapter<UpComingAdapter.UpcomingViewHolder>{
    private MovieSearchResponse movieSearchResponse;
    public void setData(MovieSearchResponse movieSearchResponse){
        this.movieSearchResponse = movieSearchResponse;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UpcomingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UpcomingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingAdapter.UpcomingViewHolder holder, int position) {
        MovieModel movies = movieSearchResponse.getMovies().get(position);
        if(movies == null){
            return;
        }
        holder.tvTitle.setText(movies.getTitle());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/"
                        +movies.getPoster_path())
                .into(holder.ivUpcoming);

    }

    @Override
    public int getItemCount() {
        if(movieSearchResponse !=null){
            return movieSearchResponse.getMovies().size();
        }
        return 0;
    }
    public class UpcomingViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private ImageView ivUpcoming;
        public UpcomingViewHolder(@NonNull View itemView){
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.tvUpcoming);
            this.ivUpcoming = itemView.findViewById(R.id.ivUpcoming);
        }
    }
}
