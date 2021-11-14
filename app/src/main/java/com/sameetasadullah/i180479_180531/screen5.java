package com.sameetasadullah.i180479_180531;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.List;

public class screen5 extends ScreenshotDetectionActivity {
    ImageView backButton, makeCall, cameraImage;
    RecyclerView recyclerView;
    screen5RVAdaptor adaptor;
    List<message> messageList;
    TextView name, onlineStatus;
    EditText message;
    Bitmap image = null;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String receiverID;
    FirebaseAuth mAuth;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen5);

        backButton = findViewById(R.id.back_button);
        makeCall = findViewById(R.id.make_call);
        recyclerView = findViewById(R.id.rv_messages);
        name = findViewById(R.id.name);
        onlineStatus = findViewById(R.id.online_status);
        message = findViewById(R.id.message);
        cameraImage = findViewById(R.id.camera_image);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Messages");
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        messageList = new ArrayList<>();

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        receiverID = intent.getStringExtra("receiverID");
//        if (intent.getIntExtra("onlineStatus", 0) == 1) {
//            onlineStatus.setAlpha((float)1);
//        }
        image = (Bitmap) intent.getExtras().get("image");
        if (image != null) {
            insertImageMessageIntoFirebase();
        }

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                com.sameetasadullah.i180479_180531.message firebaseMessage = snapshot.getValue(com.sameetasadullah.i180479_180531.message.class);
                if ((firebaseMessage.getSenderID().equals(mAuth.getUid())
                        && firebaseMessage.getReceiverID().equals(receiverID)) ||
                        (firebaseMessage.getReceiverID().equals(mAuth.getUid())
                                && firebaseMessage.getSenderID().equals(receiverID))) {
                    messageList.add(firebaseMessage);
                    adaptor.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(messageList.size());
                }
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        makeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(screen5.this, screen11.class);
                intent.putExtra("name", name.getText().toString());
                startActivity(intent);
            }
        });
        message.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEND) {
                    OffsetTime offsetTime = OffsetTime.now();
                    DatabaseReference childReference = myRef.push();
                    childReference.setValue(new message(message.getText().toString(),
                            String.valueOf(offsetTime.getHour() + ":" + offsetTime.getMinute()),
                            "", childReference.getKey(), receiverID, mAuth.getUid(), null));
                    recyclerView.smoothScrollToPosition(messageList.size());
                    message.setText("");
                    return true;
                }
                return false;
            }
        });
        cameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(screen5.this);
        recyclerView.setLayoutManager(layoutManager);
        adaptor = new screen5RVAdaptor(screen5.this, messageList);
        recyclerView.setAdapter(adaptor);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(70));
        recyclerView.scrollToPosition(messageList.size() - 1);
    }

    private void insertImageMessageIntoFirebase() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] image = byteArrayOutputStream.toByteArray();
        storageReference.child(String.valueOf(System.currentTimeMillis()) + ".jpg").putBytes(image)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                        task
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        OffsetTime offsetTime = OffsetTime.now();
                                        DatabaseReference childReference = myRef.push();
                                        childReference.setValue(new message("",
                                                String.valueOf(offsetTime.getHour() + ":" + offsetTime.getMinute()),
                                                "Islamabad", childReference.getKey(), receiverID, mAuth.getUid(),
                                                uri.toString()));
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(screen5.this, "Failed to upload image", Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(screen5.this, "Failed to upload image", Toast.LENGTH_LONG).show();
                    }
                })
        ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100 && data != null) {
            //Getting Captured Image
            image = (Bitmap) data.getExtras().get("data");
            insertImageMessageIntoFirebase();
        }
    }

    @Override
    public void onScreenCaptured(String path) {
        //Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Screenshot taken", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScreenCapturedWithDeniedPermission() {
        Toast.makeText(this, "Please grant read external storage permission for screenshot detection", Toast.LENGTH_SHORT).show();
    }

//    private double insertMessageIntoDatabase(String toString) {
//        DBHelper dbHelper = new DBHelper(screen5.this);
//        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(chatsContract.Chats._SENDER_NAME, "John");
//        cv.put(chatsContract.Chats._RECEIVER_NAME, name.getText().toString());
//        cv.put(chatsContract.Chats._MESSAGE, message.getText().toString());
//        cv.put(chatsContract.Chats._TIME, "now");
//        double result = sqLiteDatabase.insert(chatsContract.Chats.TABLENAME, null, cv);
//        if (result == -1) {
//            System.out.println("Insertion query failed");
//        } else {
//            System.out.println("Insertion query passed");
//        }
//        sqLiteDatabase.close();
//        dbHelper.close();
//        return result;
//    }
//
//    private void getDataFromDB() {
//        DBHelper dbHelper = new DBHelper(screen5.this);
//        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
//        String[] projection = new String[] {
//                chatsContract.Chats._ID,
//                chatsContract.Chats._SENDER_NAME,
//                chatsContract.Chats._RECEIVER_NAME,
//                chatsContract.Chats._MESSAGE,
//                chatsContract.Chats._TIME
//        };
//
//        messageList.clear();
//        Cursor cursor = sqLiteDatabase.query(chatsContract.Chats.TABLENAME, projection, null,
//                null, null, null, null);
//        while (cursor.moveToNext()) {
//            int index = cursor.getColumnIndex(chatsContract.Chats._SENDER_NAME);
//            String senderName = cursor.getString(index);
//            index = cursor.getColumnIndex(chatsContract.Chats._RECEIVER_NAME);
//            String receiverName = cursor.getString(index);
//
//            if ((senderName.equals("John") && receiverName.equals(name.getText().toString())) ||
//                    (senderName.equals(name.getText().toString()) && receiverName.equals("John"))) {
//                index = cursor.getColumnIndex(chatsContract.Chats._ID);
//                String id = cursor.getString(index);
//                index = cursor.getColumnIndex(chatsContract.Chats._MESSAGE);
//                String message = cursor.getString(index);
//                index = cursor.getColumnIndex(chatsContract.Chats._TIME);
//                String time = cursor.getString(index);
//
//                if (senderName.equals("John")) {
//                    messageList.add(new message(message, time, "", id, true, null));
//                } else {
//                    messageList.add(new message(message, time, "", id, false, null));
//                }
//            }
//        }
//
//        if (image != null) {
//            messageList.add(new message("", "now", "San Francisco", "-1", true, image));
//        }
//    }
}