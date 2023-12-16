package com.asac.quickseller.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Post;
import com.amplifyframework.datastore.generated.model.User;
import com.asac.quickseller.R;
import com.asac.quickseller.adapter.MyPostsAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MyPostsActivity extends AppCompatActivity implements MyPostsAdapter.OnDeleteClickListener, MyPostsAdapter.OnEditClickListener{

    private RecyclerView recyclerView;
    private MyPostsAdapter myPostsAdapter;
    private List<Post> myPosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

        recyclerView = findViewById(R.id.recyclerViewMyPosts);

        myPostsAdapter = new MyPostsAdapter(this, myPosts);
        myPostsAdapter.setOnDeleteClickListener(this);
        myPostsAdapter.setOnEditClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myPostsAdapter);
            queryMyPosts();
    }

    private void queryMyPosts() {
        AuthUser authUser = Amplify.Auth.getCurrentUser();
        if (authUser != null) {
            Amplify.API.query(
                    ModelQuery.list(User.class, User.EMAIL.eq(authUser.getUsername())),
                    userResponse -> {
                        Iterator<User> userIterator = userResponse.getData().iterator();
                        if (userIterator.hasNext()) {
                            User user = userIterator.next();
                            String userId = user.getId();

                            Amplify.API.query(
                                    ModelQuery.list(Post.class, Post.USER.eq(userId)),
                                    postResponse -> {
                                        List<Post> userPosts = new ArrayList<>();
                                        if (postResponse.hasData()) {
                                            userPosts.addAll((Collection<? extends Post>) postResponse.getData().getItems());
                                        }

                                        runOnUiThread(() -> {
                                            myPosts.clear();
                                            myPosts.addAll(userPosts);
                                            myPostsAdapter.notifyDataSetChanged();
                                            Log.i("Done" , "Done");

                                        });
                                    },
                                    postError -> {
                                        Log.i("Error" , "Error");
                                    }
                            );
                        }
                    },
                    userError -> {
                        Log.i("Error2" , "Error2");
                    }
            );
        }
    }


    @Override
    public void onDeleteClick(int position) {
        Post postToDelete = myPosts.get(position);
        deletePost(postToDelete);
    }

    private void deletePost(Post post) {
        Amplify.API.mutate(
                ModelMutation.delete(post),
                response -> {
                    runOnUiThread(() -> {
                        myPosts.remove(post);
                        myPostsAdapter.notifyItemRemoved(myPosts.indexOf(post));
                    });
                },
                error -> {
                    Log.e("DeleteError", "Error deleting post: " + error.getMessage());
                }
        );
    }
    @Override
    public void onEditClick(int position) {
        // Retrieve the selected post
        Post selectedPost = myPosts.get(position);

        // Create an Intent to start EditMyPostActivity
        Intent intent = new Intent(this, EditMyPostActivity.class);

        // Pass the data of the selected post to EditMyPostActivity
        intent.putExtra("postId", selectedPost.getId());
        intent.putExtra("postTitle", selectedPost.getTitle());
        intent.putExtra("postDescription", selectedPost.getDescription());
        intent.putExtra("postPrice", selectedPost.getPrice());

        // Start the activity
        startActivity(intent);
    }




}