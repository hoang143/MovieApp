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
import com.example.mockprojectv3.model.Popular;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {



    private List<MovieModel> mMovies;
    private OnPopularListener onPopularListener;
    public PopularAdapter(OnPopularListener onPopularListener) {
        this.onPopularListener = onPopularListener;
    }
    public void setData(List<MovieModel> list){
        this.mMovies = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular,parent,false);
        return new PopularViewHolder(view, onPopularListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        holder.tvTitle.setText(mMovies.get(position).getTitle());

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/"
                        +mMovies.get(position).getPoster_path())
                .into(holder.ivPop);
    }

    @Override
    public int getItemCount() {
        if(mMovies !=null){
            return mMovies.size();
        }
        return 0;
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvTitle;
        private ImageView ivPop;

        // Click
        OnPopularListener onPopularListener;

        public PopularViewHolder(@NonNull View itemView, OnPopularListener onPopularListener ){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivPop = itemView.findViewById(R.id.ivPopular);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPopularListener.onPopularClick(getAdapterPosition());
        }
    }
}
