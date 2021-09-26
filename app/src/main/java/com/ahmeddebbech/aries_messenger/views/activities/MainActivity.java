package com.ahmeddebbech.aries_messenger.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractMain;
import com.ahmeddebbech.aries_messenger.presenter.MainPresenter;
import com.ahmeddebbech.aries_messenger.presenter.MessengerManager;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.threads.InitialDataRetrieverThread;
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
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LifecycleObserver;

public class MainActivity extends AppCompatActivity implements LifecycleObserver, ContractMain.View {

    MainPresenter presenter;

    private TextView username_nav;
    private TextView displayName_nav;
    private ImageView photo_nav;
    private ProgressBar wait_loop;
    private View header_nav;
    private NavigationView navigationView;
    private ImageView requests_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new MainPresenter(this);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.profile_nav:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new ProfileFragment())
                                .commit();
                        navigationView.setCheckedItem(R.id.profile_nav);
                        break;
                    case R.id.connections_nav:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new ConnectionsFragment())
                                .commit();
                        navigationView.setCheckedItem(R.id.connections_nav);
                        break;
                    /*case R.id.settings_nav:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new ConnectionsFragment())
                                .commit();
                        navigationView.setCheckedItem(R.id.settings_nav);
                        break;*/
                    case R.id.feedback_nav:
                        Intent iii = new Intent(MainActivity.this, FeedbackActivity.class);
                        startActivity(iii);
                        break;
                    case R.id.logout_nav:
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        break;
                    case R.id.blocked_nav:
                        Intent i = new Intent(MainActivity.this, BlockedActivity.class);
                        startActivity(i);
                        //navigationView.setCheckedItem(R.id.settings_nav);
                        break;
                }
                DrawerLayout dl = findViewById(R.id.drawer_layout1);
                dl.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        /*ProgressBar pb = findViewById(R.id.wait_loop);
        pb.setVisibility(View.VISIBLE);

        DbSync.trackUserExistence(LoggedInUser.getInstance(), this);*/
        this.setClickListeners();
        wait_loop = findViewById(R.id.wait_loop);
        wait_loop.setVisibility(View.VISIBLE);
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
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
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
        this.requests_main = findViewById(R.id.requests_main);
        this.requests_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RequestsActivity.class);
                startActivity(i);
            }
        });
        getSupportFragmentManager().setFragmentResultListener("result", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Boolean re = result.getBoolean("result");
                setPendingBadge(re);
            }
        });
        InitialDataRetrieverThread runnable = new InitialDataRetrieverThread(FirebaseAuth.getInstance().getCurrentUser().getUid(), presenter);
        new Thread(runnable).start();
    }
    @Override
    public void setupUi(){
        wait_loop.setVisibility(View.INVISIBLE);
        header_nav = navigationView.getHeaderView(0);
        displayName_nav = (TextView) header_nav.findViewById(R.id.sideDisplayName);
        username_nav = (TextView) header_nav.findViewById(R.id.sideUsername);
        photo_nav = header_nav.findViewById(R.id.sidePhoto);
        navigationView.setCheckedItem(R.id.connections_nav);
        presenter.fillViewsWithUserData();
    }

    @Override
    public void setPendingBadge(boolean set) {
        if(set == true) {
            this.requests_main.setImageResource(R.drawable.ic_baseline_people_red_24);
        }else{
            this.requests_main.setImageResource(R.drawable.ic_baseline_people_alt_24);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //presenter.getDatafromDatabase(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //UserManager.getInstance().setAvailabilityStatus(User.ONLINE);
        /*ProgressBar pb = findViewById(R.id.wait_loop);
        pb.setVisibility(View.VISIBLE);
        DbSync.trackUserExistence(LoggedInUser.getInstance(), this);*/
        /*presenter.getConnections();
        presenter.getConversations();
        presenter.getBlocked();*/
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
        if(image != null) {
            Picasso.get().load(image).resize(200, 200).into(photo_nav);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new ConnectionsFragment()).commit();
        navigationView.setCheckedItem(R.id.connections_nav);
    }

}