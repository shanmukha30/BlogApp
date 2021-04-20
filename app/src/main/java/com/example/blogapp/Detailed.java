package com.example.blogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Detailed extends AppCompatActivity {
    TextView NewsTitle,Source,Description;
    ImageView thumbnail_id;
    WebView webView;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        NewsTitle = findViewById(R.id.NewsTitle);
        Source = findViewById(R.id.Source);
        thumbnail_id = findViewById(R.id.thumbnail_id)

        Description = findViewById(R.id.Description);
        webView = findViewById(R.id.webView);
        loader = findViewById(R.id.webViewLoader);
        loader.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String source = intent.getStringExtra("source");
        String description = intent.getStringExtra("description");
        String urlToImage = intent.getStringExtra("urlToImage");
        String url = intent.getStringExtra("url");
        NewsTitle.setText(title);
        Source.setText(source);
        Description.setText(description);

        Picasso.with(Detailed.this).load(urlToImage).into(thumbnail_id);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        if(webView.isShown()){
            loader.setVisibility(View.INVISIBLE);
        }
    }
}