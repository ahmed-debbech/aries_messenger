package com.ahmeddebbech.aries_messenger.activities;

import android.os.Bundle;
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
        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        final DrawerLayout dl = findViewById(R.id.drawer_layout1);
        findViewById(R.id.hamburger1).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dl.openDrawer(GravityCompat.START);
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
                dl.closeDrawer(GravityCompat.START);
                return false;
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

    public void signout(View v){
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}