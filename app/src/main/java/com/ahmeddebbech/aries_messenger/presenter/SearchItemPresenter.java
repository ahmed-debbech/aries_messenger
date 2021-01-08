package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.contracts.ContractSearchItem;
import com.ahmeddebbech.aries_messenger.database.DbConnector;

public class SearchItemPresenter extends Presenter implements ContractSearchItem.Presenter {

    private ContractSearchItem.View act;

    public SearchItemPresenter(ContractSearchItem.View act){
        this.act = act;
    }
    @Override
    public void returnData(Object obj) {
        act.updateUi();
    }

    @Override
    public void addToContact(String uid) {
        DbConnector.connectToAddNewContact(UserManager.getInstance().getUserModel().getUid(), uid, this);
    }

}
