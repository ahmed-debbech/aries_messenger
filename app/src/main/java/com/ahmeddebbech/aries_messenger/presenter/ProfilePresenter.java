package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.contracts.ContractProfileF;
import com.ahmeddebbech.aries_messenger.model.User;

public class ProfilePresenter extends Presenter implements ContractProfileF.Presenter {

    ContractProfileF.View act;
    public ProfilePresenter(ContractProfileF.View act){
        this.act = act;
    }
    @Override
    public void getData() {
        User u = UserManager.getInstance().getUserModel();
        act.setTextsForViews(u.getDisplayName(),u.getUsername(),u.getBio(),u.getPhotoURL());
    }

    @Override
    public void returnData(Object obj) {

    }
}
