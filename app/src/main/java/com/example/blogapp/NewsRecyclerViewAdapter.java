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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

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
        Picasso.with(mContext).load(arrayList.get(position).get("imgurl")).into(holder.thumbnail);
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DetailsActivity.class);
            intent.putExtra("url", arrayList.get(position).get("url"));
            mContext.startActivity(intent);
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Map<String, String> entry = new HashMap<>();
                entry.put("title", arrayList.get(position).get("title"));
                entry.put("source", arrayList.get(position).get("name"));
                entry.put("imgurl", arrayList.get(position).get("imgurl"));
                entry.put("url", arrayList.get(position).get("url"));
                MainActivity.favouritesList.add(entry);
                MainActivity.favouritesAdapter.notifyDataSetChanged();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection(FirebaseAuth.getInstance().getCurrentUser().toString()).document("x").set(entry).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(mContext, "Saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Could save", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }
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
