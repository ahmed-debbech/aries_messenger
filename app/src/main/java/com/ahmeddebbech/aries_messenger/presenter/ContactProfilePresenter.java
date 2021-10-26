package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.contracts.ContractContactProfile;
import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.User;

public class ContactProfilePresenter extends Presenter implements ContractContactProfile.Presenter {

    private ContractContactProfile.View act;

    public ContactProfilePresenter(ContractContactProfile.View act){
        this.act = act;
    }

    @Override
    public void getConnectionsNumber(String uid) {
        DbConnector.connectToGetUserConnectionsNumber(uid, this);
    }

    @Override
    public void fillUiWithData(String uid) {
        DbConnector.connectToGetUserData(uid, this);
    }
    @Override
    public void addToContact(String uid){
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
        UserManager.getInstance().setConnectionStatus(uid, UserManager.CONNECTED);
        DbConnector.connectToAcceptContact(UserManager.getInstance().getUserModel().getUid(), uid, this);
    }
    public void returnData(DatabaseOutput o){
        if(o.getDatabaseOutputkey() == DatabaseOutputKeys.GET_USER_DATA) {
            User u = (User)o.getObj();
            act.loadData(u.getDisplayName(),u.getPhotoURL(), u.getBio());
        }else{
            if(o.getDatabaseOutputkey() == DatabaseOutputKeys.REMOVE_CONTACT
                    || o.getDatabaseOutputkey() == DatabaseOutputKeys.ACCEPT_CONTACT
                    || o.getDatabaseOutputkey() == DatabaseOutputKeys.ADD_CONTACT){
                Boolean b = (Boolean)o.getObj();
                if(b == true){
                    act.updateUi();
                }
            }else{
                if(o.getDatabaseOutputkey() == DatabaseOutputKeys.GET_CONNECTIONS_NUMBER){
                    Long h = (Long)o.getObj();
                    act.updateConnections(h.intValue());
                }
            }
        }
    }
}
