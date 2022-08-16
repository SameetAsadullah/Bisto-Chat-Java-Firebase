package com.sameetasadullah.i180479_180531;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class createGroup extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    ImageView addDisplayPic;
    Uri imageURI = null;
    CircleImageView dp;
    EditText groupName;
    RecyclerView recyclerView;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        addDisplayPic = findViewById(R.id.add_display_pic);
        groupName = findViewById(R.id.first_name);
        recyclerView = findViewById(R.id.members);
        create = findViewById(R.id.create);

        addDisplayPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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