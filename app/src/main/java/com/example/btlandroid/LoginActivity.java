package com.example.btlandroid;

import androidx.annotation.NonNull;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    DatabaseReference userDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);
        userDatabase=FirebaseDatabase.getInstance().getReference("users");
        final EditText txt_username = findViewById(R.id.username);
        final EditText txt_password = findViewById(R.id.password);
        final Button button_login = findViewById(R.id.login);


        checkAlreadyLoggedIn();

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_username.getText().toString().isEmpty()){
                    txt_username.setError("Please Enter Username");
                    txt_username.requestFocus();
                    return;
                }
                if(txt_password.getText().toString().isEmpty()){
                    txt_password.setError("Please Enter Password");
                    txt_password.requestFocus();
                    return;
                }

                ProcessLogin(txt_username.getText().toString(), txt_password.getText().toString());
            }
        });
    }

    private void checkAlreadyLoggedIn() {
        if(StaticUtils.getUserEmail(LoginActivity.this)!=null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
    }

    private void ProcessLogin(final String email, final String password) {
        userDatabase.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String username1=dataSnapshot.child("email").getValue(String.class);
                    String password1=dataSnapshot.child("password").getValue(String.class);
                    String created=dataSnapshot.child("created_at").getValue(String.class);
                    if(password1.equalsIgnoreCase(password)){

                        StaticUtils.StoreLoggedEmail(LoginActivity.this,email);
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();

                    }
                    else{

                        Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    CreateNewUser(email,password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void CreateNewUser(String email,String password) {

        String id=userDatabase.push().getKey();

        UserModel userModel=new UserModel(id,email,password);

        userDatabase.child(email).setValue(userModel);
        StaticUtils.StoreLoggedEmail(LoginActivity.this,email);
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }


}