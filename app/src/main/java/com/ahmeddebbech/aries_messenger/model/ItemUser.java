package com.ahmeddebbech.aries_messenger.model;

import androidx.annotation.Nullable;

public class ItemUser {
    private String photo;
    private String displayName;
    private String username;
    private String uid;

    public ItemUser(String photo, String displayName, String username, String uid){
        this.displayName = displayName;
        this.username = username;
        this.photo = photo;
        this.uid = uid;
    }
    public ItemUser(User user){
        this.username = user.getUsername();
        this.displayName = user.getDisplayName();
        if(!user.getPhotoURL().isEmpty()) {
            this.photo = user.getPhotoURL();
        }else{
            this.photo = null;
        }
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof  ItemUser){
            ItemUser iu = (ItemUser)obj;
            return iu.getUid().equals(uid);
        }
        return false;
    }
}
