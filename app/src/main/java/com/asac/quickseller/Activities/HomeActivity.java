package com.asac.quickseller.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Post;
import com.asac.quickseller.R;

import java.util.Iterator;

public class HomeActivity extends AppCompatActivity {

    public final String TAG = "HomeActivity";

    Button logoutButton = null;
    Button addItemButton = null;
    Button profileButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);


        logout();
        setupGoToAddProductIntent();
        setupGoToProfileIntent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        queryDataAndDisplay();
    }
    private void queryDataAndDisplay() {

    }

    private void setupGoToAddProductIntent(){
        addItemButton= (Button) findViewById(R.id.btnHomeActivityAddItem);
        addItemButton.setOnClickListener(b -> {
            Intent goToAddItem = new Intent(HomeActivity.this, AddItemActivity.class);
            startActivity(goToAddItem);
        });
    }

    private void setupGoToProfileIntent(){
        profileButton= (Button) findViewById(R.id.btnHomeActivityProfile);
        profileButton.setOnClickListener(b -> {
            Intent goToAddItem = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(goToAddItem);
        });
    }
    private void logout() {
        logoutButton = findViewById(R.id.btnHomeActivityAddItem);
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