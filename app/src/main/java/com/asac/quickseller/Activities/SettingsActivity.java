package com.asac.quickseller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.asac.quickseller.NavbarAdapter;
import com.asac.quickseller.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {
    ViewPager2 viewPager;
    BottomNavigationView bottomNavigationView;

    Button addPost = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        setupViewPager();
        setupBottomNavigation();
        setupAddPostBtn();
    }

   private void setupAddPostBtn(){
        addPost = (Button) findViewById(R.id.settingsPageAddPostBtn) ;
        addPost.setOnClickListener(b -> {
            Intent goToAddPost = new Intent(SettingsActivity.this,AddItemActivity.class);
            startActivity(goToAddPost);
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
}