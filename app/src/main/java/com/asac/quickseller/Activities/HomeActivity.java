package com.asac.quickseller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amplifyframework.api.graphql.GraphQLRequest;
import com.amplifyframework.api.graphql.PaginatedResult;
import com.amplifyframework.api.graphql.model.ModelPagination;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;

import com.amplifyframework.datastore.generated.model.Post;
import com.amplifyframework.datastore.generated.model.ProductCategoryEnum;
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

        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontalScrollView);

        ProductCategoryEnum[] categories = ProductCategoryEnum.values();

        populateHorizontalScrollView(horizontalScrollView, categories);

        RecyclerView recyclerView = findViewById(R.id.post_view);
        adapter = new MyAdapter(getApplicationContext(), items);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        queryFirstPage();

        AuthUser authUser = Amplify.Auth.getCurrentUser();
        Log.i("ssssss" , authUser.toString());
    }


    private void populateHorizontalScrollView(HorizontalScrollView scrollView, ProductCategoryEnum[] categories) {
        Context context = scrollView.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        LinearLayout containerLayout = new LinearLayout(context);
        containerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        containerLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (ProductCategoryEnum category : categories) {
            ViewGroup categoryItemLayout = (ViewGroup) inflater.inflate(R.layout.category_item, containerLayout, false);

            TextView categoryName = categoryItemLayout.findViewById(R.id.categoryName);

            switch (category) {
                case Clothes:
                    setCategoryItem(categoryName, context, "Clothes");
                    break;
                case Electronics:
                    setCategoryItem(categoryName, context, "Electronics");
                    break;
                case Perishable_Goods:
                    setCategoryItem(categoryName, context, "Perishable Goods");
                    break;
                case Office_supplies:
                    setCategoryItem(categoryName, context, "Office Supplies");
                    break;
                case Misc:
                    setCategoryItem(categoryName, context, "Misc");
                    break;
            }

            categoryItemLayout.setOnClickListener(view -> {
                Log.d(TAG, "Clicked on category: " + categoryName.getText().toString());
            });

            containerLayout.addView(categoryItemLayout);
        }
        scrollView.addView(containerLayout);
    }

    private void setCategoryItem(TextView categoryName, Context context, String name) {
        categoryName.setText(name);
        categoryName.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        categoryName.setTextSize(12);
        categoryName.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
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