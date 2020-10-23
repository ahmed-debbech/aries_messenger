package com.ahmeddebbech.aries_messenger;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;

public class Auth {
    List<AuthUI.IdpConfig> providers;
    AppCompatActivity cur_act;
    Auth(AppCompatActivity act){
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
}
