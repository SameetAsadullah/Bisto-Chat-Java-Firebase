package com.sameetasadullah.i180479_180531;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class screen3 extends AppCompatActivity {
    TextView login;
    EditText email, password, confirm_password;
    RelativeLayout rl_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        login = findViewById(R.id.login);
        email = findViewById(R.id.et_email_address);
        password = findViewById(R.id.et_password);
        confirm_password = findViewById(R.id.et_confirm_password);
        rl_signUp = findViewById(R.id.rl_signup_button);

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
                    Intent intent = new Intent(screen3.this, inputCredentials.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}