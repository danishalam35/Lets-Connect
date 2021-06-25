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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;

    EditText emailbox, passwordBox, nameBox;
    Button loginBtn, signupBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        emailbox = findViewById(R.id.emailBoxs);
        nameBox = findViewById(R.id.nameBoxs);
        passwordBox = findViewById(R.id.passwordBoxs);
        loginBtn = findViewById(R.id.AlloginBtn);
        signupBtn = findViewById(R.id.createBtn);
        firebaseAuth=FirebaseAuth.getInstance();
        dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait....");
        databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
                String emails, passes, names;
                emails = emailbox.getText().toString();
                passes = passwordBox.getText().toString();
                names = nameBox.getText().toString();
                // create obj and set the data
                final User user = new User();
                user.setEmail(emails);
                user.setPass(passes);
                user.setName(names);


                firebaseAuth.createUserWithEmailAndPassword(emails, passes).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            startActivity(new Intent(SignupActivity.this,SignActivity.class));


                         User user= new User(emails,names,passes);

                         FirebaseDatabase.getInstance().getReference("Users")
                                 .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {

                                     }
                                 });



                        } else {
                            Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,SignActivity.class));
            }
        });

    }
}