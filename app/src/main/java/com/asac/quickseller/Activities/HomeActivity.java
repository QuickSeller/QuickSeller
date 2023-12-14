package com.asac.quickseller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SearchView;

import com.amplifyframework.api.graphql.GraphQLRequest;
import com.amplifyframework.api.graphql.PaginatedResult;
import com.amplifyframework.api.graphql.model.ModelPagination;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;

import com.amplifyframework.datastore.generated.model.Post;
import com.asac.quickseller.NavbarAdapter;
import com.asac.quickseller.R;
import com.asac.quickseller.adapter.MyAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private final String TAG = "HomeActivity";
    private ArrayList<Post> items = new ArrayList<>();
    private MyAdapter adapter;

    ViewPager2 viewPager;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        RecyclerView recyclerView = findViewById(R.id.post_view);
        adapter = new MyAdapter(getApplicationContext(), items);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.searchHomeProducts);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterItems(newText);
                return true;
            }
        });


        queryFirstPage();
    }

    private void filterItems(String query) {
        List<Post> filteredList = new ArrayList<>();

        for (Post post : items) {
            if (post.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(post);
            }
        }

        adapter.filterList(filteredList);
    }

    public void queryFirstPage() {
        query(ModelQuery.list(Post.class, ModelPagination.limit(1_000)));
    }

    private void query(GraphQLRequest<PaginatedResult<Post>> request) {
        Amplify.API.query(
                request,
                response -> {
                    if (response.hasData()) {
                        Log.i(TAG, "Received data: " + response.getData());
                        List<Post> itemList = new ArrayList<>();
                        for (Post post : response.getData().getItems()) {
                            itemList.add(post);
                        }
                        items.addAll(itemList);
                        runOnUiThread(() -> adapter.notifyDataSetChanged());

                        if (response.getData().hasNextResult()) {
                            query(response.getData().getRequestForNextResult());
                        }
                    }
                },
                failure -> Log.e(TAG, "Query failed.", failure)
        );
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
}