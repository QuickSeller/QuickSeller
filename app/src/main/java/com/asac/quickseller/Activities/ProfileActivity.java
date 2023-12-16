package com.asac.quickseller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;
import com.asac.quickseller.NavbarAdapter;
import com.asac.quickseller.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    ViewPager2 viewPager;
    BottomNavigationView bottomNavigationView;
//    ImageView profileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

//        profileImage = findViewById(R.id.profileImageView);
        getUserDataFromDynamoDB();
        goToEditProfile();

        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        setupViewPager();
        setupBottomNavigation();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDataFromDynamoDB();
    }

    private void getUserDataFromDynamoDB() {
        Amplify.API.query(
                ModelQuery.list(User.class, User.EMAIL.eq(Amplify.Auth.getCurrentUser().getUsername())),
                response -> {
                    if (response.hasData() && response.getData().iterator().hasNext()) {
                        User user = response.getData().iterator().next();
                        runOnUiThread(() -> {
                            updateUI(user);
                        });
                    } else {
                        Log.e(TAG, "User not found in the User table.");
                    }
                },
                failure -> {
                    Log.e(TAG, "Failed to query user from the User table: " + failure.getMessage());
                }
        );
    }

    private void updateUI(User user) {
        TextView profileUsername = findViewById(R.id.profileUsername);
        TextView profilePhone = findViewById(R.id.profilePhone);
        TextView profileEmail = findViewById(R.id.profileEmail);

        profileUsername.setText(user.getUsername());
        profilePhone.setText(user.getPhoneNumber());
        profileEmail.setText(user.getEmail());

        if (user.getImage() != null && !user.getImage().isEmpty()) {
            Amplify.Storage.downloadFile(
                    user.getImage(),
                    new File(getApplication().getFilesDir(), user.getImage()),
                    success ->
                    {
                        ImageView productImageView = findViewById(R.id.profileImageView);
                        productImageView.setImageBitmap(BitmapFactory.decodeFile(success.getFile().getPath()));
                    },
                    failure ->
                    {
                        Log.e(TAG, "Unable to get image from S3  for reason: " + failure.getMessage());
                    }
            );
        }

    }

    private void goToEditProfile() {

        Button editButton = findViewById(R.id.editImageButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivityForResult(intent,1);

            }
        });
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
