package com.ahmeddebbech.aries_messenger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.database.Database;
import com.ahmeddebbech.aries_messenger.user.LoggedInUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        Database.trackUserExistence(LoggedInUser.getInstance(), this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        NavigationView n = findViewById(R.id.nav_view);
        View header = n.getHeaderView(0);
        TextView dis = (TextView) header.findViewById(R.id.sideDisplayName);
        TextView usr = (TextView) header.findViewById(R.id.sideUsername);
        dis.setText(LoggedInUser.getInstance().getUserModel().getDisplayName());
        usr.setText(LoggedInUser.getInstance().getUserModel().getUsername());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Database.trackUserExistence(LoggedInUser.getInstance(), this);
    }

    public void signout(View v){
            LoggedInUser.getInstance().signOut();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}