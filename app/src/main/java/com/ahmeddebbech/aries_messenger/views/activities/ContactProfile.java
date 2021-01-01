package com.ahmeddebbech.aries_messenger.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.ahmeddebbech.aries_messenger.R;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

public class ContactProfile extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_profile);

        Intent in = getIntent();
        username = in.getStringExtra("username");
        getSupportActionBar().setTitle(username);
    }
}