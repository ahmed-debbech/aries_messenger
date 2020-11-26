package com.ahmeddebbech.aries_messenger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.auth.Auth;
import com.ahmeddebbech.aries_messenger.database.DatabaseConnector;
import com.ahmeddebbech.aries_messenger.user.LoggedInUser;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Auth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("entered dd", "%%%%%5");
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
            LoggedInUser.getInstance(res);
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
                LoggedInUser.getInstance(FirebaseAuth.getInstance().getCurrentUser());
                Toast toast;
                if(LoggedInUser.getInstance().getUserModel().getDisplayName() == null) {
                    toast = Toast.makeText(this, "Welcome" , Toast.LENGTH_SHORT);
                }else{
                    toast = Toast.makeText(this, "Welcome " + LoggedInUser.getInstance().getUserModel().getDisplayName() , Toast.LENGTH_SHORT);
                }
                toast.show();
                DatabaseConnector.connectToSignIn(LoggedInUser.getInstance().getUserModel(), this);
            } else {
                Toast toast = Toast.makeText(this, "A problem occured while signin-in. Try again!\n" +
                        response.getError(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        auth = null;
    }
    public void googleProviderOnClick(View v){
        auth.showSignInIntent();
    }

    public void redirectRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("display_name", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        startActivity(intent);
        LoggedInUser.getInstance().signOut();
    }
    public void redirectMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
