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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditMyPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_post);

        EditText nameEditMyPost = findViewById(R.id.nameEditMyPost);
        EditText descriptionEditMyPost = findViewById(R.id.descriptionEditMyPost);
        EditText priceEditMyPost = findViewById(R.id.priceEditMyPost);
        Button editMyPostfinalButton = findViewById(R.id.editMyPostfinalButton);

        // Retrieve post details from intent
        String postId = getIntent().getStringExtra("postId");
        String postTitle = getIntent().getStringExtra("title");
        String postDescription = getIntent().getStringExtra("description");
        String postPrice = getIntent().getStringExtra("price");
        String city = getIntent().getStringExtra("city");
        String productCategory = getIntent().getStringExtra("productCategory");
        String createdAt = getIntent().getStringExtra("date");

        // Check for null values
        if (createdAt == null) {
            // Handle the case where createdAt is null (show a message or take appropriate action)
            Log.e("EditMyPostActivity", "Error: createdAt is null");
            return;
        }

        Temporal.DateTime updatedCreatedAt = convertStringToTemporalDateTime(createdAt);

        // Fetch user data from Amplify or obtain it from the intent
        String userId = Amplify.Auth.getCurrentUser().getUserId(); // Replace this with the actual way to get the user ID

        // Populate EditText fields
        nameEditMyPost.setText(postTitle);
        descriptionEditMyPost.setText(postDescription);
        priceEditMyPost.setText(postPrice);

        // Implement logic to update the post when editMyPostfinalButton is clicked
        editMyPostfinalButton.setOnClickListener(view -> {
            // Retrieve updated data from EditText fields
            String updatedTitle = nameEditMyPost.getText().toString();
            String updatedDescription = descriptionEditMyPost.getText().toString();
            String updatedPrice = priceEditMyPost.getText().toString();

            // Create a new Post object with updated data
            Post updatedPost = Post.builder()
                    .user(User.justId(userId)) // Use the actual user ID
                    .city(city) // Replace with the actual city
                    .title(updatedTitle)
                    .price(updatedPrice)
                    .productCategory(ProductCategoryEnum.valueOf(productCategory))
                    .createdAt(updatedCreatedAt) // Replace with the actual category
                    .description(updatedDescription)
                    // You may need to set other fields as needed
                    .build();

            // Update the post using Amplify API
            Amplify.API.mutate(
                    ModelMutation.update(updatedPost),
                    response -> {
                        runOnUiThread(() -> {
                            // Handle successful update
                            // ...

                            // Finish the activity or navigate back to the previous screen
                            finish();
                        });
                    },
                    error -> {
                        Log.e("UpdateError", "Error updating post: " + error.getMessage());
                        // Handle update error
                        // ...
                    }
            );
        });
    }

    private Temporal.DateTime convertStringToTemporalDateTime(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date = format.parse(dateString);
            return new Temporal.DateTime(date, 0);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


}
