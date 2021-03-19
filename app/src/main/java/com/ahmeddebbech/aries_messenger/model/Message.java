package com.ahmeddebbech.aries_messenger.model;

public class Message {
    public static final String SENT ="sent";
    public static final String DELIVERED ="delivered";
    public static final String NOTIFIED = "notified";
    public static final String SEEN = "seen";

    private String id;
    private String sender_uid;
    private String id_conv;
    private String date;
    private String content;
    private String status;
    private String id_reply_msg;
    private String index;

    public Message(){

    }
    public Message(String id, String sender_uid, String id_conv, String date, String content, String status, String id_reply_msg, String index){
        this.id = id;
        this.sender_uid = sender_uid;
        this.id_conv = id_conv;
        this.date = date;
        this.content = content;
        this.status = status;
        this.id_reply_msg = id_reply_msg;
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender_uid() {
        return sender_uid;
    }

    public void setSender_uid(String sender_uid) {
        this.sender_uid = sender_uid;
    }

    public String getId_conv() {
        return id_conv;
    }

    public void setId_conv(String id_conv) {
        this.id_conv = id_conv;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_reply_msg() {
        return id_reply_msg;
    }

    public void setId_reply_msg(String id_reply_msg) {
        this.id_reply_msg = id_reply_msg;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
