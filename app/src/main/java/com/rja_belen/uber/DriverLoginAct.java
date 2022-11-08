package com.rja_belen.uber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverLoginAct extends AppCompatActivity {
    private EditText editEmail, editPassword;
    private Button btnLogin, btnSignup;

    private FirebaseAuth Auth;
    private FirebaseAuth.AuthStateListener AuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        Auth = FirebaseAuth.getInstance();

        AuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(DriverLoginAct.this, DriverMapActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = editEmail.getText().toString();
                final String pwd = editPassword.getText().toString();
                Auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(DriverLoginAct.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(DriverLoginAct.this, "Error: ", Toast.LENGTH_SHORT).show();
                        }else{
                            String user_id = Auth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id);
                            current_user_db.setValue(true);
                        }
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = editEmail.getText().toString();
                final String pwd = editPassword.getText().toString();
                Auth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(DriverLoginAct.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(DriverLoginAct.this, "Error: ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Auth.addAuthStateListener(AuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Auth.removeAuthStateListener(AuthListener);
    }
}