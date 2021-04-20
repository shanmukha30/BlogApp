package com.example.blogapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    static ArrayList<Map<String, String>> favouritesList = new ArrayList<>();
    static FavouritesRecyclerViewAdapter favouritesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView favouritesRecyclerView = findViewById(R.id.favouritesRecyclerView);

        favouritesAdapter = new FavouritesRecyclerViewAdapter(this, favouritesList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        favouritesRecyclerView.setLayoutManager(layoutManager);
        favouritesRecyclerView.setAdapter(favouritesAdapter);

        ButterKnife.bind(this);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        favouritesList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(currentUser.toString()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                QuerySnapshot docs = task.getResult();
                if (!docs.isEmpty()){
                    for (QueryDocumentSnapshot titles : docs){
                        Map<String, String> entry = new HashMap<>();
                        entry.put("title", titles.getData().get("title").toString());
                        entry.put("source", titles.getData().get("source").toString());
                        entry.put("imgurl", titles.getData().get("imgurl").toString());
                        entry.put("url", titles.getData().get("url").toString());
                        favouritesList.add(entry);
                    }
                }
            }
        }).addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Couldn't retrieve data", Toast.LENGTH_SHORT).show());

        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.logoutButton){
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
            }
            else if(item.getItemId() == R.id.searchButton){
                Intent intent = new Intent(this, NewsActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}