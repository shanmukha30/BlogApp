package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class loginActivity extends AppCompatActivity {
    @BindView(R.id.emailLogIn) EditText emailLogin;
    @BindView(R.id.passwordLogIn) EditText passwordLogin;
    @BindView(R.id.login) Button loginBtn;
    @BindView(R.id.signUpView) TextView registerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this, registerActivity.class));
                finish();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                View focusedView = loginActivity.this.getCurrentFocus();
                if(focusedView!=null){
                    inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                String email = emailLogin.getText().toString();
                String password = passwordLogin.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(loginActivity.this, "Fields cannot be empty!!", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(loginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(loginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(loginActivity.this, "Login failed: "+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        /*EditText editText1 = findViewById(R.id.emailLogIn);
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
        });*/

    }
}