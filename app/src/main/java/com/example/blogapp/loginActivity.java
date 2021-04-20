package com.example.blogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editText1 = findViewById(R.id.emailLogIn);
        EditText editText2 = findViewById(R.id.passwordLogIn);
        findViewById(R.id.loginConstraint).setOnClickListener(view -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            View focusedView = loginActivity.this.getCurrentFocus();
            if (focusedView != null) {
                imm.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                editText1.setFocusableInTouchMode(false);
                editText1.setFocusable(false);
                editText1.setFocusableInTouchMode(true);
                editText1.setFocusable(true);

                editText2.setFocusableInTouchMode(false);
                editText2.setFocusable(false);
                editText2.setFocusableInTouchMode(true);
                editText2.setFocusable(true);
            }
        });
        findViewById(R.id.signUpView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this, registerActivity.class));
            }
        });

    }
}