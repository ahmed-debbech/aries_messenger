package com.ahmeddebbech.aries_messenger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.auth.Auth;
import com.ahmeddebbech.aries_messenger.database.Database;
import com.ahmeddebbech.aries_messenger.user.LoggedInUser;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private Auth auth;
    private LoggedInUser loggedInUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_login);
        auth = new Auth(this);
        final DrawerLayout dl = findViewById(R.id.drawer_layout);
        findViewById(R.id.hamburger).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    dl.openDrawer(GravityCompat.START);
                }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_settings1:
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                }
                //close navigation drawer
                dl.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser res = auth.getLastSignedIn();
        if(res != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                loggedInUser = new LoggedInUser(FirebaseAuth.getInstance().getCurrentUser());
                Toast toast = Toast.makeText(this, "Welcome " + loggedInUser.getUserModel().getDisplayName(), Toast.LENGTH_SHORT);
                toast.show();
                Database.connectToSignup(loggedInUser.getUserModel(),this);
            } else {
                Toast toast = Toast.makeText(this, "A problem occured while signin-in. Try again!\n" +
                        response.getError(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
    public void googleProviderOnClick(View v){
        auth.showSignInIntent();
    }
    public void showRegisterActivity(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("display_name", loggedInUser.getFirebaseUserObject().getDisplayName());
        intent.putExtra("logged_user", loggedInUser);
        startActivity(intent);
    }
    public void passToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}