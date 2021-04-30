package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.contracts.ContractItemList;
import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;

import java.util.List;
import java.util.Map;

public class RequestsItemPresenter extends Presenter implements ContractItemList.Presenter {

    private ContractItemList.View act;

    public RequestsItemPresenter(ContractItemList.View act){
        this.act = act;
    }
    @Override
    public void returnData(DatabaseOutput obj) {
        if (obj.getDatabaseOutputkey() == DatabaseOutputKeys.GET_CONNECTIONS) {
            Map<String, String> connections = (Map) obj.getObj();
            UserManager.getInstance().getUserModel().setConnections(connections);
        } else {
            if (obj.getDatabaseOutputkey() == DatabaseOutputKeys.REMOVE_CONTACT
            || obj.getDatabaseOutputkey() == DatabaseOutputKeys.ADD_CONTACT
            || obj.getDatabaseOutputkey() == DatabaseOutputKeys.ACCEPT_CONTACT) {
                act.updateUi();
            }
        }
    }
    @Override
    public void addToContact(String uid) {
        UserManager.getInstance().addContact(uid);
        DbConnector.connectToAddNewContact(UserManager.getInstance().getUserModel().getUid(), uid, this);
    }

    @Override
    public void removeFromContact(String uid) {
        UserManager.getInstance().removeContact(uid);
        DbConnector.connectToRemoveContact(UserManager.getInstance().getUserModel().getUid(), uid, this);
    }

    @Override
    public void acceptContact(String uid) {
        UserManager.getInstance().setConnectionStatusToConnected(uid);
        DbConnector.connectToAcceptContact(UserManager.getInstance().getUserModel().getUid(), uid, this);
    }

    @Override
    public void updateConnectionsList(String uid) {
        DbConnector.connectToGetConnections(uid, this);
    }
}
