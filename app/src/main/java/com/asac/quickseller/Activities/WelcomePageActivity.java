package com.asac.quickseller.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.asac.quickseller.R;

public class WelcomePageActivity extends AppCompatActivity {

    Button signupButton = null;
    Button loginButton = null;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page_activity);



        goToSignupActivity();
        goToLoginActivity();
    }


    private void goToSignupActivity() {
        signupButton = findViewById(R.id.btnWelcomePageSignup);
        signupButton.setOnClickListener(v ->
                startActivity(new Intent(WelcomePageActivity.this, SignupActivity.class)));
    }

    private void goToLoginActivity() {
        loginButton = findViewById(R.id.btnWelcomePageLogin);
        loginButton.setOnClickListener(v ->
                startActivity(new Intent(WelcomePageActivity.this, LoginActivity.class)));
    }
}