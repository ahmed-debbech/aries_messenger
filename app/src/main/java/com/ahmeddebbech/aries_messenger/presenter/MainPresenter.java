package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractMain;
import com.ahmeddebbech.aries_messenger.database.DbBasic;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.Conversation;
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
    public void returnData(Object o){
        if(o instanceof User) {
            User u = (User)o;
            UserManager.getInstance().updateWithCopy(u);
            if(UserManager.getInstance().getUserModel().getConnections().size() == 0) {
                DbConnector.connectToGetConnections(UserManager.getInstance().getUserModel().getUid(), this);
            }else{
                act.setupUi();
                DbConnector.connectToGetConversations(UserManager.getInstance().getUserModel().getUid(), this);
            }
        }else{
            if(o instanceof Map){
                Map<String, String> l = (Map<String, String>)o;
                UserManager.getInstance().getUserModel().setConnections(l);
                DbConnector.connectToGetConversations(UserManager.getInstance().getUserModel().getUid(), this);
                act.setupUi();
            }else{
                if(o instanceof List){
                    List<Conversation> li = (List<Conversation>)o;
                    UserManager.getInstance().getUserModel().setConversations(li);
                    System.out.println("*** conv " + UserManager.getInstance().getUserModel().getConversations().get(0).getId());
                }
            }
        }
    }
}
