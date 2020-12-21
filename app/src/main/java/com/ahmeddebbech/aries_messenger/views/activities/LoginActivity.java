package com.ahmeddebbech.aries_messenger.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractLogin;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.LoginPresenter;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements ContractLogin.View {
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_login);

        presenter = new LoginPresenter(this);

        final DrawerLayout dl = findViewById(R.id.drawer_layout);
        findViewById(R.id.hamburger).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    dl.openDrawer(GravityCompat.START);
                }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser res = presenter.getLastSignedIn();
        if(res != null){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                FirebaseUser fu = FirebaseAuth.getInstance().getCurrentUser();
                User u  = new User(fu.getUid(),null, fu.getDisplayName(), fu.getEmail(), fu.getPhotoUrl().toString(), null);
                presenter.fillModel(u);
                Toast toast;
                if(FirebaseAuth.getInstance().getCurrentUser().getDisplayName() == null) {
                    toast = Toast.makeText(this, "Welcome" , Toast.LENGTH_SHORT);
                }else{
                    toast = Toast.makeText(this, "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName() , Toast.LENGTH_SHORT);
                }
                toast.show();
                presenter.connectToSignIn();
            } else {
                Toast toast = Toast.makeText(this, "A problem occured while signing-in. Try again!\n" +
                        response.getError(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter = null;
    }
    public void googleProviderOnClick(View v){
        presenter.loginAsGoogle();
    }

    @Override
    public void redirectRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        FirebaseAuth.getInstance().signOut();
    }
    @Override
    public void redirectMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void showSignInIntent(List<AuthUI.IdpConfig> providers) {
        this.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                1);
    }

}
