package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.contracts.ContractProfileF;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.User;

public class ProfilePresenter extends Presenter implements ContractProfileF.Presenter {

    ContractProfileF.View act;
    public ProfilePresenter(ContractProfileF.View act){
        this.act = act;
    }
    @Override
    public void getData() {
        User u = UserManager.getInstance().getUserModel();
        int num = UserManager.getInstance().getConnectionsNumber();
        act.setTextsForViews(u.getDisplayName(),u.getUsername(),u.getBio(),u.getPhotoURL(), num);
    }

    @Override
    public void returnData(DatabaseOutput obj) {

    }
}
