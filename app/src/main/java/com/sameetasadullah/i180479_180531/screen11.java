package com.sameetasadullah.i180479_180531;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class screen11 extends AppCompatActivity {
    RelativeLayout rl_end_call;
    TextView name;
    boolean minimized = true;

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
                minimized = false;
                finish();
            }
        });

        updateUserStatus("online");
    }

    private void updateUserStatus(String state) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Accounts");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calForTime.getTime());

        reference.child(auth.getUid()).child("state").setValue(state);
        reference.child(auth.getUid()).child("lastSeenTime").setValue(saveCurrentTime);
        reference.child(auth.getUid()).child("lastSeenDate").setValue(saveCurrentDate);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        minimized = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUserStatus("online");
        minimized = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUserStatus("online");
        minimized = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (minimized) {
            updateUserStatus("offline");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (minimized) {
            updateUserStatus("offline");
        }
    }
}