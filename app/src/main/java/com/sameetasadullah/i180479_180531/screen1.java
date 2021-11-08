package com.sameetasadullah.i180479_180531;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class screen1 extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen1);

        sharedPreferences = getSharedPreferences("com.sameetasadullah.i180479_180531", MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean("loggedIn", false);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (!loggedIn) {
                    Intent intent = new Intent(screen1.this, screen2.class);
                    screen1.this.startActivity(intent);
                    screen1.this.finish();
                }
                else {
                    Intent intent = new Intent(screen1.this, fragmentsContainer.class);
                    screen1.this.startActivity(intent);
                    screen1.this.finish();
                }
            }
        }, 5000);
    }
}