package com.example.a0103pr2630images.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.a0103pr2630images.R;
import com.example.a0103pr2630images.adapters.PhototsAdapter;
import com.example.a0103pr2630images.api.ApiClient;
import com.example.a0103pr2630images.api.ApiInterface;
import com.example.a0103pr2630images.data.Photos;
import com.example.a0103pr2630images.databinding.ActivityImagesBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImagesActivity extends AppCompatActivity {
    ActivityImagesBinding binding;
    int albumId;
    List<Photos> photosList;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    PhototsAdapter phototsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        binding = ActivityImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        albumId = intent.getIntExtra("albumId", 0);
        getPhotos();
        phototsAdapter = new PhototsAdapter(ImagesActivity.this, photosList);
    }

    public void getPhotos() {
        Call<List<Photos>> listPhotos = apiInterface.getPhotos(albumId);
        listPhotos.enqueue(new Callback<List<Photos>>() {
            @Override
            public void onResponse(Call<List<Photos>> call, Response<List<Photos>> response) {
                if (!response.isSuccessful()) {
                    Log.e("CODE", response.code() + "");
                    return;
                }
                photosList = response.body();
                phototsAdapter.changeData(photosList);
                binding.recyclerViewPhotos.setAdapter(phototsAdapter);
            }

            @Override
            public void onFailure(Call<List<Photos>> call, Throwable t) {
                Log.w("Code", t.getLocalizedMessage() + "");
            }
        });
    }
}