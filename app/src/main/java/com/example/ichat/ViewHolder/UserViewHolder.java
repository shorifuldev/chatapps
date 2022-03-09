package com.example.ichat.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ichat.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView profilepic;
    public ImageView sendImg;
    public TextView name,email,phone;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        profilepic = itemView.findViewById(R.id.profileProfile);
        sendImg = itemView.findViewById(R.id.sendImgId);
        name = itemView.findViewById(R.id.nameTxtId);
        email = itemView.findViewById(R.id.emailTxtId);
        phone = itemView.findViewById(R.id.phoneTxtId);


    }
}
