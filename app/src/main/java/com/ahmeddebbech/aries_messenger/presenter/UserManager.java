package com.ahmeddebbech.aries_messenger.presenter;

import android.util.ArrayMap;
import android.util.Log;

import com.ahmeddebbech.aries_messenger.database.DatabaseReferences;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManager {
    public static final String CONNECTED = "connected";
    public static final String PENDING = "pending";
    public static final String WAITING = "waiting";

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
        //userModel.setConnections(u.getConnections());
        userModel.setConversations(u.getConversations());
        userModel.setAvailability(u.getAvailability());
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
    public boolean searchForConnection(String uid, String status){
        if(userModel.getConnections() == null){
            return false;
        }
        if(userModel.getConnections().containsKey(uid)){
            if(userModel.getConnections().get(uid).equals(status)){
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
        userModel.getConnections().put(uid, UserManager.WAITING);
    }
    public List<String> getConnectionsByType(String x){
        Map<String, String> d = userModel.getConnections();
        List<String> l = new ArrayList<>();
        for(Map.Entry<String, String> entry : d.entrySet()){
            if(entry.getValue().equals(x)){
                l.add(entry.getKey());
            }
        }
        return l;
    }
    public int getConnectionsNumber(){
        Map<String, String> d = userModel.getConnections();
        List<String> l = new ArrayList<>();
        if(d == null){
            return 0;
        }
        if(d.size() != 0){
            for (Map.Entry<String, String> entry : d.entrySet()) {
                if (entry.getValue().equals(UserManager.CONNECTED)) {
                    l.add(entry.getKey());
                }
            }
        }else{
            return 0;
        }
        return l.size();
    }
    public void setConnectionStatus(String uid, String status){
        if(userModel.getConnections().containsKey(uid)){
            userModel.getConnections().put(uid, status);
        }
    }

    public Map<String, String> getAllConvsIds(){
        return userModel.getConversations();
    }

    public void setAvailabilityStatus(int status) {
        DbConnector.connectToSetAvailabilityStatus(FirebaseAuth.getInstance().getUid(), status);
    }

    public String getConvId(String uid) {
        if(this.userModel.getConversations() != null ) {
            return this.userModel.getConversations().get(uid);
        }
        return null;
    }

    public void unblock(String uid) {
        if(userModel.getBlockedUsers().contains(uid)){
            userModel.getBlockedUsers().remove(uid);
        }
    }
}
