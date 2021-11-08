package com.sameetasadullah.i180479_180531;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class screen11 extends AppCompatActivity {
    RelativeLayout rl_end_call;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen11);

        rl_end_call = findViewById(R.id.rl_end_call);
        name = findViewById(R.id.tv_name);

        name.setText(getIntent().getStringExtra("name"));

        rl_end_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}