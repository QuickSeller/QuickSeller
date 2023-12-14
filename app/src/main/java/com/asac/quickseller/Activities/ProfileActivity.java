package com.asac.quickseller.Activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;

import com.asac.quickseller.NavbarAdapter;
import com.asac.quickseller.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    SharedPreferences preferences;
    ViewPager2 viewPager;
    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        setupViewPager();
        setupBottomNavigation();

        Button editButton = findViewById(R.id.editImageButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentUsername = ((TextView) findViewById(R.id.profileUsername)).getText().toString();
                String currentEmail = ((TextView) findViewById(R.id.profileEmail)).getText().toString();
                String currentPhone = ((TextView) findViewById(R.id.profilePhone)).getText().toString();

                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                intent.putExtra("username", currentUsername);
                intent.putExtra("email", currentEmail);
                startActivityForResult(intent,1);

            }
        });}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String updatedUsername = data.getStringExtra("updatedUsername");
            String updatedEmail = data.getStringExtra("updatedEmail");
            ((TextView) findViewById(R.id.profileUsername)).setText(updatedUsername);
            ((TextView) findViewById(R.id.profileEmail)).setText(updatedEmail);
        }
    }
    String email = "";
    String username = "";
    String phone = "";
    @Override
    protected void onResume() {
        super.onResume();
        AuthUser authUser = Amplify.Auth.getCurrentUser();
        getIntent().getStringExtra("email");
        getIntent().getStringExtra("username");
        phone = getIntent().getStringExtra("phoneNumber");

        Amplify.Auth.fetchUserAttributes(
                success -> {
                    for (AuthUserAttribute userAttribute : success) {
                        if (userAttribute.getKey().equals(AuthUserAttributeKey.email())) {
                            email = userAttribute.getValue();
                        } else if (userAttribute.getKey().equals(AuthUserAttributeKey.nickname())) {
                            username = userAttribute.getValue();
                        } else if (userAttribute.getKey().equals("phoneNumber")) {
                            phone = userAttribute.getValue();
                        } else if (userAttribute.getKey().equals(AuthUserAttributeKey.custom("profileImg"))) {
                            String s3Key = userAttribute.getValue();
                            Amplify.Storage.getUrl(
                                    s3Key,
                                    result -> {
                                        String url = result.getUrl().toString();
                                        Log.i(TAG, "Successfully fetched image URL from S3: " + url);
                                        runOnUiThread(() -> {
                                            Picasso.get().load(url).into((ImageView) findViewById(R.id.profileImageView));
                                        });
                                    },
                                    error -> Log.e(TAG, "Failed to get URL from S3: " + error.getMessage())
                            );
                        }
                    }
                    runOnUiThread(() -> {
                        ((TextView) findViewById(R.id.profileEmail)).setText(email);
                        ((TextView) findViewById(R.id.profileUsername)).setText(username);
                        ((TextView) findViewById(R.id.profilePhone)).setText(phone);
                    });
                },
                failure -> {
                    Log.i(TAG, "Fetch user attributes failed: " + failure.toString());
                }
        );
    }


    private void setupViewPager() {
        NavbarAdapter navbarAdapter = new NavbarAdapter(this);
        viewPager.setAdapter(navbarAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNavigationView.setSelectedItemId(getNavigationItemId(position));
            }
        });
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.profile_bottom) {
                    return true;
                } else if (itemId == R.id.home_bottom) {
                    startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                    return true;
                } else if (itemId == R.id.settings_bottom) {
                    startActivity(new Intent(ProfileActivity.this, SettingsActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    private int getNavigationItemId(int position) {
        switch (position) {
            case 0:
                return R.id.profile_bottom;
            case 1:
                return R.id.home_bottom;
            case 2:
                return R.id.settings_bottom;
            default:
                return 0;
        }
    }
}