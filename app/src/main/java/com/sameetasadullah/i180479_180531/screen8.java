package com.sameetasadullah.i180479_180531;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

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
    List<Account> accounts;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    public boolean minimized = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Opening Camera
        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        minimized = false;
        startActivityForResult(intent,100);

        setContentView(R.layout.activity_screen8);

        //Assigning Values
        imageView = findViewById(R.id.image_view);
        rv = findViewById(R.id.rv);
        ls = new ArrayList<>();
        accounts = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference("Accounts");
        mAuth = FirebaseAuth.getInstance();

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Account firebaseAccount = snapshot.getValue(Account.class);
                accounts.add(firebaseAccount);
                ls.clear();
                addPhoneContactsToList();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

    private void addPhoneContactsToList() {
        ContentResolver contentResolver = getContentResolver();
        Cursor phones=contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        String phoneNo;
        while (phones.moveToNext()) {
            int index = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNo = phones.getString(index).replace("+92","0");

            if (phoneNo.charAt(4) == ' ') {
                StringBuilder stringBuilder = new StringBuilder(phoneNo);
                stringBuilder.deleteCharAt(4);
                phoneNo = stringBuilder.toString();
            }

            for (int i = 0; i < accounts.size(); ++i) {
                if (accounts.get(i).getPhoneNumber().equals(phoneNo) && !accounts.get(i).getID().equals(mAuth.getUid())) {
                    ls.add(new Recent_Contact(accounts.get(i).getFirstName() + " " +
                            accounts.get(i).getLastName(), accounts.get(i).getDp(), accounts.get(i).getID()));
                    break;
                }
            }
        }

        phones.close();
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
        if (minimized)
            updateUserStatus("offline");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (minimized)
            updateUserStatus("offline");
    }
}