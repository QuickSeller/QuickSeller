package com.asac.quickseller.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.asac.quickseller.R;

public class HomeActivity extends AppCompatActivity {

    public final String TAG = "HomeActivity";

    Button logoutButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        logoutButton = findViewById(R.id.btnHomeActivityLogout);
        logout();
    }

    public void logout() {
        logoutButton.setOnClickListener(v ->
                Amplify.Auth.signOut(
                        () ->
                        {
                            Log.i(TAG, "Logout succeeded");
    //                        runOnUiThread(() ->
    //                        {
    //                            ((TextView)findViewById(R.id.userEmailTextView)).setText("");
    //                        });
                            Intent goToLogInIntent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(goToLogInIntent);
                        },
                        failure ->
                        {
                            Log.i(TAG, "Logout failed");
                            runOnUiThread(() ->
                                    Toast.makeText(HomeActivity.this, "Log out failed", Toast.LENGTH_LONG).show());
                        }
                ));
    }
}