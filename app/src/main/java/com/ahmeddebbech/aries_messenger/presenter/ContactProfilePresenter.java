package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractContactProfile;
import com.ahmeddebbech.aries_messenger.database.DbBasic;
import com.ahmeddebbech.aries_messenger.model.User;

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
        }
    }
}
