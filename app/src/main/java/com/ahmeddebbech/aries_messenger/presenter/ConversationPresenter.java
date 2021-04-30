package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractConversation;
import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
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
    public void returnData(DatabaseOutput obj) {
        if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.GET_USER_DATA){
            User u = (User)obj.getObj();
            activity.retContactData(u);
        }else{
            if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.CHECK_CONV_EXISTS){
                Boolean bb = (Boolean) obj.getObj();
                if(bb == false){
                    UserManager.getInstance().setCurrentConv(null);
                }
                activity.showHint(bb);
            }else{
                if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.GET_CONV){
                    // load the meta
                    Conversation cv = (Conversation)obj.getObj();
                    UserManager.getInstance().setCurrentConv(cv);
                    DbConnector.connectToGetMessages(cv.getId(), this);
                }else{
                    if(obj.getDatabaseOutputkey() == DatabaseOutputKeys.GET_MESSAGES){
                        List<Message> lis = (List<Message>)obj.getObj();
                        activity.loadMessages(lis);
                        MessengerManager.getInstance().updateMessagesStatus(Message.SEEN, lis, UserManager.getInstance().getCurrentConv().getId());
                    }
                }
            }
        }
    }
}
