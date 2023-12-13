package com.asac.quickseller.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.asac.quickseller.R;

public class ItemDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details_activity);

        Intent intent = getIntent();
        String postId = intent.getStringExtra("postId");

        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String price = intent.getStringExtra("price");
        String productCategory = intent.getStringExtra("productCategory");
        String[] images = intent.getStringArrayExtra("images");
        String city = intent.getStringExtra("city");



        TextView titleTextView = findViewById(R.id.itemDetailsItemNameTextView);
        TextView cityTextView = findViewById(R.id.itemDetailsItemCity);
        TextView descriptionTextView = findViewById(R.id.itemDetailsItemDescription);
        TextView priceTextView = findViewById(R.id.itemDetailsItemPrice);
//        ImageView imageView = findViewById(R.id.itemDetailsImageView);

        cityTextView.setText(city);
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        priceTextView.setText(price);

    }
}