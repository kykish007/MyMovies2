package com.example.mymovies2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies2.R;
import com.example.mymovies2.data.Rewiew;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<Rewiew> rewiews;

    public void setRewiews(ArrayList<Rewiew> rewiews) {
        this.rewiews = rewiews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Rewiew rewiew = rewiews.get(position);
        holder.textViewContent.setText(rewiew.getContent());
        holder.textViewAutor.setText(rewiew.getAuthor());
    }

    @Override
    public int getItemCount() {
        return rewiews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewAutor;
        private final TextView textViewContent;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAutor = itemView.findViewById(R.id.textViewAutor);
            textViewContent = itemView.findViewById(R.id.textViewContent);
        }
    }
}
