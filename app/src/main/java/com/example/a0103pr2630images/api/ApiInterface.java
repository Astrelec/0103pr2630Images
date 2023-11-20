package com.example.a0103pr2630images.api;

import com.example.a0103pr2630images.data.Albums;
import com.example.a0103pr2630images.data.Photos;
import com.example.a0103pr2630images.data.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/users")
    Call<List<Users>> getUsers();
    @GET("/albums")
    Call<List<Albums>> getAlbums(@Query("userId") int userId);
    @GET("/photos")
    Call<List<Photos>> getPhotos(@Query("albumId") int albumId);
}
