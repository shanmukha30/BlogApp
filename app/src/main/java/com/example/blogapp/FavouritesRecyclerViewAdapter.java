package com.example.blogapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

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

        //holder.NewsTitle.setText(arrayList.get(position));

        //holder.img_recipe_thumbNail.setImageResource(mData.get(position).getThumbNail());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(mContext,RecipeActivity.class);

                intent.putExtra("Name",mData.get(position).getRecipeName());
                intent.putExtra("Ingredients",mData.get(position).getRecipeIngredients());
                intent.putExtra("MethodTitle",mData.get(position).getRecipeMethodTitle());
                intent.putExtra("Recipe",mData.get(position).getRecipe());*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView NewsTitle,Source,Description;
        CardView cardView;
        ImageView thumbNail;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            NewsTitle = itemView.findViewById(R.id.newsTitle);
            thumbNail = itemView.findViewById(R.id.thumbnail_id);
            cardView = itemView.findViewById(R.id.cardView);
            Source = itemView.findViewById(R.id.newsSource);
            Description = itemView.findViewById(R.id.newsDesc);
        }
    }
}