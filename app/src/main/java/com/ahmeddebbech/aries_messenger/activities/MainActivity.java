package com.ahmeddebbech.aries_messenger.activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.database.Database;
import com.ahmeddebbech.aries_messenger.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final NavigationView nv = findViewById(R.id.nav_view);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frag_container, new ConnectionsFragment())
                .addToBackStack(null).commit();
           nv.setCheckedItem(R.id.connections_nav);
        }
        /*ProgressBar pb = findViewById(R.id.wait_loop);
        pb.setVisibility(View.VISIBLE);

        Synchronizer.trackUserExistence(LoggedInUser.getInstance(), this);*/

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.profile_nav:
                    NavigationView nv = findViewById(R.id.nav_view);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new ProfileFragment())
                            .commit();
                    nv.setCheckedItem(R.id.profile_nav);
                    break;
                case R.id.connections_nav:
                    nv = findViewById(R.id.nav_view);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new ConnectionsFragment())
                            .commit();
                    nv.setCheckedItem(R.id.connections_nav);
                    break;
                case R.id.logout_nav:
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    break;
            }
                DrawerLayout dl = findViewById(R.id.drawer_layout1);
                dl.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        this.setClickListeners();
    }

    public void setClickListeners(){
        final DrawerLayout dl = findViewById(R.id.drawer_layout1);
        findViewById(R.id.hamburger1).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dl.openDrawer(GravityCompat.START);
            }
        });
        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        ImageView search_main = findViewById(R.id.search_main);
        search_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });
    }
    public void setupUi(){
        NavigationView n = findViewById(R.id.nav_view);
        View header = n.getHeaderView(0);
        TextView dis = (TextView) header.findViewById(R.id.sideDisplayName);
        TextView usr = (TextView) header.findViewById(R.id.sideUsername);
        dis.setText(User.getInstance().getDisplayName());
        usr.setText(User.getInstance().getUsername());
        ImageView ig = header.findViewById(R.id.sidePhoto);
        Picasso.get().load(User.getInstance().getPhotoURL()).into(ig);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Database.getUserData(FirebaseAuth.getInstance().getCurrentUser().getUid(), this);
        System.out.println(User.getInstance().getDisplayName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*ProgressBar pb = findViewById(R.id.wait_loop);
        pb.setVisibility(View.VISIBLE);
        Synchronizer.trackUserExistence(LoggedInUser.getInstance(), this);*/

    }
    @Override
    public void onBackPressed() {
        DrawerLayout nv = findViewById(R.id.drawer_layout1);
        if (nv.isDrawerOpen(GravityCompat.START)) {
            nv.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}