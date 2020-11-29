package com.ahmeddebbech.aries_messenger.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.database.Synchronizer;
import com.ahmeddebbech.aries_messenger.user.LoggedInUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        /*ProgressBar pb = findViewById(R.id.wait_loop);
        pb.setVisibility(View.VISIBLE);

        Synchronizer.trackUserExistence(LoggedInUser.getInstance(), this);*/

        NavigationView nv = findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.profile_nav:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new ProfileFragment())
                            .addToBackStack(null).commit();
                    break;
            }
            return false;
            }
        });
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
        ImageView ig = header.findViewById(R.id.sidePhoto);
        Picasso.get().load(LoggedInUser.getInstance().getUserModel().getPhotoURL()).into(ig);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*ProgressBar pb = findViewById(R.id.wait_loop);
        pb.setVisibility(View.VISIBLE);
        Synchronizer.trackUserExistence(LoggedInUser.getInstance(), this);*/
    }

    public void signout(View v){
            LoggedInUser.getInstance().signOut();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}