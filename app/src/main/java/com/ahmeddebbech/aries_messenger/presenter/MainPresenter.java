package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractMain;
import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DbBasic;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.util.GeneralUtils;

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

    @Override
    public void getConnections() {
        if(UserManager.getInstance().getConnectionsNumber() != 0) {
            DbConnector.connectToGetConnections(UserManager.getInstance().getUserModel().getUid(), this);
        }
    }

    @Override
    public void getConversations() {
        DbConnector.connectToGetConversationsIds(UserManager.getInstance().getUserModel().getUid(), this);
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
        }else{
            if(o.getDatabaseOutputkey() == DatabaseOutputKeys.GET_CONNECTIONS){
                Map<String, String> l = (Map<String, String>)o.getObj();
                if(GeneralUtils.twoStringMapsEqual(l, UserManager.getInstance().getUserModel().getConnections()) == false) {
                    UserManager.getInstance().getUserModel().setConnections(l);
                    act.setupUi();
                }
            }else{
                if(o.getDatabaseOutputkey() == DatabaseOutputKeys.CONVS_IDS_GETTER) {
                    Map<String, String> l = (Map<String, String>) o.getObj();
                    if(GeneralUtils.twoStringMapsEqual(l, UserManager.getInstance().getUserModel().getConversations()) == false) {
                        UserManager.getInstance().getUserModel().setConversations(l);
                    }
                }
            }
        }
    }
}
