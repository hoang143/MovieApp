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
    private List<MovieModel> mMovies;

    private OnUpcomingListener onUpcomingListener;
    public UpComingAdapter(OnUpcomingListener onUpcomingListener) {
        this.onUpcomingListener = onUpcomingListener;
    }
    public void setData(List<MovieModel> list){
        this.mMovies = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UpcomingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming,parent,false);
        return new UpcomingViewHolder(view, onUpcomingListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingAdapter.UpcomingViewHolder holder, int position) {
        holder.tvTitle.setText(mMovies.get(position).getTitle());

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/"
                        +mMovies.get(position).getPoster_path())
                .into(holder.ivUpcoming);
    }

    @Override
    public int getItemCount() {
        if(mMovies != null){
            return mMovies.size();
        }
        return 0;
    }

    public MovieModel getSelectedTrendingMovie(int position) {
        if(mMovies != null){
            if(mMovies.size() >0){
                return mMovies.get(position);
            }
        }
        return null;
    }



    public class UpcomingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnUpcomingListener onUpcomingListener;
        private ImageView ivUpcoming;
        private TextView tvTitle;
        public UpcomingViewHolder(@NonNull View itemView, OnUpcomingListener onUpcomingListener){
            super(itemView);
            this.onUpcomingListener = onUpcomingListener;
            this.tvTitle = itemView.findViewById(R.id.tvTitle_Upcoming);
            this.ivUpcoming = itemView.findViewById(R.id.ivUpcoming);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onUpcomingListener.onUpcomingClick(getAdapterPosition());
        }
    }
}
