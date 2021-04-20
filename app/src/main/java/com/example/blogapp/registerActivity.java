package com.example.blogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class registerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText editText1 = findViewById(R.id.firstName);
        EditText editText2 = findViewById(R.id.lastName);
        EditText editText3 = findViewById(R.id.editTextPhone);
        EditText editText4 = findViewById(R.id.email);
        EditText editText5 = findViewById(R.id.password);
        EditText editText6 = findViewById(R.id.conPass);

        findViewById(R.id.registerConstraint).setOnClickListener(view -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            View focusedView = registerActivity.this.getCurrentFocus();
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

                editText3.setFocusableInTouchMode(false);
                editText3.setFocusable(false);
                editText3.setFocusableInTouchMode(true);
                editText3.setFocusable(true);

                editText4.setFocusableInTouchMode(false);
                editText4.setFocusable(false);
                editText4.setFocusableInTouchMode(true);
                editText4.setFocusable(true);

                editText5.setFocusableInTouchMode(false);
                editText5.setFocusable(false);
                editText5.setFocusableInTouchMode(true);
                editText5.setFocusable(true);

                editText6.setFocusableInTouchMode(false);
                editText6.setFocusable(false);
                editText6.setFocusableInTouchMode(true);
                editText6.setFocusable(true);
            }
        });

    }
}