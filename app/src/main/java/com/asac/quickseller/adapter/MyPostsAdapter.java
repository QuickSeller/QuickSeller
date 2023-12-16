package com.asac.quickseller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Post;
import com.asac.quickseller.Activities.EditMyPostActivity;
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

        // Add click listener for the "Edit" button
        holder.editButton.setOnClickListener(view -> {
            if (onEditClickListener != null) {
                onEditClickListener.onEditClick(position);
            }
        });
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    private OnDeleteClickListener onDeleteClickListener;

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        onDeleteClickListener = listener;
    }

    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    private OnEditClickListener onEditClickListener;

    public void setOnEditClickListener(OnEditClickListener listener) {
        onEditClickListener = listener;
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

        public MyPostsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.nameTextViewMyPost);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextViewMyPost);
            priceTextView = itemView.findViewById(R.id.priceTextViewMyPost);
            editButton = itemView.findViewById(R.id.editMyPostButton);

            // Add click listener for the "Delete" button
            Button deleteButton = itemView.findViewById(R.id.deleteMyPostButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onDeleteClickListener != null) {
                        onDeleteClickListener.onDeleteClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
