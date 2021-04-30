package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractContactProfile;
import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DbBasic;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.database.DbUtil;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.User;

import java.sql.Connection;

public class ContactProfilePresenter extends Presenter implements ContractContactProfile.Presenter {

    private ContractContactProfile.View act;

    public ContactProfilePresenter(ContractContactProfile.View act){
        this.act = act;
    }
    @Override
    public void fillUiWithData(String uid) {
        DbBasic.getUserData(uid,this);
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
            }
        }
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
        UserManager.getInstance().setConnectionStatusToConnected(uid);
        DbConnector.connectToAcceptContact(UserManager.getInstance().getUserModel().getUid(), uid, this);
    }
}
