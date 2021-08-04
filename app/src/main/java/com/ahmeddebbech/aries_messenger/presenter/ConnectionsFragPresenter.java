package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractConnectionsFrag;
import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.util.GeneralUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionsFragPresenter extends Presenter implements ContractConnectionsFrag.Presenter {

    private ContractConnectionsFrag.View frag;

    public ConnectionsFragPresenter(ContractConnectionsFrag.View v){
        this.frag = v;
    }
    @Override
    public void loadContacts(String uid) {
        Map<String, String> map = UserManager.getInstance().getUserModel().getConnections();
        if(map != null && map.size() > 0) {
            List<String> li = UserManager.getInstance().getConnectionsByType(UserManager.CONNECTED);
            DbConnector.connectToConvertUidsToUsers(li, this);
        }
    }


    @Override
    public void checkNewMessages() {
        //MessengerManager.getInstance().checkNewMessages(UserManager.getInstance().getUserModel().getUid(), this);
    }


    @Override
    public void returnData(DatabaseOutput dot) {
        if(dot.getDatabaseOutputkey() == DatabaseOutputKeys.CONVERT_TO_USERS){
            List<ItemUser> lis = (List<ItemUser>)dot.getObj();
            frag.showContacts(lis);
        }else{
            if(dot.getDatabaseOutputkey() == DatabaseOutputKeys.CHECK_NEW_MESSAGES_KEY) {
                MessengerManager.getInstance().updateMessagesStatus(Message.DELIVERED);
            }
        }
    }
}
