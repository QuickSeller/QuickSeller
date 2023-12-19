package com.asac.quickseller.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.CityEnum;
import com.amplifyframework.datastore.generated.model.Post;
import com.amplifyframework.datastore.generated.model.ProductCategoryEnum;
import com.amplifyframework.datastore.generated.model.User;
import com.asac.quickseller.R;

import java.util.List;

public class EditPostActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText priceEditText;
    private Button saveButton;

    private String postId;

    private List<String> existingImages; // Add this field to store existing images


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        titleEditText = findViewById(R.id.editPostActivityEditTitleEditText);
        descriptionEditText = findViewById(R.id.editPostActivityEditDescriptionEditText);
        priceEditText = findViewById(R.id.editPostActivityEditPriceEditText);
        saveButton = findViewById(R.id.saveButton);

        // Get the post ID from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            postId = extras.getString("postId");
            Log.i("IDddd" , postId);
        }

        // Load the existing post data
        loadPostData();

        saveButton.setOnClickListener(view -> onSaveButtonClick());
    }

    private void loadPostData() {
        Amplify.API.query(
                ModelQuery.get(Post.class, postId),
                response -> {
                    Post post = response.getData();
                    existingImages = post.getImages();
                    runOnUiThread(() -> {
                        // Set the existing data in the EditText fields
                        titleEditText.setText(post.getTitle());
                        descriptionEditText.setText(post.getDescription());
                        priceEditText.setText(String.valueOf(post.getPrice()));
                    });
                },
                error -> {
                    // Handle error
                    showToast("Error loading post data");
                    finish();
                }
        );
    }

    private void onSaveButtonClick() {
        // Get the updated values from the EditText fields
        String updatedTitle = titleEditText.getText().toString().trim();
        String updatedDescription = descriptionEditText.getText().toString().trim();
        double updatedPrice = Double.parseDouble(priceEditText.getText().toString().trim());

        // Update the post
        updatePost(updatedTitle, updatedDescription, updatedPrice);
    }

    private void updatePost(String updatedTitle, String updatedDescription, double updatedPrice) {
        // Fetch the existing post from the database to get user, createdAt, productCategory, and city values
        Amplify.API.query(
                ModelQuery.get(Post.class, postId),
                response -> {
                    Post existingPost = response.getData();
                    if (existingPost != null) {
                        // Get existing user, createdAt, productCategory, and city values
                        User existingUser = existingPost.getUser();
                        Temporal.DateTime existingCreatedAt = existingPost.getCreatedAt();
                        ProductCategoryEnum existingProductCategory = existingPost.getProductCategory();
                        String existingCity = String.valueOf(existingPost.getCity());

                        // Get the updated values from the EditText fields
                        // Use existing values or set default values based on your logic
                        String updatedCity = existingCity != null ? existingCity : "Default City";
                        ProductCategoryEnum updatedProductCategory = existingProductCategory != null ? existingProductCategory : ProductCategoryEnum.Misc;

                        // Create a new Post object with updated values, using existing user, createdAt, productCategory, and city
                        Post updatedPost = Post.builder()
                                .user(existingUser)
                                .city(CityEnum.valueOf(updatedCity))
                                .title(updatedTitle)
                                .price(String.valueOf(updatedPrice))
                                .productCategory(updatedProductCategory)
                                .createdAt(existingCreatedAt)
                                .id(postId)
                                .description(updatedDescription)
                                .images(existingImages) // Include existing images
                                .build();

                        // Update the post
                        Amplify.API.mutate(
                                ModelMutation.update(updatedPost),
                                mutationResponse -> {
                                    runOnUiThread(() -> {
                                        showToast("Post updated successfully");
                                        finish(); // Finish the activity after updating the post
                                    });
                                },
                                error -> {
                                    // Handle error
                                    showToast("Error updating post");
                                });
                    }
                },
                error -> {
                    // Handle error
                    showToast("Error fetching existing post");
                });
    }





    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
