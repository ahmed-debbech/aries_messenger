package com.ahmeddebbech.aries_messenger;

import com.google.firebase.auth.FirebaseUser;

public class LoggedInUser  {
    private FirebaseUser userRef;
    private class User{
        private String uid;
        private String username;
        private String displayName;
        private String email;
        public User(){

        }
        public User(String uid, String username, String display, String email){
            this.uid = uid;
            this.username = username;
            this.displayName = display;
            this.email = email;
        }
    }
    private User usr;
    LoggedInUser(FirebaseUser user){
        userRef = user;
        usr = new User(userRef.getUid(),"@xxxx",userRef.getDisplayName(),userRef.getEmail());
    }
    public FirebaseUser getFirebaseUserObject(){
        return userRef;
    }
}
