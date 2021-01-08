package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractContactProfile;
import com.ahmeddebbech.aries_messenger.database.DbBasic;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.database.DbUtil;
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
    public void returnData(Object o){
        if(o instanceof User) {
            User u = (User)o;
            act.loadData(u.getDisplayName(),u.getPhotoURL(), u.getBio());
        }else{
            if(o instanceof Boolean){
                Boolean b = (Boolean)o;
                if(b == true){
                    act.showAddedAck();
                }
            }
        }
    }
    @Override
    public void addToContact(String uid){
        //DbBasic.addContact(UserManager.getInstance().getUserModel().getUid(), uid, this);
        //DbUtil.getLastConnectionNumber(UserManager.getInstance().getUserModel().getUid());
        DbConnector.connectToAddNewContact(UserManager.getInstance().getUserModel().getUid(), uid, this);
    }
}
