package com.asac.quickseller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Post;
import com.asac.quickseller.Activities.ItemDetailsActivity;
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
        View view = LayoutInflater.from(context).inflate(R.layout.all_post_view, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = items.get(position);

        holder.nameView.setText(items.get(position).getTitle());
        holder.priceView.setText(items.get(position).getPrice());
        holder.imageView.setImageResource(items.get(position).getImages().indexOf(0)); /////////////

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemDetailsActivity.class);
                intent.putExtra("postId", post.getId());
                intent.putExtra("city", post.getCity());
                intent.putExtra("title", post.getTitle());
                intent.putExtra("description", post.getDescription());
                intent.putExtra("price", post.getPrice());
                intent.putExtra("productCategory", post.getProductCategory());
                intent.putExtra("images", post.getImages().toArray(new String[0]));

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });

    }
    public void filterList(List<Post> filteredList) {
        items = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
