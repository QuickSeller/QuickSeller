package com.asac.quickseller.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Comment;
import com.asac.quickseller.R;
import com.asac.quickseller.adapter.CommentsAdapter;
import com.asac.quickseller.adapter.ItemDetailsImageAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ItemDetailsActivity extends AppCompatActivity {

    public static final String TAG = "ItemDetailsActivity";
    Button addCommentBtn=null;
    EditText commentEditText = null;
    List<Comment> commentList = null;
    CommentsAdapter commentsAdapter;
//    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details_activity);

        Intent intent = getIntent();
        String postId = intent.getStringExtra("postId");

        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String price = intent.getStringExtra("price");
//        String productCategory = intent.getStringExtra("productCategory");
//        String[] images = intent.getStringArrayExtra("images");
        String city = intent.getStringExtra("city");
        String owner = intent.getStringExtra("owner");
        String date = intent.getStringExtra("date");



        TextView titleTextView = findViewById(R.id.itemDetailsItemNameTextView);
        TextView cityTextView = findViewById(R.id.itemDetailsItemCity);
        TextView descriptionTextView = findViewById(R.id.itemDetailsItemDescription);
        TextView priceTextView = findViewById(R.id.itemDetailsItemPrice);
        TextView ownerTextView = findViewById(R.id.itemDetailsOwner);
//        imageView = findViewById(R.id.itemDetailsImageView);
        TextView dateTextView = findViewById(R.id.itemDetailsDate);


        cityTextView.setText("City : " + city);
        titleTextView.setText("Item Name : " + title);
        descriptionTextView.setText("Description : " + description);
        priceTextView.setText("Price : " + price);
        dateTextView.setText("Date :" + date);
        ownerTextView.setText("Owner : " + owner);


        commentList=new ArrayList<>();
        recyclerViewSetup();

    }

    private void recyclerViewSetup(){
        RecyclerView commentsRecycler = (RecyclerView) findViewById(R.id.commentsRecyclerView);
        commentsAdapter = new CommentsAdapter(commentList, this);
        commentsRecycler.setAdapter(commentsAdapter);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        commentsRecycler.setLayoutManager(layout);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();

        addComments();

        Amplify.API.query(
                ModelQuery.list(Comment.class),
                success -> {
                    Log.i(TAG,"ItemDetailsActivity(): Comment Read Successfully");
                    commentList.clear();
                    for(Comment userComment : success.getData()){
                        commentList.add(userComment);
                    }
                    runOnUiThread(() -> {
                        commentsAdapter.notifyDataSetChanged();
                    });
                },
                failure -> Log.i(TAG,"ItemDetailsActivity(): Read Comment Failed")
        );

        loadAndDisplayImages();
    }

    private void loadAndDisplayImages() {
        Intent intent = getIntent();
        String[] images = intent.getStringArrayExtra("images");
        if (images != null && images.length > 0) {
            List<String> imageList = Arrays.asList(images);

            RecyclerView recyclerView = findViewById(R.id.itemDetailsImageRecyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);

            ItemDetailsImageAdapter imageAdapter = new ItemDetailsImageAdapter(imageList, this);
            recyclerView.setAdapter(imageAdapter);
        }
    }


    private void addComments() {
        addCommentBtn = findViewById(R.id.HomePageAddCommentBtn);
        commentEditText = findViewById(R.id.HomePageEditTextComment);

        addCommentBtn.setOnClickListener(b -> {
            String comment = commentEditText.getText().toString();

            Comment newComment = Comment.builder()
                    .content(comment)
                    .createdAt(new Temporal.DateTime(new Date(), 0))
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(newComment),
                    success -> {
                        Log.i(TAG, "ItemDetailsActivity(): Comment added Successfully" + success.toString());
                        runOnUiThread(() -> {
                            Snackbar.make(findViewById(R.id.itemDetails), "Comment Added", Snackbar.LENGTH_SHORT).show();
                            commentsAdapter.notifyDataSetChanged();
                            queryComments();
                        });
                        commentEditText.setText("");
                    },
                    failure -> {
                        Log.e(TAG, "ItemDetailsActivity(): Failure in adding Comment" + failure.toString());
                    }
            );
        });
        queryComments();
    }

    private void queryComments() {
        Amplify.API.query(
                ModelQuery.list(Comment.class),
                success -> {
                    Log.i(TAG, "ItemDetailsActivity(): Comment Read Successfully");
                    commentList.clear();
                    for (Comment userComment : success.getData()) {
                        commentList.add(userComment);
                    }
                    runOnUiThread(() -> {
                        commentsAdapter.notifyDataSetChanged();
                    });
                },
                failure -> Log.i(TAG, "ItemDetailsActivity(): Read Comment Failed")
        );
    }




}