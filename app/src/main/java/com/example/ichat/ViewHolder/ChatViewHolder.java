package com.example.ichat.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ichat.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    public TextView msgTv;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);

        msgTv = itemView.findViewById(R.id.msgTxtview);

    }
}
