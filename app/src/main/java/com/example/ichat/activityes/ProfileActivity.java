package com.example.ichat.activityes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ichat.Model.User;
import com.example.ichat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private ImageView coverPic,editProfile;
    private CircleImageView profilePic;
    private TextView name,phone,email;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    Picasso picasso;

    String currentuserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            currentuserId = firebaseUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("User").child(currentuserId);
        }

        coverPic = findViewById(R.id.coverPicId);
        editProfile = findViewById(R.id.editProfile);
        profilePic = findViewById(R.id.profile_image);
        name = findViewById(R.id.nameTxt);
        phone = findViewById(R.id.phoneTxt);
        email = findViewById(R.id.emailTxt);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,ProfileEditActivity.class));
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user != null){
                name.setText("Name : "+user.getUserName());
                phone.setText("Phone : "+user.getUserPhone());
                email.setText("Email : "+user.getUserEmail());

                    if(user.getUserCoverPic() != null && user.getUserProfilePic() != null){
                         picasso.get().load(user.getUserCoverPic()).into(coverPic);
                         picasso.get().load(user.getUserProfilePic()).into(profilePic);

                    }
                    else {
                        coverPic.setImageDrawable(getResources().getDrawable(R.drawable.cover));
                        profilePic.setImageDrawable(getResources().getDrawable(R.drawable.profile));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}