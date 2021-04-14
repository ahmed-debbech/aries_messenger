package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractConversation;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.database.DbConversations;
import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.util.RandomIdGenerator;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConversationPresenter extends Presenter implements ContractConversation.Presenter {
    private ContractConversation.View activity;

    public ConversationPresenter(ContractConversation.View act){
        this.activity = act;
    }

    @Override
    public void sendMessage(String msg, String receiver) {
        if(UserManager.getInstance().getCurrentConv() == null){
            Conversation cv = new Conversation();
            cv.setId(RandomIdGenerator.generateConversationId(UserManager.getInstance().getUserModel().getUid(), receiver));
            cv.setCount(0);
            System.out.println("$$$$$$$"  + cv.getId());
            List<String> mem = new ArrayList<>();
            mem.add(UserManager.getInstance().getUserModel().getUid());
            mem.add(receiver);
            cv.setMembers(mem);
            cv.setLatest_msg("");
            UserManager.getInstance().setCurrentConv(cv);
            DbConversations.createConversation(cv);
        }
        Message m = new Message();
        m.setSender_uid(UserManager.getInstance().getUserModel().getUid());
        m.setId(RandomIdGenerator.generateMessageId(UserManager.getInstance().getCurrentConv().getId()));
        m.setId_conv(UserManager.getInstance().getCurrentConv().getId());
        m.setContent(msg);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String format = simpleDateFormat.format(new Date());
        m.setDate(Integer.parseInt(format));
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
                if(bb == false){
                    UserManager.getInstance().setCurrentConv(null);
                }
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
