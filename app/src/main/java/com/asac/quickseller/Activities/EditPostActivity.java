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
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class EditPostActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText priceEditText;
    private Button saveButton;

    private String postId;

    private List<String> existingImages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        titleEditText = findViewById(R.id.editPostActivityEditTitleEditText);
        descriptionEditText = findViewById(R.id.editPostActivityEditDescriptionEditText);
        priceEditText = findViewById(R.id.editPostActivityEditPriceEditText);
        saveButton = findViewById(R.id.saveButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            postId = extras.getString("postId");
            Log.i("IDddd" , postId);
        }

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
                        titleEditText.setText(post.getTitle());
                        descriptionEditText.setText(post.getDescription());
                        priceEditText.setText(String.valueOf(post.getPrice()));
                    });
                },
                error -> {
                    showToast("Error loading post data");
                    finish();
                }
        );
    }

    private void onSaveButtonClick() {
        String updatedTitle = titleEditText.getText().toString().trim();
        String updatedDescription = descriptionEditText.getText().toString().trim();
        double updatedPrice = Double.parseDouble(priceEditText.getText().toString().trim());

        updatePost(updatedTitle, updatedDescription, updatedPrice);
    }

    private void updatePost(String updatedTitle, String updatedDescription, double updatedPrice) {
        Amplify.API.query(
                ModelQuery.get(Post.class, postId),
                response -> {
                    Post existingPost = response.getData();
                    if (existingPost != null) {
                        User existingUser = existingPost.getUser();
                        Temporal.DateTime existingCreatedAt = existingPost.getCreatedAt();
                        ProductCategoryEnum existingProductCategory = existingPost.getProductCategory();
                        String existingCity = String.valueOf(existingPost.getCity());

                        String updatedCity = existingCity != null ? existingCity : "Default City";
                        ProductCategoryEnum updatedProductCategory = existingProductCategory != null ? existingProductCategory : ProductCategoryEnum.Misc;

                        Post updatedPost = Post.builder()
                                .user(existingUser)
                                .city(CityEnum.valueOf(updatedCity))
                                .title(updatedTitle)
                                .price(String.valueOf(updatedPrice))
                                .productCategory(updatedProductCategory)
                                .createdAt(existingCreatedAt)
                                .id(postId)
                                .description(updatedDescription)
                                .images(existingImages)
                                .build();

                        Amplify.API.mutate(
                                ModelMutation.update(updatedPost),
                                mutationResponse -> {
                                    runOnUiThread(() -> {
                                        Snackbar.make(findViewById(R.id.edit_post), "Post updated", Snackbar.LENGTH_SHORT).show();
                                    finish();
                                    });
                                },
                                error -> {
                                    showToast("Error updating post");
                                });
                    }
                },
                error -> {
                    showToast("Error fetching existing post");
                });
    }





    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
