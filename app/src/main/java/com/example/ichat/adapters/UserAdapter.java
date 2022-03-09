package com.example.ichat.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ichat.Model.User;
import com.example.ichat.R;
import com.example.ichat.ViewHolder.UserViewHolder;
import com.example.ichat.activityes.ChatActivity;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    Context context;

    List<User> userList;

    public UserAdapter(Context context, List<User> userList, FirebaseUser firebaseUser) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_user,parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User user = userList.get(position);
        holder.name.setText(user.getUserName());
        holder.email.setText(user.getUserEmail());
        holder.phone.setText(user.getUserPhone());
        Picasso.get().load(user.getUserProfilePic()).into(holder.profilepic);

        holder.sendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("userId",user.getUserId());
                context.startActivities(new Intent[]{intent});
            }
        });

    }

    @Override
    public int getItemCount() {

        return userList.size();
    }
}
