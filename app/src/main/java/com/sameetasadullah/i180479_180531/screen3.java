package com.sameetasadullah.i180479_180531;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class screen3 extends AppCompatActivity {
    TextView login;
    EditText email, password, confirm_password;
    RelativeLayout rl_signUp;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        login = findViewById(R.id.login);
        email = findViewById(R.id.et_email_address);
        password = findViewById(R.id.et_password);
        confirm_password = findViewById(R.id.et_confirm_password);
        rl_signUp = findViewById(R.id.rl_signup_button);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(screen3.this, screen2.class);
                startActivity(intent);
                finish();
            }
        });
        rl_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().equals("") ||
                        password.getText().toString().equals("") ||
                        confirm_password.getText().toString().equals("")) {
                    Toast.makeText(screen3.this, "Please fill all the input fields",
                            Toast.LENGTH_LONG).show();
                }
                else if (!password.getText().toString().equals(confirm_password.getText().toString())) {
                    Toast.makeText(screen3.this, "Passwords do not match",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task< AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(screen3.this, inputCredentials.class);
                                        intent.putExtra("email", email.getText().toString());
                                        intent.putExtra("password", password.getText().toString());
                                        intent.putExtra("id", mAuth.getCurrentUser().getUid());
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(screen3.this, "Failed to Create User", Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(screen3.this, "Failed to Create User", Toast.LENGTH_LONG).show();
                                }
                            })
                    ;
                }
            }
        });
    }
}