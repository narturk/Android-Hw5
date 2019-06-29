package com.example.kotelmania;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class myLogin extends AppCompatActivity {

    EditText email, password;
    Button loginBtn, registerBtn;
    FirebaseAuth firebaseAuth;
    String emailVal;
    String passwordVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            Intent intent = new Intent(this, listActivity.class);
            startActivity(intent);
        }


        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        loginBtn = (Button) findViewById(R.id.login);
        registerBtn = (Button) findViewById(R.id.register);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                emailVal = email.getText().toString();
                passwordVal = password.getText().toString();

                if(emailVal.isEmpty() || passwordVal.isEmpty()){
                    Toast.makeText(myLogin.this, "You have to submit all.", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(emailVal, passwordVal)
                        .addOnCompleteListener(myLogin.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    Toast.makeText(myLogin.this,
                                            "logged in with user " + user.getEmail(),
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(myLogin.this, listActivity.class);
                                    startActivity(intent);

                                } else {
                                    Log.i("failed", "cant log int");
                                    Toast.makeText(myLogin.this, "Login failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                emailVal = email.getText().toString();
                passwordVal = password.getText().toString();

                if(emailVal.isEmpty() || passwordVal.isEmpty()){
                    Toast.makeText(myLogin.this, "You have to submit all.", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(emailVal, passwordVal)
                        .addOnCompleteListener(myLogin.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    Toast.makeText(myLogin.this,
                                            "register with user " + user.getEmail(),
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(myLogin.this, listActivity.class);
                                    startActivity(intent);

                                } else {
                                    Log.i("failed", "cant register");
                                    Toast.makeText(myLogin.this, "Register failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
