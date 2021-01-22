package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.contracts.ContractItemList;
import com.ahmeddebbech.aries_messenger.database.DbConnector;

import java.util.List;
import java.util.Map;

public class RequestsItemPresenter extends Presenter implements ContractItemList.Presenter {

    private ContractItemList.View act;

    public RequestsItemPresenter(ContractItemList.View act){
        this.act = act;
    }
    @Override
    public void returnData(Object obj) {
        if (obj instanceof List) {
            Map<String, String> connections = (Map) obj;
            UserManager.getInstance().getUserModel().setConnections(connections);
        } else {
            if (obj instanceof Boolean) {
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
    public void updateConnectionsList(String uid) {
        DbConnector.connectToGetConnections(uid, this);
    }
}
