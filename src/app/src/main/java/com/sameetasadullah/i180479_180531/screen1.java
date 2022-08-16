package com.sameetasadullah.i180479_180531;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class screen1 extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen1);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (user != null) {
                    intent = new Intent(screen1.this, fragmentsContainer.class);
                } else {
                    intent = new Intent(screen1.this, screen2.class);
                }
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}