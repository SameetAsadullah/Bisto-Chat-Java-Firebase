package com.sameetasadullah.i180479_180531;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class inputCredentials extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    EditText firstName, lastName, gender, bio;
    Button create;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView addDisplayPic;
    CircleImageView dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_credentials);

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        gender = findViewById(R.id.gender);
        bio = findViewById(R.id.bio);
        create = findViewById(R.id.create);
        addDisplayPic = findViewById(R.id.add_display_pic);
        dp = findViewById(R.id.display_pic);

        sharedPreferences = getSharedPreferences("com.sameetasadullah.i180479_180531", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstName.getText().toString().equals("") ||
                        lastName.getText().toString().equals("") ||
                        gender.getText().toString().equals("") ||
                        bio.getText().toString().equals("")) {
                    Toast.makeText(inputCredentials.this,
                            "Please fill all the input fields", Toast.LENGTH_LONG).show();
                }
                else {
                    editor.putBoolean("loggedIn", true);
                    editor.commit();
                    editor.apply();

                    Intent intent = new Intent(inputCredentials.this, fragmentsContainer.class);
                    startActivity(intent);
                    finish();
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
        if (requestCode == PICK_IMAGE) {
            addDisplayPic.setAlpha((float)0);
            dp.setImageURI(data.getData());
        }
    }
}