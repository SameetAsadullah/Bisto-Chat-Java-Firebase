package com.sameetasadullah.i180479_180531;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class inputCredentials extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    EditText firstName, lastName, bio;
    Button create;
    ImageView addDisplayPic;
    CircleImageView dp;
    Uri imageURI = null;
    RadioGroup genderRadioGroup;
    RadioButton genderRadioButton;
    String email, password, ID;
    FirebaseDatabase database;
    DatabaseReference myRef;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_credentials);

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        bio = findViewById(R.id.bio);
        create = findViewById(R.id.create);
        addDisplayPic = findViewById(R.id.add_display_pic);
        dp = findViewById(R.id.display_pic);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Accounts");
        storageReference = FirebaseStorage.getInstance().getReference("DPs");

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        ID = intent.getStringExtra("id");

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioButtonID =genderRadioGroup.getCheckedRadioButtonId();

                if (firstName.getText().toString().equals("") ||
                        lastName.getText().toString().equals("") ||
                        radioButtonID == -1 ||
                        bio.getText().toString().equals("")) {
                    Toast.makeText(inputCredentials.this,
                            "Please fill all the input fields", Toast.LENGTH_LONG).show();
                }
                else if (imageURI == null) {
                    Toast.makeText(inputCredentials.this,
                            "Please select image", Toast.LENGTH_LONG).show();
                }
                else {
                    genderRadioButton = findViewById(radioButtonID);
                    storageReference.child(ID + ".jpg").putFile(imageURI)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                                    task
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String dp = uri.toString();
                                                myRef.push().setValue(
                                                        new Account(
                                                                ID, email, password,
                                                                firstName.getText().toString(),
                                                                lastName.getText().toString(),
                                                                genderRadioButton.getText().toString(),
                                                                bio.getText().toString(),
                                                                dp
                                                        )
                                                );
                                                Intent intent = new Intent(inputCredentials.this, fragmentsContainer.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(inputCredentials.this, "Failed to Upload Image", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                    ;
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(inputCredentials.this, "Failed to Upload Image", Toast.LENGTH_LONG).show();
                                }
                            })
                    ;
                }
            }
        });
        addDisplayPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            addDisplayPic.setAlpha((float)0);
            imageURI = data.getData();
            dp.setImageURI(imageURI);
        }
    }
}