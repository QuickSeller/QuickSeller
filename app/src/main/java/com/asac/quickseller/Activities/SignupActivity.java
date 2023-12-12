package com.asac.quickseller.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;
import com.asac.quickseller.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "SignupActivity";
    public static final String SIGNUP_EMAIL_TAG = "Signup_Email_Tag";
    Button addUserBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        signUp();
        goToLoginActivity();
    }

    private void signUp() {
        Button signupSubmitButton = (Button) findViewById(R.id.signupSubmitButton);
        signupSubmitButton.setOnClickListener(v ->
        {
            String email = ((EditText) findViewById(R.id.signupEmailEditText)).getText().toString();
            String userName = ((EditText) findViewById(R.id.signupUsernameEditText)).getText().toString();
            String password = ((EditText) findViewById(R.id.signupPasswordEditText)).getText().toString(); //mohamad123
            String phoneNumber = ((EditText) findViewById(R.id.signupPhoneNoEditText)).getText().toString();


            Amplify.Auth.signUp(email,
                    password,
                    AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(),email)
                            .userAttribute(AuthUserAttributeKey.nickname(),userName)
                            .build(),
                    success -> {
                        Log.i(TAG, "SignupActivity() : Signed up successfully" + success.toString());
                        Intent goToVerifyIntent= new Intent(SignupActivity.this, VerifyActivity.class);
                        goToVerifyIntent.putExtra(SIGNUP_EMAIL_TAG, email);//
                        setupCreateUserBtn(email,userName,phoneNumber);
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

    private void setupCreateUserBtn(String email,String userName,String phoneNumber) {

        User newUser =User.builder()
                .username(userName)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
        if(userName == ""){
            Snackbar.make(findViewById(R.id.signup),"Please add your  name", Snackbar.LENGTH_SHORT).show();
        }else if(email == ""){
            Snackbar.make(findViewById(R.id.signup),"Please add your email", Snackbar.LENGTH_SHORT).show();
        } else if (phoneNumber == "") {
            Snackbar.make(findViewById(R.id.signup),"Please add your Phone Number", Snackbar.LENGTH_SHORT).show();
        }else{
            Amplify.API.mutate(
                    ModelMutation.create(newUser),
                    success -> {
                        Log.i(TAG, "SignupActivity(): User added Successfully" + success.toString());
                        runOnUiThread(() -> {
                            Snackbar.make(findViewById(R.id.signup),"New user Added", Snackbar.LENGTH_SHORT).show();
                        });
                    },
                    failure -> {
                        Log.e(TAG, "SignupActivity(): Failed to add User" + failure.toString());

                    }
            );
        }
    }

//    private void saveNewUser(String userName, String email, String phoneNumber) {
//
////        User newUser =User.builder()
////                .username(userName)
////                .email(email)
////                .phoneNumber(phoneNumber)
////                .build();
////        if(userName == ""){
////            Snackbar.make(findViewById(R.id.signup),"Please add your  name", Snackbar.LENGTH_SHORT).show();
////        }else if(email == ""){
////            Snackbar.make(findViewById(R.id.signup),"Please add your email", Snackbar.LENGTH_SHORT).show();
////        } else if (phoneNumber == "") {
////            Snackbar.make(findViewById(R.id.signup),"Please add your Phone Number", Snackbar.LENGTH_SHORT).show();
////        }else{
////            Amplify.API.mutate(
////                    ModelMutation.create(newUser),
////                    success -> {
////                        Log.i(TAG, "SignupActivity(): User added Successfully" + success.toString());
////                        runOnUiThread(() -> {
////                            Snackbar.make(findViewById(R.id.signup),"New user Added", Snackbar.LENGTH_SHORT).show();
////                        });
////                    },
////                    failure -> {
////                        Log.e(TAG, "SignupActivity(): Failed to add User" + failure.toString());
////
////                    }
////            );
////        }
//
//    }

    private void goToLoginActivity() {
        TextView loginTextView = findViewById(R.id.backToLogInTextView);
        loginTextView.setOnClickListener(v ->
                startActivity(new Intent(SignupActivity.this, LoginActivity.class)));
    }

}