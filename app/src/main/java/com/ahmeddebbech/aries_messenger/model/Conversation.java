package com.ahmeddebbech.aries_messenger.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    @NonNull
    @Override
    public String toString() {
        if(members == null) {
            return "{id: " + id + ", latest_msg: " + latest_msg + ", count: " + count + " }";
        }else{
            return "{id: " + id + ", members: " + members.toString() + ", latest_msg: " + latest_msg + ", count: " + count + " }";
        }
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof Conversation){
            Conversation cv = (Conversation) obj;
            if(cv.getId().equals(this.id)){
                return true;
            }
        }
        return false;
    }
}
