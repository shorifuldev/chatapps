package com.example.ichat.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ichat.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatUserViewHolder extends RecyclerView.ViewHolder {

    private CardView cardView;
    private CircleImageView profileImg;
    private TextView name;

    public ChatUserViewHolder(@NonNull View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.userCardId);
        profileImg = itemView.findViewById(R.id.profileImg);
        name = itemView.findViewById(R.id.nameId);

    }
}
