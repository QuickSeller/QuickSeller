package com.asac.quickseller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Comment;
import com.asac.quickseller.R;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.commnetsViewHolder> {


    List<Comment> comments ;
    Context callingView;

    public CommentsAdapter(List<Comment> comments, Context callingView) {
        this.comments = comments;
        this.callingView = callingView;
    }

    @NonNull
    @Override
    public commnetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View commentsFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_recycler_view,parent,false);
        return new commnetsViewHolder(commentsFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull commnetsViewHolder holder, int position) {
        TextView commentTextView = (TextView) holder.itemView.findViewById(R.id.commentTextViewFragment);
        TextView usernameTextView = holder.itemView.findViewById(R.id.usernameTextView);
        TextView createdAtTextView = holder.itemView.findViewById(R.id.createdAtTextView);

        Comment comment = comments.get(position);
        String commentContent = comment.getContent();
        String username = comment.getUser().getUsername();
        Temporal.DateTime createdAt = comment.getCreatedAt();

        commentTextView.setText(commentContent);
        usernameTextView.setText( username);
        createdAtTextView.setText("Date: " + formatDate(createdAt));

    }

    private String formatDate(Temporal.DateTime dateTime) {
        String dateTimeString = dateTime.toString();
        if (dateTimeString.length() >= 19) {
            return dateTimeString.substring(34, dateTimeString.length()-10);
        } else {
            return dateTimeString;
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class commnetsViewHolder extends RecyclerView.ViewHolder {
        public commnetsViewHolder(@NonNull View itemView) {
            super(itemView);}
    }

}