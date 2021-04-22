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
        Picasso.with(mContext).load(arrayList.get(position).get("imgurl"))
                .resize(2048, 1600)
                .onlyScaleDown()
                .placeholder(R.drawable.errorplaceholder)
                .error(R.drawable.errorplaceholder)
                .into(holder.thumbnail);
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext,DetailsActivity.class);
            //intent.putExtra("title",arrayList.get(position).get("title"));
            //intent.putExtra("source",arrayList.get(position).get("name"));
            //intent.putExtra("imageUrl",arrayList.get(position).get("imageUrl"));
            intent.putExtra("url",arrayList.get(position).get("url"));
            //intent.putExtra("description",arrayList.get(position).get("description"));
            mContext.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        //return favArticles.size();
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

       /*@BindView(R.id.newsTitle) TextView newsTitle;
        @BindView(R.id.thumbnail_id)ImageView thumbnail;
        @BindView(R.id.cardView)CardView cardView;
        @BindView(R.id.newsSource)TextView newsSource;
        @BindView(R.id.newsDesc)TextView newsDesc;*/

        TextView newsTitle;
        ImageView thumbnail;
        CardView cardView;
        TextView newsSource;
        TextView newsDesc;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //ButterKnife.bind(mContext, itemView);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            thumbnail = itemView.findViewById(R.id.thumbnail_id);
            cardView = itemView.findViewById(R.id.cardView);
            newsSource = itemView.findViewById(R.id.newsSource);
            newsDesc = itemView.findViewById(R.id.newsDesc);
        }
    }
}
