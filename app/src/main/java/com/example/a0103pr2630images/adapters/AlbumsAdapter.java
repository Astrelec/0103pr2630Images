package com.example.a0103pr2630images.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a0103pr2630images.R;
import com.example.a0103pr2630images.data.Albums;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {
    public interface OnAlbumClickListener{
        void onAlbumClick(Albums album, int position);
    }
    private OnAlbumClickListener onClickListener;
    private LayoutInflater layoutInflater;
    private List<Albums> albumsList;
    public AlbumsAdapter(Context context, List<Albums> albumsList, OnAlbumClickListener onClickListener){
        this.onClickListener = onClickListener;
        this.layoutInflater = LayoutInflater.from(context);
        this.albumsList = albumsList;
    }
    @NonNull
    @Override
    public AlbumsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.album_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AlbumsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Albums album = albumsList.get(position);
        holder.tvId.setText("ID: " + album.getId());
        holder.tvUserId.setText("UserID: " + album.getUserId());
        holder.tvTitle.setText("Title: " + album.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onAlbumClick(album, position);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public  void changeData(List<Albums> albumsList){
        this.albumsList = albumsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return albumsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserId, tvId, tvTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserId = itemView.findViewById(R.id.textViewAlbumItemUserId);
            tvId = itemView.findViewById(R.id.textViewAlbumItemId);
            tvTitle = itemView.findViewById(R.id.textViewAlbumItemTitle);
        }
    }
}
