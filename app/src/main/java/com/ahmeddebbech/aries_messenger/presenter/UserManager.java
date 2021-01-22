package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UserManager {
    private User userModel;
    private static UserManager umInstance;

    private UserManager(){
        userModel = new User();
    }
    public static UserManager getInstance(){
        if(umInstance == null){
            umInstance = new UserManager();
        }
        return umInstance;
    }
    public void updateWithCopy(User u){
        userModel.setUid(u.getUid());
        userModel.setUsername(u.getUsername());
        userModel.setDisplayName(u.getDisplayName());
        userModel.setEmail(u.getEmail());
        userModel.setPhotoURL(u.getPhotoURL());
        userModel.setBio(u.getBio());
        userModel.setConnections(u.getConnections());
    }
    public void fillAllData(String uid, String username, String displayName, String email, String photoURL, String bio){
        userModel = new User(uid,username,displayName,email,photoURL,bio);
    }
    public User getUserModel(){
        return userModel;
    }
    public boolean searchForConnection(String uid){
        if(userModel.getConnections() == null){
            return false;
        }
        return userModel.getConnections().contains(uid);
    }
    public void removeContact(String uid){
        userModel.getConnections().remove(uid);
    }
    public void addContact(String uid){
        userModel.getConnections().add(uid);
    }
    public List<String> getPendingConnections(){
        List<String> d = userModel.getConnections();
        for(int i=0; i<=d.size()-1; i++){
            if(!d.get(i).equals("-")){
                d.remove(i);
            }
        }
        for(int i=0; i<=d.size()-1; i++){
            System.out.println("contact "+ d.get(i));
        }
        return d;
    }
}
