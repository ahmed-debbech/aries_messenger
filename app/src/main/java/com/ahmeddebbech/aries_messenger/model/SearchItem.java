package com.ahmeddebbech.aries_messenger.model;

public class SearchItem {
    private String photo;
    private String displayName;
    private String username;

    SearchItem(String photo, String displayName, String username){
        this.displayName = displayName;
        this.username = username;
        this.photo = photo;
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
}