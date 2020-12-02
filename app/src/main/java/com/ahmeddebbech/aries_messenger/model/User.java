package com.ahmeddebbech.aries_messenger.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//This is a singleton class

public class User {
    private String uid;
    private String username;
    private String displayName;
    private String email;
    private String photoURL;

    private static User single_instance;

    public User(){

    }
    public User(String uid, String username, String displayName, String email, String photoURL){
        this.uid = uid;
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.photoURL= photoURL;
    }
    public User(FirebaseUser fb){
        this.uid  = fb.getUid();
        this.displayName = fb.getDisplayName();
        this.email = fb.getEmail();
        this.photoURL = fb.getPhotoUrl().toString();
    }
    private User(User u){
        this.username = u.username;
        this.displayName = u.displayName;
        this.email = u.email;
        this.photoURL = u.photoURL;
    }
    public static User getInstance(User u){
        single_instance = new User(u);
        return single_instance;
    }
    public static User getInstance(FirebaseUser u){
        if(single_instance == null){
            single_instance = new User(u);
        }
        return single_instance;
    }
    public static User getInstance(){
        if(single_instance == null){
            single_instance = new User();
        }
        return single_instance;
    }
    public String getEmail(){
        return email;
    }

    public String getUid(){
        return uid;
    }

    public String getUsername(){
        return username;
    }

    public String getDisplayName(){
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

}