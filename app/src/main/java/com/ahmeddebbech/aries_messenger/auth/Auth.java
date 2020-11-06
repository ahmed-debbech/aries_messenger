package com.ahmeddebbech.aries_messenger.auth;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class Auth {
    List<AuthUI.IdpConfig> providers;
    AppCompatActivity cur_act;

    public Auth(AppCompatActivity act){
        providers = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build());
        cur_act = act;
    }
    public void showSignInIntent(){
        cur_act.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                1);
    }
    public FirebaseUser getLastSignedIn(){
        FirebaseUser account = FirebaseAuth.getInstance().getCurrentUser();
        if(account != null){
            return account;
        }
        return null;
    }
}
