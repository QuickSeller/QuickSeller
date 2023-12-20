package com.asac.quickseller.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MyPostsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyPostsAdapter myPostsAdapter;
    private List<Post> myPosts = new ArrayList<>();

    ImageButton backBtn=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

        recyclerView = findViewById(R.id.recyclerViewMyPosts);
        myPostsAdapter = new MyPostsAdapter(this, myPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myPostsAdapter);

        queryMyPosts();
        setupBackBtn();
    }

    private void setupBackBtn(){
        backBtn = (ImageButton) findViewById(R.id.backFromMyPost);
        backBtn.setOnClickListener(b -> {
            Intent back = new Intent(MyPostsActivity.this, SettingsActivity.class);
            startActivity(back);
        });
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
                                            Log.i("Done", "Done");

                                        });
                                    },
                                    postError -> {
                                        Log.i("Error", "Error");
                                    }
                            );
                        }
                    },
                    userError -> {
                        Log.i("Error2", "Error2");
                    }
            );
        }
    }


}