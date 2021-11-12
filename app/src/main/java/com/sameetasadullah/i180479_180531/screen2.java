package com.sameetasadullah.i180479_180531;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class screen2 extends AppCompatActivity {
    TextView register;
    EditText email, password;
    RelativeLayout rl_login_button;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        ActivityCompat.requestPermissions(screen2.this,
                new String[]{
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.INTERNET
                }, 1);

        register = findViewById(R.id.register);
        email = findViewById(R.id.et_email_address);
        password = findViewById(R.id.et_password);
        rl_login_button = findViewById(R.id.rl_login_button);
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(screen2.this, screen3.class);
                startActivity(intent);
                finish();
            }
        });
        rl_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email.getText().toString().isEmpty() &&
                        !password.getText().toString().isEmpty()) {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(screen2.this, fragmentsContainer.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(screen2.this, "SignIn Error Occurred", Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(screen2.this, "SignIn Error Occurred", Toast.LENGTH_LONG).show();
                                }
                            })
                    ;
                } else {
                    Toast.makeText(screen2.this, "Please fill all input fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}