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

    private void signUp() {
        Button signupSubmitButton = (Button) findViewById(R.id.signupSubmitButton);
        signupSubmitButton.setOnClickListener(v ->
        {
            String email = ((EditText) findViewById(R.id.signupEmailEditText)).getText().toString();
            String nickname = ((EditText) findViewById(R.id.signupUsernameEditText)).getText().toString();
            String password = ((EditText) findViewById(R.id.signupPasswordEditText)).getText().toString(); //mohamad123

            Amplify.Auth.signUp(email,
                    password,
                    AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(),email)
                            .userAttribute(AuthUserAttributeKey.nickname(),nickname)
                            .build(),
                    success -> {
                        Log.i(TAG, "SignupActivity() : Signed up successfully" + success.toString());
                        Intent goToVerifyIntent= new Intent(SignupActivity.this, VerifyActivity.class);
                        goToVerifyIntent.putExtra(SIGNUP_EMAIL_TAG, email);
                        startActivity(goToVerifyIntent);
                    },
                    failure -> {
                        Log.e(TAG, "SignupActivity() : Failed to Sign up", failure);
                        runOnUiThread(() -> {
                            Toast.makeText(SignupActivity.this, "Signup failed: " + failure.getCause().toString(), Toast.LENGTH_LONG).show();
                        });
                    }
            );

        });
    }

}