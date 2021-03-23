package com.ahmeddebbech.aries_messenger.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//This is a singleton class

public class User {
    private String uid;
    private String username;
    private String displayName;
    private String email;
    private String photoURL;
    private String bio;

    private Map<String, String> connections;

    public User(){

    }
    public User(String uid, String username, String displayName, String email, String photoURL, String bio){
        this.uid = uid;
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.photoURL= photoURL;
        this.bio = bio;
        this.connections = new HashMap<>();
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Map<String, String> getConnections() {
        return connections;
    }

    public void setConnections(Map<String, String> connections) {
        this.connections = connections;
    }

}