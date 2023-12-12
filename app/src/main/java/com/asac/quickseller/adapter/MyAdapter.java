package com.asac.quickseller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Post;
import com.asac.quickseller.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Post> items;

    public MyAdapter(Context context, List<Post> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.all_post_view , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getTitle());
        holder.priceView.setText(items.get(position).getPrice());
        holder.imageView.setImageResource(items.get(position).getImages().indexOf(0)); /////////////
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
