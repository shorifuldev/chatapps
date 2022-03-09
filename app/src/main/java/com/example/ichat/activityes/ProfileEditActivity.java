package com.example.ichat.activityes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ichat.Model.User;
import com.example.ichat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEditActivity extends AppCompatActivity {
    private ImageView coverpic, backImg;
    private CircleImageView profilePic;
    private EditText nameEditTxt,phoneEditTxt,emailEditTxt;
    private Button updatebtn;
    private ProgressBar progressBar;
    Picasso picasso,picasso2;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    String currentuserId;
    Uri uri,uri2;
    String coverPicUrl,profilePicUrl;
    ProgressDialog Dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            currentuserId = firebaseUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("User").child(currentuserId);
            storageReference = FirebaseStorage.getInstance().getReference("Upload").child(currentuserId);
        }
        Dialog = new ProgressDialog(this);
        Dialog.setTitle("Please wait...");
        Dialog.setMessage("Your profile is updating...");

        coverpic = findViewById(R.id.coverPicId);
        backImg = findViewById(R.id.backImg);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        profilePic = findViewById(R.id.profile_image);
        nameEditTxt = findViewById(R.id.nameEditText);
        phoneEditTxt = findViewById(R.id.phoneEditText);
        emailEditTxt = findViewById(R.id.emailEditText);
        updatebtn = findViewById(R.id.updatebtn);
        progressBar = findViewById(R.id.progressBar2);


        coverpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,2);
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name_str = nameEditTxt.getText().toString().trim();
                String Phone_str = phoneEditTxt.getText().toString().trim();
                String email_str = emailEditTxt.getText().toString().trim();

                StorageReference storageRef = storageReference.child("coverPic_"+Name_str);
                StorageReference storageRef2 = storageReference.child("profilePic_"+Name_str);
                Dialog.show();
                if(uri != null){
                    storageRef.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        coverPicUrl =String.valueOf(uri);
                                        updateuserInfo();
                                    }
                                });
                            }
                        }
                        private void updateuserInfo() {
                            HashMap<String, Object> updateMap = new HashMap<>();
                            updateMap.put("UserName",Name_str);
                            updateMap.put("UserPhone",Phone_str);
                            updateMap.put("UserEmail",email_str);
                            updateMap.put("UserCoverPic",coverPicUrl);

                            databaseReference.updateChildren(updateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(ProfileEditActivity.this, "Update Successfully...", Toast.LENGTH_SHORT).show();
                                    finish();

                                }
                            });
                        }
                    });
                }
                else if(uri2 != null){
                    storageRef2.putFile(uri2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                storageRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        profilePicUrl = String.valueOf(uri);
                                        updateuserInfo2();
                                    }
                                });
                            }
                        }
                        private void updateuserInfo2() {
                            HashMap<String, Object> updateMap = new HashMap<>();
                            updateMap.put("UserName",Name_str);
                            updateMap.put("UserPhone",Phone_str);
                            updateMap.put("UserEmail",email_str);
                            updateMap.put("UserProfilePic",profilePicUrl);

                            databaseReference.updateChildren(updateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(ProfileEditActivity.this, "Update Successfully...", Toast.LENGTH_SHORT).show();
                                    finish();

                                }
                            });
                        }
                    });

                }
                else {
                    HashMap<String, Object> updateMap = new HashMap<>();
                    updateMap.put("UserName",Name_str);
                    updateMap.put("UserPhone",Phone_str);
                    updateMap.put("UserEmail",email_str);

                    databaseReference.updateChildren(updateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Dialog.dismiss();
                            Toast.makeText(ProfileEditActivity.this, "Update Successfully...", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    });

                }
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user != null){
                    nameEditTxt.setText(user.getUserName());
                    phoneEditTxt.setText(user.getUserPhone());
                    emailEditTxt.setText(user.getUserEmail());

                    if(user.getUserCoverPic() != null && user.getUserProfilePic() != null){
                        picasso.get().load(user.getUserCoverPic()).into(coverpic);
                        picasso.get().load(user.getUserProfilePic()).into(profilePic);

                    }
                    else {
                        coverpic.setImageDrawable(getResources().getDrawable(R.drawable.cover));
                        profilePic.setImageDrawable(getResources().getDrawable(R.drawable.profile));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data != null){
            uri = data.getData();
            coverpic.setImageURI(uri);
        }
        else if( requestCode==2 && resultCode==RESULT_OK && data!=null){
            uri2 = data.getData();
            profilePic.setImageURI(uri2);
        }
    }
}