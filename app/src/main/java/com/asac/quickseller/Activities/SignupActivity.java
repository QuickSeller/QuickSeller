package com.asac.quickseller.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.asac.quickseller.R;

public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "SignupActivity";
    public static final String SIGNUP_EMAIL_TAG = "Signup_Email_Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        signUp();
    }

    public void signUp() {
        Button signupSubmitButton = (Button) findViewById(R.id.signupSubmitButton);
        signupSubmitButton.setOnClickListener(v ->
        {
            String username = ((EditText) findViewById(R.id.signupUsernameEditText)).getText().toString();
            String password = ((EditText) findViewById(R.id.signupPasswordEditText)).getText().toString(); //mohamad123

            Amplify.Auth.signUp(username,
                    password,
                    AuthSignUpOptions.builder()
                            .userAttribute(AuthUserAttributeKey.email(), username)
                            .userAttribute(AuthUserAttributeKey.nickname(), "T")
                            .build(),
                    good ->
                    {
                        Log.i(TAG, "Signup succeeded: " + good.toString());
                        Intent goToVerifyIntent = new Intent(SignupActivity.this, VerifyActivity.class);
                        goToVerifyIntent.putExtra(SIGNUP_EMAIL_TAG, username);
                        startActivity(goToVerifyIntent);
                    },
                    bad ->
                    {
                        Log.i(TAG, "Signup failed with username: " + username + " with this message: " + bad.toString());
                        runOnUiThread(() ->
                        {
                            Toast.makeText(SignupActivity.this, "Signup failed", Toast.LENGTH_LONG).show();
                        });
                    }
            );
        });
    }

}