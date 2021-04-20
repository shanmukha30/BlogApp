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

import java.util.ArrayList;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.MyHolder>{
    private Context mContext;
    private ArrayList<String> arrayList;

    public NewsRecyclerViewAdapter(Context mContext, ArrayList<String> arrayList){
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_layout_news,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.NewsTitle.setText(arrayList.get(position));
        holder.Source.setText(arrayList.get(position));
        String url = mData.getUrl();
        //holder.img_recipe_thumbNail.setImageResource(mData.get(position).getThumbNail());

        String ima
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,Detailed.class);

                intent.putExtra("Name",mData.get(position).getRecipeName());
                intent.putExtra("Ingredients",mData.get(position).getRecipeIngredients());
                intent.putExtra("MethodTitle",mData.get(position).getRecipeMethodTitle());
                intent.putExtra("Recipe",mData.get(position).getRecipe());
                intent.putExtra("url",mData.getUrl());
                intent.putExtra("title",mData.getTitle);
                intent.putExtra("source",mData.getSource);
                intent.putExtra("description",mData.getDescription);



                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public ImageView img_recipe_thumbNail;
        TextView NewsTitle,Source,Description;
        CardView cardView;
        ImageView thumbNail;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            NewsTitle = itemView.findViewById(R.id.NewsTitle);
            thumbNail = itemView.findViewById(R.id.thumbnail_id);
            cardView = itemView.findViewById(R.id.cardView);
            Source = itemView.findViewById(R.id.Source);
            Description = itemView.findViewById(R.id.Description);
        }
    }
}
