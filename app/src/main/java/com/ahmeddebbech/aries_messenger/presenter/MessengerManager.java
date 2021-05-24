package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.database.DbConversations;
import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.util.InputChecker;
import com.ahmeddebbech.aries_messenger.util.RandomIdGenerator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessengerManager {

    private static MessengerManager instance;
    private Message msg_to_repply_to = null;

    private MessengerManager(){

    }
    public static MessengerManager getInstance(){
        if(instance == null){
            instance = new MessengerManager();
        }
        return instance;
    }
    public void updateMessagesStatus(final String status, final List<Message> list, final String convid){
        DbConnector.connectToGetLastSeenIndex(UserManager.getInstance().getUserModel().getUid(), convid, new Presenter(){
            @Override
            public void returnData(DatabaseOutput obj) {
                if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.GET_LAST_SEEN) {
                    Integer ind = (Integer)obj.getObj();
                    List<Message> nlist = new ArrayList<>();
                    for (int i=ind+1; i<=UserManager.getInstance().getCurrentConv().getCount(); i++) {
                        for(int j=0; j<=list.size()-1; j++){
                            if(list.get(j).getIndex() == i){
                                if(list.get(j).getSender_uid() != UserManager.getInstance().getUserModel().getUid()) {
                                    list.get(j).setStatus(status);
                                    nlist.add(list.get(j));
                                }
                            }
                        }
                    }
                    DbConnector.connectToSendListOfMessages(nlist, convid);
                }
            }
        });

    }
    public void sendMessage(String msg, String receiver, Presenter pres) {
        if (UserManager.getInstance().getCurrentConv() == null) {
            Conversation cv = new Conversation();
            cv.setId(RandomIdGenerator.generateConversationId(UserManager.getInstance().getUserModel().getUid(), receiver));
            cv.setCount(0);
            List<String> mem = new ArrayList<>();
            mem.add(UserManager.getInstance().getUserModel().getUid());
            mem.add(receiver);
            cv.setMembers(mem);
            cv.setLatest_msg("");
            UserManager.getInstance().setCurrentConv(cv);
            DbConnector.connectToCreateConversation(cv, pres);
        }
        Message m = new Message();
        m.setSender_uid(UserManager.getInstance().getUserModel().getUid());
        m.setId(RandomIdGenerator.generateMessageId(UserManager.getInstance().getCurrentConv().getId()));
        m.setId_conv(UserManager.getInstance().getCurrentConv().getId());
        m.setContent(msg);
        if(msg_to_repply_to != null){
            m.setId_reply_msg(msg_to_repply_to.getId());
            msg_to_repply_to = null;
        }
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        m.setDate(time.toString());
        m.setStatus(Message.SENT);
        m.setIndex(UserManager.getInstance().getCurrentConv().getCount() + 1);
        DbConnector.sendMessage(UserManager.getInstance().getCurrentConv().getId(), m);
    }
    public void checkNewMessages(String uid , Presenter pres){
        DbConnector.connectToCheckNewMessages(uid, pres);
    }

    public void updateMessagesStatus(String status) {
        System.out.println("deliver detected!");
    }

    public void editMessage(String msg_id, String msg_cont) {
        DbConnector.connectToEditMsg(UserManager.getInstance().getCurrentConv().getId(), msg_id, msg_cont);
    }
    public void setMsgToReplyTo(Message msg){
        this.msg_to_repply_to = msg;
    }
    public Message getMsgToReplyTo(){
        return msg_to_repply_to;
    }
}
