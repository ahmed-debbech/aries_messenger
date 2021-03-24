package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractConversation;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.User;

public class ConversationPresenter extends Presenter implements ContractConversation.Presenter {
    private ContractConversation.View activity;

    public ConversationPresenter(ContractConversation.View act){
        this.activity = act;
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
                }
            }
        }
    }
}
