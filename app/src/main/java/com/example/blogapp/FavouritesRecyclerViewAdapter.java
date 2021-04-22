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
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouritesRecyclerViewAdapter extends RecyclerView.Adapter<FavouritesRecyclerViewAdapter.MyHolder>{
     private Context mContext;
    //private ArrayList<Map<String, String>> arrayList;
    private ArrayList<Map<String, String>> favArticles;


    public FavouritesRecyclerViewAdapter(Context mContext, ArrayList<Map<String, String>> arrayList){
        this.mContext = mContext;
        this.favArticles = arrayList;
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

        holder.newsTitle.setText(favArticles.get(position).get("title"));
        holder.newsSource.setText(favArticles.get(position).get("name"));
        holder.newsDesc.setText(favArticles.get(position).get("description"));
        Picasso.with(mContext).load(favArticles.get(position).get("imageUrl")).into(holder.thumbnail);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DetailsActivity.class);
                intent.putExtra("title",favArticles.get(position).get("title"));
                intent.putExtra("source",favArticles.get(position).get("name"));
                intent.putExtra("imageUrl",favArticles.get(position).get("imageUrl"));
                intent.putExtra("url",favArticles.get(position).get("url"));
                intent.putExtra("description",favArticles.get(position).get("description"));
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        //return favArticles.size();
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
            ButterKnife.bind(mContext, itemView);
        }
    }
}
