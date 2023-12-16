package com.asac.quickseller.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;


import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Post;
import com.amplifyframework.datastore.generated.model.ProductCategoryEnum;
import com.amplifyframework.datastore.generated.model.User;
import com.asac.quickseller.R;

import java.util.Date;


public class EditMyPostActivity extends AppCompatActivity {

    private EditText nameEditText, descriptionEditText, priceEditText;
    private Button editMyPostfinalButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_post);

        // Initialize EditText fields
        nameEditText = findViewById(R.id.nameEditMyPost);
        descriptionEditText = findViewById(R.id.descriptionEditMyPost);
        priceEditText = findViewById(R.id.priceEditMyPost);
        editMyPostfinalButton = findViewById(R.id.editMyPostfinalButton);


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
        editMyPostfinalButton.setOnClickListener(view -> updatePostData());



    }
    private void updatePostData() {
        // Get updated data from EditText fields
        String updatedTitle = nameEditText.getText().toString();
        String updatedDescription = descriptionEditText.getText().toString();
        String updatedPrice = priceEditText.getText().toString();
        String updatedCity = Post.CITY.toString();
        String productCategory = Post.PRODUCT_CATEGORY.toString();
//        Temporal.DateTime dateTime=Post.CREATED_AT();

        // Create a new Post object with updated data
        Post updatedPost =Post.builder()
                .user(User.justId("userId")) // Use the actual user ID
                .city(updatedCity) // Replace with the actual city
                .title(updatedTitle)
                .price(updatedPrice)
                .productCategory(ProductCategoryEnum.valueOf(productCategory))
                .createdAt(new Temporal.DateTime(new Date(), 0)) // Replace with the actual category
                .description(updatedDescription)
                // You may need to set other fields as needed
                .build();

        // Update the post in the database using Amplify API
        Amplify.API.mutate(
                ModelMutation.update(updatedPost),
                response -> {
                    runOnUiThread(() -> {

                        finish();
                    });
                },
                error -> {
                    // Handle error updating post
                    // You may want to show an error message
                    Log.e("UpdateError", "Error updating post: " + error.getMessage());
                }
        );
}
}
