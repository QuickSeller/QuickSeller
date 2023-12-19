package com.asac.quickseller.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Post;
import com.asac.quickseller.Activities.ItemDetailsActivity;
import com.asac.quickseller.R;

import java.io.File;
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
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.nameView.setText(items.get(position).getTitle());
//        holder.priceView.setText(items.get(position).getPrice());
//        holder.imageView.setImageResource(items.get(position).getImages().indexOf(0)); /////////////

        Post post = items.get(position);


        if (post != null) {
            String title = post.getTitle();
            String price = post.getPrice();

//            Temporal.DateTime createdAt = post.getCreatedAt();
//            if (createdAt != null) {
//                String formattedDate = formatDate(createdAt);
//                holder.dateView.setText(formattedDate);
//            } else {
//                holder.dateView.setText("N/A");
//            }

            if (title != null && !title.isEmpty()) {
                holder.nameView.setText(title);
            } else {
                holder.nameView.setText("N/A");
            }

            if (price != null && !price.isEmpty()) {
                holder.priceView.setText(price +" JD");
            } else {
                holder.priceView.setText("N/A");
            }

//            holder.imageView.setImageResource(R.drawable.logoooo);

            if (post.getImages() != null && !post.getImages().isEmpty())
            {
                Amplify.Storage.downloadFile(
                        post.getImages().get(0),
                        new File(context.getFilesDir(), post.getImages().get(0)),
                        success ->
                        {
                            holder.imageView.setImageURI(Uri.parse(success.getFile().getPath()));
                            holder.imageView.setImageBitmap(BitmapFactory.decodeFile(success.getFile().getPath()));
                        },
                        failure ->
                        {
                            Log.e("TAG", "Unable to get image from S3 for the product for S3 key: " + post.getImages() + " for reason: " + failure.getMessage());
                        }
                );
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ItemDetailsActivity.class);
                    intent.putExtra("postId", post.getId());
                    intent.putExtra("city", post.getCity());
                    intent.putExtra("title", post.getTitle());
                    intent.putExtra("description", post.getDescription());
                    intent.putExtra("price", post.getPrice());
                    intent.putExtra("owner", post.getUser().getUsername());
                    intent.putExtra("images", post.getImages().toArray(new String[0]));
                    intent.putExtra("date", formatDate(post.getCreatedAt()));
                    intent.putExtra("phone", post.getUser().getPhoneNumber());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }
            });


        }
    }

    private String formatDate(Temporal.DateTime dateTime) {
        // You can use SimpleDateFormat or DateTimeFormatter for more advanced formatting
        // For simplicity, let's use substring to extract date and time
        String dateTimeString = dateTime.toString();
        if (dateTimeString.length() >= 19) {
            return dateTimeString.substring(34, dateTimeString.length()-10);
        } else {
            return dateTimeString;
        }
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