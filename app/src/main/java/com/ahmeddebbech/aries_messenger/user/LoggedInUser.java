package com.ahmeddebbech.aries_messenger.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.ahmeddebbech.aries_messenger.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoggedInUser implements Parcelable {
    private User usr;

    private static LoggedInUser loggedInUserInstance = null;

    protected LoggedInUser(Parcel in) {
        usr = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<LoggedInUser> CREATOR = new Creator<LoggedInUser>() {
        @Override
        public LoggedInUser createFromParcel(Parcel in) {
            return new LoggedInUser(in);
        }

        @Override
        public LoggedInUser[] newArray(int size) {
            return new LoggedInUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(usr, flags);
    }

    private LoggedInUser(FirebaseUser user){
        usr = new User(user);
    }
    public LoggedInUser(){

    }

    public static LoggedInUser getInstance(FirebaseUser user){
        if(loggedInUserInstance == null){
            loggedInUserInstance = new LoggedInUser(user);
        }
        return loggedInUserInstance;
    }
    public static LoggedInUser getInstance(){
        if(loggedInUserInstance == null){
            loggedInUserInstance = new LoggedInUser();
        }
        return loggedInUserInstance;
    }
    public User getUserModel() {
        return usr;
    }
    public void signOut(){
        usr = null;
        FirebaseAuth.getInstance().signOut();
    }

}
