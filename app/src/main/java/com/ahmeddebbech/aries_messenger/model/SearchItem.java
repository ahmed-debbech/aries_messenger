package com.ahmeddebbech.aries_messenger.model;

public class SearchItem {
    private String photo;
    private String displayName;
    private String username;
    private String uid;

    public SearchItem(String photo, String displayName, String username, String uid){
        this.displayName = displayName;
        this.username = username;
        this.photo = photo;
        this.uid = uid;
    }
    public SearchItem(User user){
        this.username = user.getUsername();
        this.displayName = user.getDisplayName();
        this.photo = user.getPhotoURL();
        this.uid = user.getUid();
    }
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
