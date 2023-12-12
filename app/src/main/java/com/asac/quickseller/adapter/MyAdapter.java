package com.asac.quickseller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Post;
import com.asac.quickseller.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    ArrayList<Post> items;

    public MyAdapter(Context context, ArrayList<Post> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.all_post_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = items.get(position);
        if (post != null) {
            String title = post.getTitle();
            String price = post.getPrice();

            // Check for null values and set default if needed
            if (title != null && !title.isEmpty()) {
                holder.nameView.setText(title);
            } else {
                holder.nameView.setText("N/A");
            }

            if (price != null && !price.isEmpty()) {
                holder.priceView.setText(price);
            } else {
                holder.priceView.setText("N/A");
            }

            // Use Glide to load the image from the URL
            if (!post.getImages().isEmpty()) {
                String imageUrl = post.getImages().get(0); // Assuming the first image URL
                RequestOptions requestOptions = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL); // Optional: You can customize the RequestOptions

                Glide.with(context)
                        .load(imageUrl)
                        .apply(requestOptions)
                        .into(holder.imageView);
            } else {
                // Handle the case where there are no images
                holder.imageView.setImageResource(R.drawable.logoooo); // Provide a default image resource
            }
        } else {
            // Handle the case where the Post object is null (optional)
            holder.nameView.setText("N/A");
            holder.priceView.setText("N/A");
            holder.imageView.setImageResource(R.drawable.logoooo); // Provide a default image resource
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}