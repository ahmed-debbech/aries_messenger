package com.ahmeddebbech.aries_messenger.user;

import com.ahmeddebbech.aries_messenger.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoggedInUser {
    private User usr;

    private static LoggedInUser loggedInUserInstance = null;

    private LoggedInUser(FirebaseUser user){
        usr = new User(user);
    }
    private  LoggedInUser(){}

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

    public void setUserModel(User user){
        this.usr = user;
    }
    public void signOut(){
        usr = null;
        FirebaseAuth.getInstance().signOut();
        loggedInUserInstance = null;
    }

}
