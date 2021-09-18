package com.ahmeddebbech.aries_messenger.database.model;

import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.Message;

public class MessagePersist {

    private Message message;
    private String recep_uid;
    private Conversation conversation;

    public MessagePersist(Message m, String recep_id, Conversation cv){
        this.message = m;
        this.recep_uid = recep_id;
        this.conversation = cv;
    }
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getRecep_id() {
        return recep_uid;
    }

    public void setRecep_id(String recep_id) {
        this.recep_uid = recep_id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
