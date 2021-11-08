package com.sameetasadullah.i180479_180531;

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

public class screen2 extends AppCompatActivity {
    TextView register;
    EditText email, password;
    RelativeLayout rl_login_button;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        ActivityCompat.requestPermissions(screen2.this,
                new String[]{Manifest.permission.READ_CONTACTS}, 1);

//        if(this.getApplicationContext().checkSelfPermission(
//            Manifest.permission.READ_CONTACTS ) == PackageManager.PERMISSION_GRANTED ) {
//        }

        register = findViewById(R.id.register);
        email = findViewById(R.id.et_email_address);
        password = findViewById(R.id.et_password);
        rl_login_button = findViewById(R.id.rl_login_button);

        sharedPreferences = getSharedPreferences("com.sameetasadullah.i180479_180531", MODE_PRIVATE);
        editor = sharedPreferences.edit();

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
                if (email.getText().toString().equals("smd") &&
                        password.getText().toString().equals("smd")) {
                    editor.putBoolean("loggedIn", true);
                    editor.commit();
                    editor.apply();

                    Intent intent = new Intent(screen2.this, fragmentsContainer.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(screen2.this, "Please enter correct input", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}