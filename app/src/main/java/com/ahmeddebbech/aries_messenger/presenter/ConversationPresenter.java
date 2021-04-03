package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractConversation;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.database.DbConversations;
import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.util.RandomIdGenerator;

import java.util.List;

public class ConversationPresenter extends Presenter implements ContractConversation.Presenter {
    private ContractConversation.View activity;

    public ConversationPresenter(ContractConversation.View act){
        this.activity = act;
    }

    @Override
    public void sendMessage(String msg) {
        Message m = new Message();
        m.setSender_uid(UserManager.getInstance().getUserModel().getUid());
        m.setId(RandomIdGenerator.generateMessageId(UserManager.getInstance().getCurrentConv().getId()));
        m.setId_conv(UserManager.getInstance().getCurrentConv().getId());
        m.setContent(msg);
        m.setDate(1617488846);
        m.setStatus(Message.SENT);
        m.setIndex(UserManager.getInstance().getCurrentConv().getCount() + 1);
        Log.d("type@#", "" + UserManager.getInstance().getCurrentConv().getCount());
        DbConversations.sendMessage(UserManager.getInstance().getCurrentConv().getId(), m);
    }

    @Override
    public void loadData(String uid) {
        DbConnector.connectToGetUserData(uid, this);
    }

    @Override
    public void conversationExists(String uid) {
        DbConnector.connectToCheckIfConversationExists(uid, this);
    }

    @Override
    public void getConversationMetadata(String uidA, String uidB) {
        DbConnector.connectToGetOneConversation(uidA, uidB, this);
    }

    @Override
    public void returnData(Object obj) {
        if(obj instanceof User){
            User u = (User)obj;
            activity.retContactData(u);
        }else{
            if(obj instanceof Boolean){
                Boolean bb = (Boolean) obj;
                activity.showHint(bb);
            }else{
                if(obj instanceof Conversation){
                    // load the meta
                    Conversation cv = (Conversation)obj;
                    Log.d("#@e", "con: " + cv.getId());
                    Log.d("#@e", "con: " + cv.getMembers().toString());
                    Log.d("#@e", "con: " + cv.getLatest_msg());
                    UserManager.getInstance().setCurrentConv(cv);

                    DbConnector.connectToGetMessages(cv.getId(), this);
                }else{
                    if(obj instanceof List){
                        List<Message> lis = (List<Message>)obj;
                        activity.loadMessages(lis);
                    }
                }
            }
        }
    }
}
