package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.contracts.ContractMain;
import com.ahmeddebbech.aries_messenger.database.DbBasic;
import com.ahmeddebbech.aries_messenger.model.User;
import com.google.firebase.auth.FirebaseAuth;

public class MainPresenter implements ContractMain.Presenter {

    private ContractMain.View act;

    public MainPresenter(ContractMain.View activity){
        this.act = activity;
    }
    @Override
    public void fillViewsWithUserData() {
        String disp = UserManager.getInstance().getUserModel().getDisplayName();
        String usr = UserManager.getInstance().getUserModel().getUsername();
        String photo = UserManager.getInstance().getUserModel().getPhotoURL();
        act.renderViewsWithData(disp,usr,photo);
    }
    public void getDatafromDatabase(){
        DbBasic.getUserData(UserManager.getInstance().getUserModel().getUid(), this);
    }
    @Override
    public void returnData(){
        act.setupUi();
    }
}
