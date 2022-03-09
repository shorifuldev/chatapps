package com.example.ichat.activityes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ichat.Model.Chat;
import com.example.ichat.Model.User;
import com.example.ichat.R;
import com.example.ichat.adapters.ChatAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    Intent intent;
    String receiverId, senderId;

    private CircleImageView profileImg;
    private TextView nameTxt;
    private EditText typemsg;
    private ImageView sendmsg;
    private RecyclerView chatRecycler;

    List<Chat> chatList;
    ChatAdapter chatAdapter;

    FirebaseUser currentfirebaseuser;
    DatabaseReference databaseReference;
    DatabaseReference chatdatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        intent = getIntent();

        receiverId = intent.getStringExtra("userId");
        currentfirebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentfirebaseuser != null){
            senderId = currentfirebaseuser.getUid();
        }

        profileImg = findViewById(R.id.profile_image);
        nameTxt = findViewById(R.id.nameTxt);
        typemsg = findViewById(R.id.typeMsg);
        sendmsg = findViewById(R.id.sendmsg);
        chatRecycler = findViewById(R.id.chatRecyclerId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ChatActivity.this);
        layoutManager.setStackFromEnd(true);
        chatRecycler.setLayoutManager(layoutManager);

        chatList = new ArrayList<>();


        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(receiverId);
        chatdatabaseReference = FirebaseDatabase.getInstance().getReference("Chats");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Picasso.get().load(user.getUserProfilePic()).into(profileImg);
                nameTxt.setText(user.getUserName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 String chatId = chatdatabaseReference.push().getKey();
                 String msg_str = typemsg.getText().toString();

                 HashMap<String,Object> chatMap = new HashMap<>();
                 chatMap.put("sender",senderId);
                 chatMap.put("receiver",receiverId);
                 chatMap.put("message",msg_str);
                 chatMap.put("chatId",chatId);

                 chatdatabaseReference.child(chatId).setValue(chatMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful()){
                             typemsg.getText().clear();
                         }
                     }
                 });


                 DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Chatlist").child(senderId).child(receiverId);
                 reference1.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {

                         if (!snapshot.exists()){
                             reference1.child("id").setValue(receiverId);
                         }

                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });

            }
        });

        chatdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if(senderId.equals(chat.getSender()) && receiverId.equals(chat.getReceiver())
                    || senderId.equals(chat.getReceiver()) && receiverId.equals(chat.getSender())){
                        chatList.add(chat);
                    }
                }

                chatAdapter = new ChatAdapter(ChatActivity.this,chatList);
                chatRecycler.setAdapter(chatAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



}