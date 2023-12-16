package com.asac.quickseller.Activities;

import android.os.Bundle;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;


import com.asac.quickseller.R;


public class EditMyPostActivity extends AppCompatActivity {

    private EditText nameEditText, descriptionEditText, priceEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_post);

        // Initialize EditText fields
        nameEditText = findViewById(R.id.nameEditMyPost);
        descriptionEditText = findViewById(R.id.descriptionEditMyPost);
        priceEditText = findViewById(R.id.priceEditMyPost);

        // Retrieve data from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String postId = extras.getString("postId");
            String postTitle = extras.getString("postTitle");
            String postDescription = extras.getString("postDescription");
            String postPrice = extras.getString("postPrice");

            // Set data in EditText fields
            nameEditText.setText(postTitle);
            descriptionEditText.setText(postDescription);
            priceEditText.setText(postPrice);
        }


    }
}
