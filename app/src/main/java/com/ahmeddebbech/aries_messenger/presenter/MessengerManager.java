package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.database.DbConversations;
import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.util.InputChecker;
import com.ahmeddebbech.aries_messenger.util.RandomIdGenerator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MessengerManager extends Presenter{

    private static MessengerManager instance;
    private Message msg_to_repply_to = null;
    private List<Message> msg_list;
    private Conversation currentConv = null;
    private List<Conversation> latest_updated_convs = null;

    private MessengerManager(){

    }
    public static MessengerManager getInstance(){
        if(instance == null){
            instance = new MessengerManager();

        }
        return instance;
    }
    public void sendMessage(String msg, String receiver, Presenter pres) {
        Conversation cv = null;
        if (this.getCurrentConv() == null) {
            cv = new Conversation();
            cv.setId(RandomIdGenerator.generateConversationId(UserManager.getInstance().getUserModel().getUid(), receiver));
            cv.setCount(0);
            List<String> mem = new ArrayList<>();
            mem.add(UserManager.getInstance().getUserModel().getUid());
            mem.add(receiver);
            cv.setMembers(mem);
            cv.setLatest_msg("");
            this.setCurrentConv(cv);
            UserManager.getInstance().getUserModel().getConversations().put(receiver, cv.getId());
        }
        Message m = new Message();
        m.setSender_uid(UserManager.getInstance().getUserModel().getUid());
        m.setId_conv(this.getCurrentConv().getId());
        m.setId(RandomIdGenerator.generateMessageId(this.getCurrentConv().getId()));
        m.setIndex(this.getCurrentConv().getCount() + 1);
        m.setContent(InputChecker.makeMessageFine(msg));
        if(msg_to_repply_to != null){
            m.setId_reply_msg(msg_to_repply_to.getId());
            msg_to_repply_to = null;
        }
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        m.setDate(time.toString());
        m.setStatus(Message.SENT);
        DbConnector.connectToSendMessage(receiver, m, cv);
    }

    public void checkNewMessages(String uid , Presenter pres){
        DbConnector.connectToCheckNewMessages(uid, UserManager.getInstance().getAllConvsIds(), pres);
    }

    public void updateMessagesStatus(String status) {
        System.out.println("deliver detected!");
    }

    public void updateMessageState(String state, String convid, String msg_id){
        if(state == Message.SEEN){
            DbConnector.connectToUpdateMessageState(state, convid, msg_id);
        }
    }

    public void editMessage(String msg_id, String msg_cont) {
        DbConnector.connectToEditMsg(this.getCurrentConv().getId(), msg_id, msg_cont);
    }

    public boolean msgDoesExist(String id){
        for(Message m : msg_list){
            if(m.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public Message getOneMessage(String id) {
        for(Message m : msg_list){
            if(m.getId().equals(id)){
                return m;
            }
        }
        return null;
    }
    public void closeConversation() {
        this.currentConv = null;
        if(this.msg_list != null) {
            this.getMessages().clear();
        }
        this.msg_list = null;
    }

    public void addNewMessage(Message m) {
        if(this.msg_list == null){
            this.msg_list = new ArrayList<Message>();
        }
        if(!this.msg_list.contains(m)){
            this.msg_list.add(m);
        }
        Collections.sort(this.msg_list, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return o1.getIndex() - o2.getIndex();
            }
        });
    }

    public void updateMessage(Message m) {
        if(this.msg_list.contains(m)){
            int g = this.msg_list.indexOf(m);
            this.msg_list.set(g,m);
        }
    }

    public boolean hasNewMessages(String uid){
        if(UserManager.getInstance().getUserModel().getConversations() != null) {
            if (UserManager.getInstance().getUserModel().getConversations().containsKey(uid)) {
                String convid = UserManager.getInstance().getUserModel().getConversations().get(uid);

                if (this.latest_updated_convs != null && this.latest_updated_convs.size() > 0) {
                    for (Conversation vv : this.latest_updated_convs) {
                        if (vv.getId().equals(convid)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void addToLatestUpdatedConvs(Conversation cv){
        if(this.latest_updated_convs == null){
            this.latest_updated_convs = new ArrayList<>();
        }
        if(!this.latest_updated_convs.contains(cv)) {
            this.latest_updated_convs.add(cv);
        }
    }

    public void removeFromLatestUpdatedConvs(Conversation cv){
        if(this.latest_updated_convs != null){
            this.latest_updated_convs.remove(cv);
        }
    }
    public void removeFromLatestUpdatedConvs(String uid){
        if(UserManager.getInstance().getUserModel().getConversations() != null) {
            if (UserManager.getInstance().getUserModel().getConversations().containsKey(uid)) {
                String convid = UserManager.getInstance().getUserModel().getConversations().get(uid);

                Conversation cc = new Conversation();
                cc.setId(convid);
                if (this.latest_updated_convs != null && this.latest_updated_convs.size() > 0) {
                    this.latest_updated_convs.remove(cc);
                }
            }
        }
    }

    public void setMsgToReplyTo(Message msg){
        this.msg_to_repply_to = msg;
    }

    public Message getMsgToReplyTo(){
        return msg_to_repply_to;
    }

    public void setMessages(List<Message> list) {
        this.msg_list = list;
    }

    public List<Message> getMessages(){
        if(this.msg_list != null){
            Collections.sort(this.msg_list, new Comparator<Message>() {
                @Override
                public int compare(Message o1, Message o2) {
                    return o1.getIndex() - o2.getIndex();
                }
            });
        }
        return this.msg_list;
    }

    public Conversation getCurrentConv() {
        return currentConv;
    }

    public void setCurrentConv(Conversation currentConv) {
        this.currentConv = currentConv;
    }

    @Override
    public void returnData(DatabaseOutput obj) {
        if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.CHECK_NEW_MESSAGES_KEY){
            Conversation cv = (Conversation) obj.getObj();
            this.addToLatestUpdatedConvs(cv);
        }
    }
}
