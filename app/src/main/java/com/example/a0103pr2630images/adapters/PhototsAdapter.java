package com.example.a0103pr2630images.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a0103pr2630images.R;
import com.example.a0103pr2630images.data.Photos;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class PhototsAdapter extends RecyclerView.Adapter<PhototsAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Photos> photosList;

    public PhototsAdapter(Context context, List<Photos> photosList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.photosList = photosList;
    }

    @NonNull
    @Override
    public PhototsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.photo_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PhototsAdapter.ViewHolder holder, int position) {
        Photos photo = photosList.get(position);
        holder.tvAlbumId.setText("AlbumID: " + photo.getAlbumId());
        holder.tvId.setText("ID: " + photo.getId());
        holder.tvTitle.setText("Title: " + photo.getTitle());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(photo.getThumbnailUrl());
                    Bitmap image = BitmapFactory.decodeStream(new URL(photo.getThumbnailUrl()).openConnection().getInputStream());
                    holder.img.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.img.setImageBitmap(image);
                        }
                    });
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        });
        thread.start();
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void changeData(List<Photos> photosList) {
        this.photosList = photosList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvAlbumId, tvId, tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAlbumId = itemView.findViewById(R.id.textViewPhotoItemAlbumId);
            tvId = itemView.findViewById(R.id.textViewPhotoItemId);
            tvTitle = itemView.findViewById(R.id.textViewPhotoItemTitle);
            img = itemView.findViewById(R.id.imageViewPhotoItem);
        }
    }
}
