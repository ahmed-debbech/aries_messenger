package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.contracts.ContractSearchItem;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.User;

import java.util.List;

public class SearchItemPresenter extends Presenter implements ContractSearchItem.Presenter {

    private ContractSearchItem.View act;

    public SearchItemPresenter(ContractSearchItem.View act){
        this.act = act;
    }
    @Override
    public void returnData(Object obj) {
        if (obj instanceof List) {
            List<String> connections = (List) obj;
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
