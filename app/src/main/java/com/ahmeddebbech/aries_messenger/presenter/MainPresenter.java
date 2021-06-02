package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractMain;
import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DbBasic;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.User;

import java.util.List;
import java.util.Map;

public class MainPresenter extends Presenter implements ContractMain.Presenter {

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

    public void getDatafromDatabase(String uid){
        DbConnector.connectToGetUserData(uid, this);
    }
    @Override
    public void returnData(DatabaseOutput o){
        if(o.getDatabaseOutputkey() == DatabaseOutputKeys.GET_USER_DATA) {
            User u = (User)o.getObj();
            UserManager.getInstance().updateWithCopy(u);
            DbConnector.connectToGetConnections(UserManager.getInstance().getUserModel().getUid(), this);
            Log.d("trr-", ""+UserManager.getInstance().getUserModel().getUid());
            UserManager.getInstance().setAvailabilityStatus(User.ONLINE);
        }else{
            if(o.getDatabaseOutputkey() == DatabaseOutputKeys.GET_CONNECTIONS){
                Map<String, String> l = (Map<String, String>)o.getObj();
                UserManager.getInstance().getUserModel().setConnections(l);
                act.setupUi();
            }
        }
    }
}
