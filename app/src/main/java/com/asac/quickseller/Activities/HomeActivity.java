package com.asac.quickseller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.CityEnum;
import com.amplifyframework.datastore.generated.model.Post;
import com.amplifyframework.datastore.generated.model.ProductCategoryEnum;
import com.asac.quickseller.NavbarAdapter;
import com.asac.quickseller.R;
import com.asac.quickseller.adapter.MyAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public final String TAG = "HomeActivity";

    ViewPager2 viewPager;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        RecyclerView recyclerView = findViewById(R.id.post_view);

//        List<Post> items = new ArrayList<>();
//        items.add(Post.builder()
//                .city(CityEnum.Amman)
//                .title("AAA")
//                .price("111")
//                .productCategory(ProductCategoryEnum.Clothes)
//                .images(List.of("Pic111"))
//                .createdAt(new Temporal.DateTime(new Date(),0)).build()
//        );
//
//        items.add(Post.builder()
//                .city(CityEnum.Zarqa)
//                .title("BBB")
//                .price("222")
//                .productCategory(ProductCategoryEnum.Electronics)
//                .images(List.of("Pic222"))
//                .createdAt(new Temporal.DateTime(new Date(),0)).build()
//        );
//
//
//        items.add(Post.builder()
//                .city(CityEnum.Zarqa)
//                .title("BBB")
//                .price("222")
//                .productCategory(ProductCategoryEnum.Electronics)
//                .images(List.of("Pic222"))
//                .createdAt(new Temporal.DateTime(new Date(),0)).build()
//        );
//
//
//        items.add(Post.builder()
//                .city(CityEnum.Zarqa)
//                .title("BBB")
//                .price("222")
//                .productCategory(ProductCategoryEnum.Electronics)
//                .images(List.of("Pic222"))
//                .createdAt(new Temporal.DateTime(new Date(),0)).build()
//        );
//
//
//        items.add(Post.builder()
//                .city(CityEnum.Zarqa)
//                .title("BBB")
//                .price("222")
//                .productCategory(ProductCategoryEnum.Electronics)
//                .images(List.of("Pic222"))
//                .createdAt(new Temporal.DateTime(new Date(),0)).build()
//        );
//
//
//        items.add(Post.builder()
//                .city(CityEnum.Zarqa)
//                .title("BBB")
//                .price("222")
//                .productCategory(ProductCategoryEnum.Electronics)
//                .images(List.of("R.drawable.logoooo"))
//                .createdAt(new Temporal.DateTime(new Date(),0)).build()
//        );
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),items));

    }

    @Override
    protected void onResume() {
        super.onResume();
        queryDataAndDisplay();
    }

    private void queryDataAndDisplay() {

        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        setupViewPager();
        setupBottomNavigation();
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

                if (itemId == R.id.home_bottom) {
                    return true;
                } else if (itemId == R.id.profile_bottom) {
                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                    return true;
                } else if (itemId == R.id.settings_bottom) {
                    startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                    return true;
                }

                return false;
            }
        });
    }

    private int getNavigationItemId(int position) {
        switch (position) {
            case 0:
                return R.id.home_bottom;
            case 1:
                return R.id.profile_bottom;
            case 2:
                return R.id.settings_bottom;
            default:
                return 0;
        }
    }


//    private void logout() {
//        logoutButton = findViewById(R.id.btnHomeActivityAddItem);
//        logoutButton.setOnClickListener(v ->
//                Amplify.Auth.signOut(
//                        () ->
//                        {
//                            Log.i(TAG, "Logout succeeded");
//                            //                        runOnUiThread(() ->
//                            //                        {
//                            //                            ((TextView)findViewById(R.id.userEmailTextView)).setText("");
//                            //                        });
//                            Intent goToLogInIntent = new Intent(HomeActivity.this, LoginActivity.class);
//                            startActivity(goToLogInIntent);
//                        },
//                        failure ->
//                        {
//                            Log.i(TAG, "Logout failed");
//                            runOnUiThread(() ->
//                                    Toast.makeText(HomeActivity.this, "Log out failed", Toast.LENGTH_LONG).show());
//                        }
//                ));
//    }
    }