package com.ahmeddebbech.aries_messenger.presenter;

import android.os.Messenger;
import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractConversation;
import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DbBasic;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.database.DbConversations;
import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.util.InputChecker;
import com.ahmeddebbech.aries_messenger.util.RandomIdGenerator;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
        if (!msg.equals("")) {
            if (!InputChecker.isLonger(msg, 1000)) {
                MessengerManager.getInstance().sendMessage(msg, receiver, this);
                activity.clearField();
            } else {
                activity.showError("Your Message is too long");
            }
        }
    }

    @Override
    public void loadUser(String uid) {
        DbConnector.connectToGetUserByUid(uid, this, false);
    }

    @Override
    public void getConversation(String uidA, String uidB) {
        DbConnector.connectToGetOneConversation(uidA, uidB, this);
    }

    @Override
    public void trackIsTypingStatus() {
        DbConnector.connectToTrackWhosTyping(MessengerManager.getInstance().getCurrentConv().getId(),this);
    }

    @Override
    public void sendTypingSignal(boolean signal) {
        if(MessengerManager.getInstance().getCurrentConv() != null) {
            if (signal == true) {
                DbConnector.connectToSendTypingSignal(UserManager.getInstance().getUserModel().getUid(), MessengerManager.getInstance().getCurrentConv().getId(), true);
            } else {
                DbConnector.connectToSendTypingSignal(UserManager.getInstance().getUserModel().getUid(), MessengerManager.getInstance().getCurrentConv().getId(), false);
            }
        }
    }

    @Override
    public void getMessages() {
        DbConnector.connectToGetMessages(MessengerManager.getInstance().getCurrentConv().getId(), this);
    }

    @Override
    public void trackNewMessages() {
        DbConnector.connectToGetNewMessage(MessengerManager.getInstance().getCurrentConv().getId(),this);
    }

    @Override
    public void returnData(DatabaseOutput obj) {
        if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.GET_USER_FROM_UID){
            User u = (User)obj.getObj();
            activity.showUserData(u);
        }else{
            if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.GET_NEW_MSG){
                Message m = (Message)obj.getObj();
                MessengerManager.getInstance().addNewMessage(m);
                activity.addNewMessage(m);
            }else{
                if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.GET_CONV){
                    // load the meta
                    if(obj.getObj() != null) {
                        Conversation cv = (Conversation) obj.getObj();
                        MessengerManager.getInstance().setCurrentConv(cv);
                        activity.showHint(true);
                    }else{
                        MessengerManager.getInstance().setCurrentConv(null);
                        activity.showHint(false);
                    }
                }else{
                    if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.GET_MESSAGES){
                        Log.d("$$e-", "here");
                        List<Message> lis = (List<Message>)obj.getObj();
                        activity.loadMessages(lis);
                        MessengerManager.getInstance().setMessages(lis);
                        MessengerManager.getInstance().updateMessagesStatus(Message.SEEN, lis, MessengerManager.getInstance().getCurrentConv().getId());
                    }else{
                        if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.GET_TYPERS){
                            List<String> ppl_type = (List<String>)obj.getObj();
                            boolean found = false;
                            for(String id : ppl_type){
                                if(!UserManager.getInstance().getUserModel().getUid().equals(id)){
                                    found = true;
                                    DbConnector.connectToGetUserByUid(id, this, true);
                                }
                            }
                            if(found == false){
                                activity.hideTypingLabel();
                            }
                        }else{
                            if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.GET_USER_FROM_UID){
                                User id = (User)obj.getObj();
                                activity.showTypingLabel(id.getDisplayName());
                            }
                        }
                    }
                }
            }
        }
    }
}
