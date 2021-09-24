package com.ahmeddebbech.aries_messenger.model;

import java.nio.file.Path;

public class Feedback {

    private String user_id;
    private String email;
    private String description;

    public Feedback(String uid, String email, String desc){
        this.user_id = uid;
        this.email = email;
        this.description = desc;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
