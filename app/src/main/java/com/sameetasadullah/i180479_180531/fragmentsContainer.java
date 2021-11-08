package com.sameetasadullah.i180479_180531;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class fragmentsContainer extends AppCompatActivity {
    fragmentAdapter fragmentAdapter;
    ViewPager2 viewPager;
    ImageView callsImage, messagesImage, contactsImage, cameraImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments_container);

        fragmentAdapter = new fragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager = findViewById(R.id.container);
        callsImage = findViewById(R.id.calls_image);
        messagesImage = findViewById(R.id.messages_image);
        contactsImage = findViewById(R.id.contacts_image);
        cameraImage = findViewById(R.id.camera_image);

        setupViewPager(viewPager);

        messagesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        contactsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        callsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });
        cameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragmentsContainer.this, screen8.class);
                startActivity(intent);
            }
        });
    }

    private void changeImageColorToDefault(int item) {
        System.out.println(item);
        if (item == 0) {
            messagesImage.setBackgroundTintList(AppCompatResources.getColorStateList(fragmentsContainer.this, R.color.grey));
        } else if (item == 1) {
            contactsImage.setBackgroundTintList(AppCompatResources.getColorStateList(fragmentsContainer.this, R.color.grey));
        } else if (item == 2) {
            callsImage.setBackgroundTintList(AppCompatResources.getColorStateList(fragmentsContainer.this, R.color.grey));
        }
    }

    public void changeImageColorToBlue(int item) {
        if (item == 0) {
            messagesImage.setBackgroundTintList(AppCompatResources.getColorStateList(fragmentsContainer.this, R.color.blue));
            contactsImage.setBackgroundTintList(AppCompatResources.getColorStateList(fragmentsContainer.this, R.color.grey));
            callsImage.setBackgroundTintList(AppCompatResources.getColorStateList(fragmentsContainer.this, R.color.grey));
            cameraImage.setBackgroundTintList(AppCompatResources.getColorStateList(fragmentsContainer.this, R.color.grey));
        }
        else if (item == 1) {
            contactsImage.setBackgroundTintList(AppCompatResources.getColorStateList(fragmentsContainer.this, R.color.blue));
            messagesImage.setBackgroundTintList(AppCompatResources.getColorStateList(fragmentsContainer.this, R.color.grey));
            callsImage.setBackgroundTintList(AppCompatResources.getColorStateList(fragmentsContainer.this, R.color.grey));
            cameraImage.setBackgroundTintList(AppCompatResources.getColorStateList(fragmentsContainer.this, R.color.grey));
        }
        else if (item == 2) {
            callsImage.setBackgroundTintList(AppCompatResources.getColorStateList(fragmentsContainer.this, R.color.blue));
            contactsImage.setBackgroundTintList(AppCompatResources.getColorStateList(fragmentsContainer.this, R.color.grey));
            messagesImage.setBackgroundTintList(AppCompatResources.getColorStateList(fragmentsContainer.this, R.color.grey));
            cameraImage.setBackgroundTintList(AppCompatResources.getColorStateList(fragmentsContainer.this, R.color.grey));
        }
    }

    private void setupViewPager(ViewPager2 viewPager) {
        fragmentAdapter fragmentAdapter = new fragmentAdapter(getSupportFragmentManager(), getLifecycle());
        fragmentAdapter.addFragment(new fragment_screen4(), "Fragment_Screen4");
        fragmentAdapter.addFragment(new fragment_screen6(), "Fragment_Screen6");
        fragmentAdapter.addFragment(new fragment_screen10(), "Fragment_Screen10");
        viewPager.setAdapter(fragmentAdapter);
    }
}