package com.ahmeddebbech.aries_messenger.model;

import java.util.List;

public class Conversation {
    private String id;
    private List<String> members;
    private String latest_msg;
    private int count;

    public Conversation(){

    }
    public Conversation(String id, List<String> members, String latest_msg, int count){
        this.id = id;
        this.members = members;
        this.latest_msg = latest_msg;
        this.count = count;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getLatest_msg() {
        return latest_msg;
    }

    public void setLatest_msg(String latest_msg) {
        this.latest_msg = latest_msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
