package com.example.blogapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;

public class FavouritesRecyclerViewAdapter extends RecyclerView.Adapter<FavouritesRecyclerViewAdapter.MyHolder>{
    private Context mContext;
    private ArrayList<Map<String, String>> arrayList;

    public FavouritesRecyclerViewAdapter(Context mContext, ArrayList<Map<String, String>> arrayList){
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_layout_favourites,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.newsTitle.setText(arrayList.get(position).get("title"));
        holder.newsSource.setText(arrayList.get(position).get("name"));
        holder.newsDesc.setText(arrayList.get(position).get("description"));
        Picasso.with(mContext).load(arrayList.get(position).get("imgurl")).into(holder.thumbnail);
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DetailsActivity.class);
            intent.putExtra("url", arrayList.get(position).get("url"));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.newsTitle) TextView newsTitle;
        @BindView(R.id.thumbnail_id) ImageView thumbnail;
        @BindView(R.id.cardView) CardView cardView;
        @BindView(R.id.newsSource) TextView newsSource;
        @BindView(R.id.newsDesc) TextView newsDesc;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
