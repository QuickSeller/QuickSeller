package com.asac.quickseller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        Comment comment = comments.get(position);
        String commentId = comment.getId();
        String commentContent = comment.getContent();
        commentTextView.setText(commentContent);

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
