package com.ahmeddebbech.aries_messenger.presenter;

import android.os.Messenger;
import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractConversation;
import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DatabaseReferences;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConversationPresenter extends Presenter implements ContractConversation.Presenter {
    private ContractConversation.View activity;
    private String convid;

    public ConversationPresenter(ContractConversation.View act, String conv){
        this.activity = act;
        MessengerManager.getInstance().setMessages(null);
        this.convid = conv;
    }

    @Override
    public void sendMessage(String msg, String receiver) {
        if (!msg.equals("")) {
            if (!InputChecker.isLonger(msg, 1000)) {
                Message mm = MessengerManager.getInstance().sendMessage(msg, receiver, this);
                activity.pushMessageToScreen(mm);
                activity.clearField();
            } else {
                activity.showError("Your Message is too long");
            }
        }
    }

    private void loadUser(String uid) {
        DbConnector.connectToGetUserByUid(uid, DatabaseOutputKeys.GET_USER_FROM_UID, this, false);
    }

    public void getConversation(String uidA, String uidB) {
        DbConnector.connectToGetOneConversation(uidA, uidB, this);
    }

    @Override
    public void trackIsTypingStatus(String uid) {
        DbConnector.connectToTrackWhosTyping(UserManager.getInstance().getConvId(uid),this);
    }

    public void sendTypingSignal(boolean signal) {
        if(MessengerManager.getInstance().getCurrentConv() != null) {
            if (signal == true) {
                DbConnector.connectToSendTypingSignal(UserManager.getInstance().getUserModel().getUid(), MessengerManager.getInstance().getCurrentConv().getId(), true);
            } else {
                DbConnector.connectToSendTypingSignal(UserManager.getInstance().getUserModel().getUid(), MessengerManager.getInstance().getCurrentConv().getId(), false);
            }
        }
    }

    public void trackNewMessages(String uid) {
        if(UserManager.getInstance().getUserModel().getConversations() != null) {
            if (UserManager.getInstance().getUserModel().getConversations().containsKey(uid)) {
                String id = UserManager.getInstance().getUserModel().getConversations().get(uid);
                DbConnector.connectToGetNewMessage(id, this);
            }
        }
    }

    @Override
    public void closeConversation() {
        DatabaseReferences.removeConvListener();
        MessengerManager.getInstance().closeConversation();
    }

    @Override
    public void blockConnection(String uid) {
        UserManager.getInstance().removeContact(uid);
        DbConnector.connectToBlockConnection(UserManager.getInstance().getUserModel().getUid(), uid);
    }

    @Override
    public void initChat( String uidB) {
        this.loadUser(uidB);
        this.getConversation(UserManager.getInstance().getUserModel().getUid(), uidB);
        this.trackNewMessages(uidB);
        this.trackIsTypingStatus(uidB);
    }

    private void updateSeenIndex(Conversation cv){
        DbConnector.connectToUpdateConversation(cv, UserManager.getInstance().getUserModel().getUid());
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
                if(!m.getSender_uid().equals(UserManager.getInstance().getUserModel().getUid())) {
                    MessengerManager.getInstance().updateMessageState(Message.SEEN, convid, m.getId());
                }
                activity.addNewMessage(m);
            }else{
                if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.GET_CONV){
                    // load the meta
                    if(obj.getObj() != null) {
                        Conversation cv = (Conversation) obj.getObj();
                        MessengerManager.getInstance().setCurrentConv(cv);
                        activity.showHint(true);
                        updateSeenIndex(cv);
                    }else{
                        MessengerManager.getInstance().setCurrentConv(null);
                        activity.showHint(false);
                    }
                }else{
                    if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.GET_TYPERS){
                        List<String> ppl_type = (List<String>)obj.getObj();
                        boolean found = false;
                        for(String id : ppl_type){
                            if(!UserManager.getInstance().getUserModel().getUid().equals(id)){
                                found = true;
                                DbConnector.connectToGetUserByUid(id, DatabaseOutputKeys.GET_USER_TYPING_NAME, this, true);
                            }
                        }
                        if(found == false){
                            activity.hideTypingLabel();
                        }
                    }else{
                        if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.GET_USER_TYPING_NAME){
                            User id = (User)obj.getObj();
                            activity.showTypingLabel(id.getDisplayName());
                        }else{
                            if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.GET_CHANGED_MESSAGE){
                                Message m = (Message)obj.getObj();
                                MessengerManager.getInstance().updateMessage(m);
                                activity.updateMessage(m);
                            }
                        }
                    }
                }
            }
        }
    }
}
