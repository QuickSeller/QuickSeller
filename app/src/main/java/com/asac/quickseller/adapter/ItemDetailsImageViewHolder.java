package com.asac.quickseller.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDetailsImageViewHolder extends RecyclerView.ViewHolder {

    public final ImageView imageView;

    public ItemDetailsImageViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(com.asac.quickseller.R.id.itemDetailsImageItemImageView);
    }
}