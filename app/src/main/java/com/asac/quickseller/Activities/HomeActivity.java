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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.CityEnum;
import com.amplifyframework.datastore.generated.model.Comment;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.SearchView;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {

    private final String TAG = "HomeActivity";
    private ArrayList<Post> items = new ArrayList<>();
    private MyAdapter adapter;
    ImageButton addPost = null;
    ViewPager2 viewPager;
    BottomNavigationView bottomNavigationView;
    private ProductCategoryEnum selectedCategory = null;
    private Set<ProductCategoryEnum> selectedCategories = new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontalScrollView);

        ProductCategoryEnum[] categories = ProductCategoryEnum.values();

        populateHorizontalScrollView(horizontalScrollView, categories);

        RecyclerView recyclerView = findViewById(R.id.post_view);
        adapter = new MyAdapter(getApplicationContext(), items);
        setupAddPostBtn();
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

//        AuthUser authUser = Amplify.Auth.getCurrentUser();
//        Log.i("ssssss", authUser.toString());
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
            setCategoryItem(categoryName, context, category);

            categoryItemLayout.setOnClickListener(view -> {
                Log.d(TAG, "Clicked on category: " + categoryName.getText().toString());

                if (selectedCategories.contains(category)) {
                    selectedCategories.remove(category);
                } else {
                    selectedCategories.add(category);
                }

                setCategoryItem(categoryName, context, category);

                queryPostsByCategories(new ArrayList<>(selectedCategories));
            });

            containerLayout.addView(categoryItemLayout);
        }
        scrollView.addView(containerLayout);
    }


    private void setCategoryItem(TextView categoryName, Context context, ProductCategoryEnum category) {
        categoryName.setText(category.name());
        categoryName.setTextColor(selectedCategories.contains(category)
                ? ContextCompat.getColor(context, R.color.g_dark_blue)
                : ContextCompat.getColor(context, R.color.black));
        categoryName.setTextSize(12);
        categoryName.setGravity(Gravity.CENTER_HORIZONTAL);
    }


    private void filterItems(String query) {
        List<Post> filteredList = new ArrayList<>();

        for (Post post : items) {
            if (post != null && post.getTitle() != null && post.getTitle().toLowerCase().contains(query.toLowerCase())) {
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


    private void queryPostsByCategories(List<ProductCategoryEnum> categories) {
        items.clear();
        if (categories.isEmpty()) {
            query(ModelQuery.list(Post.class, ModelPagination.limit(1_000)));
        } else {
            for (ProductCategoryEnum category : categories) {
                GraphQLRequest<PaginatedResult<Post>> request = ModelQuery.list(
                        Post.class,
                        Post.PRODUCT_CATEGORY.eq(category),
                        ModelPagination.limit(1_000)
                );

                query(request);
            }
        }
    }


    private void setupAddPostBtn() {
        addPost = (ImageButton) findViewById(R.id.homePageAddPostBtn);
        addPost.setOnClickListener(b -> {
            Intent goToAddPost = new Intent(HomeActivity.this, AddItemActivity.class);
            startActivity(goToAddPost);
        });

    }
}