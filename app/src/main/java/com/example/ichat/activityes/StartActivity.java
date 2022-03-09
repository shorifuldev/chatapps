package com.example.ichat.activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ichat.R;
import com.google.firebase.auth.FirebaseAuth;


public class StartActivity extends AppCompatActivity {

    private AppCompatButton signUpBtn;
    private AppCompatButton signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        signUpBtn = findViewById(R.id.signUpBtnId);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,SignUpActivity.class));
            }
        });

        signInBtn = findViewById(R.id.signInBtnId);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,SignInActivity.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!= null){
            startActivity(new Intent(StartActivity.this, HomeActivity.class));
        }

    }
}