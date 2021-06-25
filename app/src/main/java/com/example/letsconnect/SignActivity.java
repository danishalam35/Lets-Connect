package com.example.letsconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignActivity extends AppCompatActivity {

    EditText emailbox,passwordBox;
    Button loginBtn,signupBtn;
    FirebaseAuth auth;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        emailbox=findViewById(R.id.emailBox);
        passwordBox=findViewById(R.id.passwordBox);
        loginBtn=findViewById(R.id.loginBtn);
        signupBtn=findViewById(R.id.createBtn);
        auth=FirebaseAuth.getInstance();
        dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait....");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String email,password;
                email=emailbox.getText().toString();
                password=passwordBox.getText().toString();

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()){

                            startActivity(new Intent(SignActivity.this,MeetingActivity.class));

                            Toast.makeText(SignActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(SignActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignActivity.this,SignupActivity.class));
            }
        });

    }
}