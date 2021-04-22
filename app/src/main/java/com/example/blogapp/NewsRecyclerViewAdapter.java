package com.example.blogapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.MyHolder>{
    private Context mContext;
    private ArrayList<Map<String, String>> arrayList;

    public NewsRecyclerViewAdapter(Context mContext, ArrayList<Map<String, String>> arrayList){
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
            Intent intent = new Intent(mContext, DetailsActivity.class);
            intent.putExtra("url", arrayList.get(position).get("url"));
            mContext.startActivity(intent);
        });
        holder.cardView.setOnLongClickListener(v -> {
            Map<String, String> entry = new HashMap<>();
            entry.put("title", arrayList.get(position).get("title"));
            entry.put("name", arrayList.get(position).get("name"));
            entry.put("description", arrayList.get(position).get("description"));
            entry.put("imgurl", arrayList.get(position).get("imgurl"));
            entry.put("url", arrayList.get(position).get("url"));
            MainActivity.favouritesList.add(entry);
            MainActivity.favouritesAdapter.notifyDataSetChanged();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(FirebaseAuth.getInstance().getCurrentUser().getEmail()).document(arrayList.get(position).get("title")).set(entry).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(mContext, "Saved", Toast.LENGTH_SHORT).show();
                    MainActivity.favouritesAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(mContext, "Couldn't save", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> Toast.makeText(mContext, "Couldn't save", Toast.LENGTH_SHORT).show());
            return true;
        });
    }

    @Override
    public int getItemCount() {
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
