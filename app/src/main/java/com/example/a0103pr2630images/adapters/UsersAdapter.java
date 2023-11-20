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
import com.example.a0103pr2630images.data.Users;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    public interface OnUserClickListener{
        void onUserClick(Users user, int position);
    }
    private OnUserClickListener onClickListener;
    private LayoutInflater layoutInflater;
    private List<Users> usersList;
    public UsersAdapter(List<Users> usersList, Context context, OnUserClickListener onClickListener){
        this.layoutInflater = LayoutInflater.from(context);
        this.usersList = usersList;
        this.onClickListener = onClickListener;
    }
    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void changeData(List<Users> usersList){
        this.usersList = usersList;
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Users user = usersList.get(position);
        holder.tvUserId.setText("ID: " + user.getId());
        holder.tvName.setText("Name: " + user.getName());
        holder.tvUsername.setText("Username: " + user.getUsername());
        holder.tvEmail.setText("Email: " + user.getEmail());
        holder.tvPhone.setText("Phone: " + user.getPhone());
        holder.tvWebsite.setText("Website: " + user.getWebsite());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onUserClick(user,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserId, tvName, tvUsername, tvEmail, tvPhone, tvWebsite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserId = itemView.findViewById(R.id.textViewUserItemId);
            tvName = itemView.findViewById(R.id.textViewUserItemName);
            tvUsername = itemView.findViewById(R.id.textViewUserItemUsername);
            tvEmail = itemView.findViewById(R.id.textViewUserItemEmail);
            tvPhone = itemView.findViewById(R.id.textViewUserItemPhone);
            tvWebsite = itemView.findViewById(R.id.textViewUserItemWebsite);
        }
    }
}
