package com.example.a0103pr2630images.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.a0103pr2630images.R;
import com.example.a0103pr2630images.adapters.UsersAdapter;
import com.example.a0103pr2630images.api.ApiClient;
import com.example.a0103pr2630images.api.ApiInterface;
import com.example.a0103pr2630images.data.Users;
import com.example.a0103pr2630images.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<Users> usersList = new ArrayList<>();
    UsersAdapter usersAdapter;
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getUsers();
        UsersAdapter.OnUserClickListener onUserClickListener = ((user, position) -> {
            Intent intent = new Intent(MainActivity.this, AlbumsActivity.class);
            intent.putExtra("userId", user.getId());
            startActivity(intent);
        });
        usersAdapter = new UsersAdapter(usersList, getApplicationContext(), onUserClickListener);
    }

    public void getUsers() {
        Call<List<Users>> listUsers = apiInterface.getUsers();
        listUsers.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (!response.isSuccessful()) {
                    Log.e("ErrorResponse", response.code() + "");
                    return;
                }
                usersList = response.body();
                usersAdapter.changeData(usersList);
                binding.recyclerViewUsers.setAdapter(usersAdapter);
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Log.w("Code", t.getLocalizedMessage() + "");
            }
        });
    }
}