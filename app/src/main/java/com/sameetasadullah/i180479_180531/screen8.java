package com.sameetasadullah.i180479_180531;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class screen8 extends AppCompatActivity {

    //Initializing Variables
    ImageView imageView;
    Button btOpen;
    RecyclerView rv;
    List<Recent_Contact> ls;
    screen8RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Opening Camera
        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,100);

        setContentView(R.layout.activity_screen8);

        //Assigning Values
        imageView = findViewById(R.id.image_view);
        rv = findViewById(R.id.rv);
        ls = new ArrayList<>();
        ls.add(new Recent_Contact("Hannah Lu"));
        ls.add(new Recent_Contact("Jason Boyd"));
        ls.add(new Recent_Contact("Nicholas Dunn"));
        ls.add(new Recent_Contact("Jammie Lan"));
        ls.add(new Recent_Contact("Tyson Fury"));
        ls.add(new Recent_Contact("Michael Syph"));
        ls.add(new Recent_Contact("Manny Jr."));
        ls.add(new Recent_Contact("ShadowLord"));

        //Adapter
        adapter = new screen8RVAdapter(ls,this);
        RecyclerView.LayoutManager lm =new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
        rv.addItemDecoration(new VerticalSpaceItemDecoration(50));

        updateUserStatus("online");
    }

    public void Restart(View v){
        this.recreate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100 && data != null) {
            //Getting Captured Image
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            //Setting Captured Image to ImageView

            imageView.setImageBitmap(captureImage);
            adapter.setImage(captureImage);
        }
        if (data == null) {
            this.finish();
        }
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
    public void onStart() {
        super.onStart();
        updateUserStatus("online");
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUserStatus("online");
    }

    @Override
    public void onStop() {
        super.onStop();
        updateUserStatus("offline");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        updateUserStatus("offline");
    }
}