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

public class registerActivity extends AppCompatActivity {
    @BindView(R.id.firstName)EditText firstName;
    @BindView(R.id.lastName)EditText lastName;
    @BindView(R.id.editTextPhone)EditText phoneNumber;
    @BindView(R.id.email)EditText emailText;
    @BindView(R.id.password)EditText passwordText;
    @BindView(R.id.conPass)EditText confirmPasswordText;
    @BindView(R.id.signUpAccButton)Button registerButton;
    @BindView(R.id.loginView)TextView loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registerActivity.this, loginActivity.class));
                finish();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                View focusedView = registerActivity.this.getCurrentFocus();
                if(focusedView!=null){
                    inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String confirmPassword = confirmPasswordText.getText().toString();
                if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(registerActivity.this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
                } else if(!password.equals(confirmPassword)){
                    Toast.makeText(registerActivity.this, "Check your passwords!!", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(registerActivity.this, "Registered Successfully. Now you can LogIn", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(registerActivity.this, loginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(registerActivity.this, "Registration failed: "+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        /*findViewById(R.id.registerConstraint).setOnClickListener(view -> {
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
        });*/

    }
}