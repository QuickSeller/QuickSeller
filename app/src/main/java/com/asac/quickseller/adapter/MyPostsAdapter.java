package com.asac.quickseller.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Post;
import com.asac.quickseller.Activities.EditPostActivity;
import com.asac.quickseller.R;

import java.util.List;

public class MyPostsAdapter extends RecyclerView.Adapter<MyPostsAdapter.MyPostsViewHolder> {

    private List<Post> posts;
    private Context context;

    public MyPostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }
    public void updateData(List<Post> newPosts) {
        this.posts.clear();
        this.posts.addAll(newPosts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyPostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_mypostrecycle_us, parent, false);
        return new MyPostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostsViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.titleTextView.setText(post.getTitle());
        holder.descriptionTextView.setText(post.getDescription());
        holder.priceTextView.setText("$" + post.getPrice());
        holder.editButton.setOnClickListener(view -> onEditButtonClick(post));
        holder.deleteButton.setOnClickListener(view -> onDeleteButtonClick(post));
    }


    private void onEditButtonClick(Post post) {
        Log.i("EditButton", "Edit button clicked for post: " + post.getId());
        Intent intent = new Intent(context, EditPostActivity.class);
        intent.putExtra("postId", post.getId());
        context.startActivity(intent);
    }

    private void onDeleteButtonClick(Post post) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete this post?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            deletePost(post);
        });

        builder.setNegativeButton("No", (dialog, which) -> {
        });

        builder.show();
    }

    private void deletePost(Post post) {
        Amplify.API.mutate(ModelMutation.delete(post),
                success -> {
                    // Post deleted successfully, update the UI
                    posts.remove(post);
                    notifyDataSetChanged();
                    Log.i("DeleteButton", "Post deleted successfully");
                },
                error -> {
                    Log.e("DeleteButton", "Error deleting post", error);
                });
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyPostsViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView descriptionTextView;
        TextView priceTextView;
        Button editButton;
        Button deleteButton;

        public MyPostsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.nameTextViewMyPost);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextViewMyPost);
            priceTextView = itemView.findViewById(R.id.priceTextViewMyPost);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

        }
    }

}