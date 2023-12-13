package com.asac.quickseller.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;
import com.asac.quickseller.NavbarAdapter;
import com.asac.quickseller.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.tasks.Task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String USER_NICKNAME_TAG="userNickname";
    public static final String USER_EMAIL_TAG="userEmail";
    ViewPager2 viewPager;
    BottomNavigationView bottomNavigationView;
    SharedPreferences preferences;

    private static final String TAG = "UserProfileActivity";
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


        Button editButton=findViewById(R.id.editImageButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);

            }
        });
    }





    @Override
    protected void onResume() {
        super.onResume();
        String userNickname = preferences.getString(ProfileActivity.USER_NICKNAME_TAG, "No nickname");
        String userEmail = preferences.getString(ProfileActivity.USER_EMAIL_TAG, "No email");
        ((TextView)findViewById(R.id.profileEmail)).setText(userEmail);
        ((TextView)findViewById(R.id.profileUsername)).setText(userNickname);

    }
    private void updateEditText(int editTextId, String text) {
        EditText editText = findViewById(editTextId);
        if (editText != null) {
            editText.setText(text);
        }
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
    private void init() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("openedApp")
                .addProperty("time", Long.toString(new Date().getTime()))
                .addProperty("trackingEvent", " main activity opened")
                .build();
        Amplify.Analytics.recordEvent(event);
    }

}