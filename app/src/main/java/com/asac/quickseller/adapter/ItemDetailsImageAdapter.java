package com.asac.quickseller.adapter;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.asac.quickseller.R;

import java.io.File;
import java.util.List;

public class ItemDetailsImageAdapter extends RecyclerView.Adapter<ItemDetailsImageViewHolder> {

    private final List<String> images;
    private final Context context;

    public ItemDetailsImageAdapter(List<String> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemDetailsImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_details_image_item, parent, false);
        return new ItemDetailsImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDetailsImageViewHolder holder, int position) {
        String imageUrl = images.get(position);

        Amplify.Storage.downloadFile(
                imageUrl,
                new File(context.getFilesDir(), imageUrl),
                success -> {
                    runOnUiThread(() -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(success.getFile().getPath());
                        holder.imageView.setImageBitmap(bitmap);
                    });
                },
                failure -> {
                    Log.e("TAG", "Unable to get image from S3 for reason: " + failure.getMessage());
                }
        );
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}