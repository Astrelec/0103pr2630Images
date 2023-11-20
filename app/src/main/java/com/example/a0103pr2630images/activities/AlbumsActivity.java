package com.example.a0103pr2630images.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.a0103pr2630images.R;
import com.example.a0103pr2630images.adapters.AlbumsAdapter;
import com.example.a0103pr2630images.api.ApiClient;
import com.example.a0103pr2630images.api.ApiInterface;
import com.example.a0103pr2630images.data.Albums;
import com.example.a0103pr2630images.databinding.ActivityAlbumsBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumsActivity extends AppCompatActivity {

    ActivityAlbumsBinding binding;
    List<Albums> albumsList = new ArrayList<>();
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    AlbumsAdapter albumsAdapter;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        binding = ActivityAlbumsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        getAlbums();
        AlbumsAdapter.OnAlbumClickListener onAlbumClickListener = new AlbumsAdapter.OnAlbumClickListener() {
            @Override
            public void onAlbumClick(Albums album, int position) {
                Intent intent = new Intent(AlbumsActivity.this, ImagesActivity.class);
                intent.putExtra("albumId", album.getId());
                startActivity(intent);
            }
        };
        albumsAdapter = new AlbumsAdapter(AlbumsActivity.this, albumsList, onAlbumClickListener);
    }
    public void getAlbums(){
        Call<List<Albums>> listAlbum = apiInterface.getAlbums(userId);
        listAlbum.enqueue(new Callback<List<Albums>>() {
            @Override
            public void onResponse(Call<List<Albums>> call, Response<List<Albums>> response) {
                if (!response.isSuccessful()){
                    Log.e("CODE", response.code()+"");
                    return;
                }
                albumsList = response.body();
                albumsAdapter.changeData(albumsList);
                binding.recyclerViewAlbums.setAdapter(albumsAdapter);
            }

            @Override
            public void onFailure(Call<List<Albums>> call, Throwable t) {
                Log.w("Code", t.getLocalizedMessage()+ "");
            }
        });
    }
}