package com.asac.quickseller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.asac.quickseller.NavbarAdapter;
import com.asac.quickseller.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.amplifyframework.core.Amplify;

public class SettingsActivity extends AppCompatActivity {
    public final String TAG = "SettingsActivity";
    ViewPager2 viewPager;
    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        setupViewPager();
        setupBottomNavigation();

        setupAboutUsButton();
        setupPrivacyPolicyButton();
        setupLogOutButton();
        setupChangePasswordButton();
        setupMyPostButton();
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

                if (itemId == R.id.settings_bottom) {
                    return true;
                } else if (itemId == R.id.profile_bottom) {
                    startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
                    return true;
                } else if (itemId == R.id.home_bottom) {
                    startActivity(new Intent(SettingsActivity.this, HomeActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    private int getNavigationItemId(int position) {
        switch (position) {
            case 0:
                return R.id.settings_bottom;
            case 1:
                return R.id.profile_bottom;
            case 2:
                return R.id.home_bottom;
            default:
                return 0;
        }
    }


    //Logout Functionality

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        amplifySignOut();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void amplifySignOut() {
        Amplify.Auth.signOut(
                () -> {
                    Log.i(TAG, "Logout succeeded");
                    navigateToLogin();
                },
                failure -> {
                    Log.e(TAG, "Logout failed", failure);
                    runOnUiThread(() ->
                            Toast.makeText(SettingsActivity.this, "Log out failed", Toast.LENGTH_LONG).show()
                    );
                }
        );
    }

    private void setupLogOutButton() {
        Button logoutButton = findViewById(R.id.settingsPageSignOut);
        logoutButton.setOnClickListener(b -> {
            showLogoutConfirmationDialog();
        });
    }

    private void navigateToLogin() {
        Intent goToLoginIntent = new Intent(SettingsActivity.this, LoginActivity.class);
        startActivity(goToLoginIntent);
        finish();
    }

    //Privacy Policy Functionality
    private void setupPrivacyPolicyButton() {
        Button privacyPolicyButton = findViewById(R.id.settingprivacyandpolicybutton);
        privacyPolicyButton.setOnClickListener(b -> {
            Intent intent = new Intent(SettingsActivity.this, PrivacyAndPolicy.class);
            startActivity(intent);
        });
    }

    //About us Button
    private void setupAboutUsButton() {
        Button aboutUsButton = findViewById(R.id.settingAboutUsButton);
        aboutUsButton.setOnClickListener(b -> {
            Intent intent = new Intent(SettingsActivity.this, AboutUsActivity.class);
            startActivity(intent);
        });
    }


    //change password Button
    private void setupChangePasswordButton() {
        Button changePasswordButton = findViewById(R.id.settingChangePasswordButton);
        changePasswordButton.setOnClickListener(b -> {
            Intent intent = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        });
    }

    // My Post Button
    private void setupMyPostButton() {
        Button myPosts = findViewById(R.id.myPost);
        myPosts.setOnClickListener(b -> {
            Intent intent = new Intent(SettingsActivity.this, MyPostsActivity.class);
            startActivity(intent);
        });
    }

}
