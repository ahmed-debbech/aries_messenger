package com.ahmeddebbech.aries_messenger.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractMain;
import com.ahmeddebbech.aries_messenger.database.DbBasic;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.MainPresenter;
import com.ahmeddebbech.aries_messenger.views.fragments.ConnectionsFragment;
import com.ahmeddebbech.aries_messenger.views.fragments.ProfileFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity implements ContractMain.View {

    MainPresenter presenter;

    private TextView username_nav;
    private TextView displayName_nav;
    private ImageView photo_nav;
    private View header_nav;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new MainPresenter(this);

        final NavigationView nv = findViewById(R.id.nav_view);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frag_container, new ConnectionsFragment())
                .addToBackStack(null).commit();
           nv.setCheckedItem(R.id.connections_nav);
        }
        /*ProgressBar pb = findViewById(R.id.wait_loop);
        pb.setVisibility(View.VISIBLE);

        DbSync.trackUserExistence(LoggedInUser.getInstance(), this);*/

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
    @Override
    public void setupUi(){
        navigationView = findViewById(R.id.nav_view);
        header_nav = navigationView.getHeaderView(0);
        displayName_nav = (TextView) header_nav.findViewById(R.id.sideDisplayName);
        username_nav = (TextView) header_nav.findViewById(R.id.sideUsername);
        photo_nav = header_nav.findViewById(R.id.sidePhoto);

        presenter.fillViewsWithUserData();
    }
    @Override
    protected void onStart() {
        super.onStart();
        presenter.getDatafromDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*ProgressBar pb = findViewById(R.id.wait_loop);
        pb.setVisibility(View.VISIBLE);
        DbSync.trackUserExistence(LoggedInUser.getInstance(), this);*/

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

    @Override
    public void renderViewsWithData(String disp, String usern, String image) {
        displayName_nav.setText(disp);
        username_nav.setText(usern);
        Picasso.get().load(image).into(photo_nav);
    }

}