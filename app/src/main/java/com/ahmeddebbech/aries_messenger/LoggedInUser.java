package com.ahmeddebbech.aries_messenger;

import com.google.firebase.auth.FirebaseUser;

public class LoggedInUser  {
    private FirebaseUser userRef;
    LoggedInUser(FirebaseUser user){
        userRef = user;
    }
    public FirebaseUser getFirebaseUserObject(){
        return userRef;
    }
}
