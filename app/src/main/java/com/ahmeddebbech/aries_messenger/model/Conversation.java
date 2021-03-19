package com.ahmeddebbech.aries_messenger.model;

import java.util.List;

public class Conversation {
    private String id;
    private List<User> members;
    private List<Message> messages;
    private String latest_msg;
    private String count;

    public Conversation(){

    }
    public Conversation(String id, List<User> members, List<Message> messages, String latest_msg, String count){
        this.id = id;
        this.members = members;
        this.messages = messages;
        this.latest_msg = latest_msg;
        this.count = count;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getLatest_msg() {
        return latest_msg;
    }

    public void setLatest_msg(String latest_msg) {
        this.latest_msg = latest_msg;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
