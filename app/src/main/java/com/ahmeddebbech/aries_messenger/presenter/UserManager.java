package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.User;
import com.google.firebase.auth.FirebaseUser;

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
        return userModel.getConnections().contains(uid);
    }
    public int searchForConnectionIndex(String uid){
        for(int i=0; i<=userModel.getConnections().size()-1; i++){
            if(userModel.getConnections().get(i).equals(uid)){
                return i;
            }
        }
        return -1;
    }
}
