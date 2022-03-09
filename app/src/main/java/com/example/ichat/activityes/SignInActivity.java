package com.example.ichat.activityes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ichat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {


    private TextInputEditText userEmail;
    private TextInputEditText userPassword;
    private TextView donthaveaccoutn;
    private Button signInBtn;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();

        userEmail = findViewById(R.id.userEmailId);
        userPassword = findViewById(R.id.userPasswordId);

        signInBtn = findViewById(R.id.signInBtnId);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                if(email.isEmpty()){
                    userEmail.setError("Email is empty");
                    userEmail.requestFocus();
                }
                else if(password.isEmpty()){
                    userPassword.setError("Password is empty");
                    userPassword.requestFocus();
                }
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(SignInActivity.this, "Password not match!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        donthaveaccoutn = findViewById(R.id.havent_accoutnId);
        donthaveaccoutn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });
    }
}