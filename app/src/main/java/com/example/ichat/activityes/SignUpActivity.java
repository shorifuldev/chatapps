package com.example.ichat.activityes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ichat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText userEmail;
    private TextInputEditText userPassword;
    private TextInputEditText userName;
    private TextInputEditText userPhone;
    private TextView haveanaccoutn;
    private Button signUpBtn;
    String currentUser;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        userName = findViewById(R.id.userNameId);
        userPhone = findViewById(R.id.userPhoneId);
        userEmail = findViewById(R.id.userEmailId);
        userPassword = findViewById(R.id.userPasswordId);
        signUpBtn=findViewById(R.id.signUpbtnId);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString().trim();
                String phone = userPhone.getText().toString().trim();
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                if(name.isEmpty()){
                    userName.setError("Enter a Valide Name");
                    userName.requestFocus();
                }
                else if(phone.isEmpty()){
                    userPhone.setError("Enter a Valide Phone");
                    userPhone.requestFocus();
                }
                else if(email.isEmpty()){
                    userEmail.setError("Enter a Valide Email");
                    userEmail.requestFocus();
                }
                else if (password.isEmpty() || password.length()<6 ) {
                    userPassword.setError("Enter a Valide Password");
                    userPassword.requestFocus();
                }

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            firebaseUser = firebaseAuth.getCurrentUser();

                            if(firebaseUser!=null){
                                currentUser=firebaseUser.getUid();
                            }
                            HashMap<String,String> userMap = new HashMap<>();
                            userMap.put("UserName",name);
                            userMap.put("UserPhone",phone);
                            userMap.put("UserEmail",email);
                            userMap.put("UserPassword",password);
                            userMap.put("UserId",currentUser);

                            databaseReference.child(currentUser).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "Create accoutn successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                                        finish();
                                    }
                                }
                            });
                        }
                        else {
                            if(task.getException()instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(SignUpActivity.this, "Allready have a account", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(SignUpActivity.this, "Not Successfull", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
        haveanaccoutn = findViewById(R.id.haveanaccoutnId);
        haveanaccoutn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
            }
        });
    }
}