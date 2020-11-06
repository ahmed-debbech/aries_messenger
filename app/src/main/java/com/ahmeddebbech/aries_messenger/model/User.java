package com.ahmeddebbech.aries_messenger.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;

public class User implements Parcelable {
    private String uid;
    private String username;
    private String displayName;
    private String email;

    public User(){

    }
    public User(String uid, String username, String displayName, String email){
        this.uid = uid;
        this.username = username;
        this.displayName = displayName;
        this.email = email;
    }
    public User(FirebaseUser fb){
        this.uid  = fb.getUid();
        this.username = "";
        this.displayName = fb.getDisplayName();
        this.email = fb.getEmail();
    }
    protected User(Parcel in) {
        uid = in.readString();
        username = in.readString();
        displayName = in.readString();
        email = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(username);
        dest.writeString(displayName);
        dest.writeString(email);
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
}