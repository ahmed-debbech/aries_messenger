package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManager {
    public static final int CONNECTED = 0;
    public static final int PENDING = 1;
    public static final int WAITING = 2;

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

    /**
     * Checks for a connection in the user model by its UID
     * @param uid the UID of the connection to search for
     * @param status : the status of the connection to look for ["connected", "pending", "waiting"]
     * @return the connection if found (the connection returned is in "connected" status not "pending" or "waiting"
     */
    public boolean searchForConnection(String uid, int status){
        if(userModel.getConnections() == null){
            return false;
        }
        if(userModel.getConnections().containsKey(uid)){
            String stat = "";
            switch(status){
                case 0: stat = "connected"; break;
                case 1: stat = "pending"; break;
                case 2: stat = "waiting"; break;
            }
            if(userModel.getConnections().get(uid) == stat){
                return true;
            }
        }
        return false;
    }
    public void removeContact(String uid){
        userModel.getConnections().remove(uid);
    }
    public void addContact(String uid){
        if(userModel.getConnections() == null){
            userModel.setConnections(new HashMap<String, String>());
        }
        userModel.getConnections().put(uid, "pending");
    }
    public List<String> getPendingConnections(){
        Map<String, String> d = userModel.getConnections();
        List<String> l = new ArrayList<>();
        for(Map.Entry<String, String> entry : d.entrySet()){
            if(entry.getValue().equals("pending")){
                l.add(entry.getKey());
            }
        }
        return l;
    }
    public int getConnectionsNumber(){
        Map<String, String> d = userModel.getConnections();
        List<String> l = new ArrayList<>();
        if(l.size() != 0){
            for (Map.Entry<String, String> entry : d.entrySet()) {
                if (entry.getValue().equals("connected")) {
                    l.add(entry.getKey());
                }
            }
        }else{
            return 0;
        }
        return l.size();
    }
    public void setConnectionStatusToConnected(String uid){
        if(userModel.getConnections().containsKey(uid)){
            userModel.getConnections().put(uid, "connected");
        }
    }
}
