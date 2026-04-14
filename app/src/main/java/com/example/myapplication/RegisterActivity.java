package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText name ,email, password,confirmPassword;
    Button registerBtn;
     private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        registerBtn = findViewById(R.id.registerBtn);
        auth = FirebaseAuth.getInstance();
        registerBtn.setOnClickListener(v -> createAccount());
    }
    //Create Account

    private void createAccount(){

        String userName = String.valueOf(name.getText()).trim();
        String userEmail = String.valueOf(email.getText()).trim();
        String userPassword = String.valueOf(password.getText()).trim();
        String userConfirm = String.valueOf(confirmPassword.getText()).trim();

        if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || userConfirm.isEmpty()){
            Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show();
            return;
        }

        if (!userPassword.equals(userConfirm)){
            Toast.makeText(this,"Password not match",Toast.LENGTH_SHORT).show();
            return;
        }

        if(userPassword.length() < 6){
            Toast.makeText(this,"Password is too short",Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){

                        FirebaseUser user = auth.getCurrentUser();

                        if (user != null){

                            UserProfileChangeRequest profile =
                                    new UserProfileChangeRequest.Builder()
                                            .setDisplayName(userName)
                                            .build();

                            user.updateProfile(profile);
                        }

                        Toast.makeText(this,"Account Created",Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();

                    } else {

                        Toast.makeText(RegisterActivity.this,
                                "Error: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                });
    }}