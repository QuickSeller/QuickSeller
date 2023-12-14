package com.asac.quickseller.Activities;

import static com.amplifyframework.core.model.query.Where.id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Comment;
import com.amplifyframework.datastore.generated.model.User;
import com.asac.quickseller.R;
import com.asac.quickseller.adapter.CommentsAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemDetailsActivity extends AppCompatActivity {
    public static final String TAG = "ItemDetailsActivity";
    Button addCommentBtn=null;
    EditText commentEditText = null;
    List<Comment> commentList = null;
    CommentsAdapter commentsAdapter;
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
//        TextView cityTextView = findViewById(R.id.itemDetailsItemCity);
        TextView descriptionTextView = findViewById(R.id.itemDetailsItemDescription);
//        TextView priceTextView = findViewById(R.id.itemDetailsItemPrice);
//        ImageView imageView = findViewById(R.id.itemDetailsImageView);

//        cityTextView.setText(city);
        titleTextView.setText(title);
        descriptionTextView.setText(description);
//        priceTextView.setText(price);


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


    }



    private void addComments(){
        addCommentBtn = (Button) findViewById(R.id.HomePageAddCommentBtn);
        commentEditText = (EditText) findViewById(R.id.HomePageEditTextComment);
        AuthUser authUser = Amplify.Auth.getCurrentUser();
        addCommentBtn.setOnClickListener(b -> {

            String comment = commentEditText.getText().toString();

//            if (authUser != null) {
//                        .id(authUser.getUserId())
//                        .username(authUser.getUsername())
////                        .email(authUser.getEmail())
////                        .phoneNumber(authUser.getUserAttribute("phone_number")) // adjust based on your AuthUser properties
////                        .image(authUser.getUserAttribute("image")) // adjust based on your AuthUser properties
////                        .build();
////            }
                Comment newComment = Comment.builder()
                        .content(comment)
                        .createdAt(new Temporal.DateTime(new Date(), 0))
                        .build();

            Amplify.API.mutate(
                    ModelMutation.create(newComment),
                    success -> {
                        Log.i(TAG, "HomeActivity(): Comment added Successfully" + success.toString());
                        runOnUiThread(() -> {
                            Snackbar.make(findViewById(R.id.itemDetails),"Comment Added", Snackbar.LENGTH_SHORT).show();
                        });
                        commentEditText.setText("");
                        },
                    failure -> {
                        Log.e(TAG, "HomeActivity(): Failure in adding Comment" + failure.toString());
                    }
            );
        });

    }
}