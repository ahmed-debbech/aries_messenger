package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractMain;
import com.ahmeddebbech.aries_messenger.database.BackendServiceApi;
import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DbBasic;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.util.GeneralUtils;
import com.ahmeddebbech.aries_messenger.views.fragments.ConnectionsFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter extends Presenter implements ContractMain.Presenter {

    private ContractMain.View act;

    public MainPresenter(ContractMain.View activity){
        this.act = activity;

        Retrofit retro = new Retrofit.Builder()
                .baseUrl("https://powerful-brushlands-08899.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DbConnector.backendServiceApi = retro.create(BackendServiceApi.class);
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
        DbConnector.connectToGetConnections(FirebaseAuth.getInstance().getCurrentUser().getUid(), this);
    }

    @Override
    public void getConversations() {
        DbConnector.connectToGetConversationsIds(FirebaseAuth.getInstance().getCurrentUser().getUid(), this);
    }

    @Override
    public void getBlocked(){
        DbConnector.connectToGetBlockedUsers(FirebaseAuth.getInstance().getCurrentUser().getUid(), this);
    }

    public void getDatafromDatabase(String uid){
        DbConnector.connectToGetUserData(uid, this);
    }
    @Override
    public void returnData(DatabaseOutput o){
        if(o.getDatabaseOutputkey() == DatabaseOutputKeys.GET_USER_DATA) {
            User u = (User)o.getObj();
            UserManager.getInstance().updateWithCopy(u);
            act.setupUi();
        }else{
            if(o.getDatabaseOutputkey() == DatabaseOutputKeys.GET_CONNECTIONS){
                Map<String, String> l = (Map<String, String>)o.getObj();
                //if(GeneralUtils.twoStringMapsEqual(l, UserManager.getInstance().getUserModel().getConnections()) == false) {
                UserManager.getInstance().getUserModel().setConnections(l);
                    List<String> penders = UserManager.getInstance().getConnectionsByType(UserManager.PENDING);
                    if(!penders.isEmpty()) {
                        act.setPendingBadge(true);
                    }else{
                        act.setPendingBadge(false);
                    }
                //}
            }else{
                if(o.getDatabaseOutputkey() == DatabaseOutputKeys.CONVS_IDS_GETTER) {
                    Map<String, String> l = (Map<String, String>) o.getObj();
                    if(GeneralUtils.twoStringMapsEqual(l, UserManager.getInstance().getUserModel().getConversations()) == false) {
                        UserManager.getInstance().getUserModel().setConversations(l);
                        MessengerManager.getInstance().checkNewMessages(UserManager.getInstance().getUserModel().getUid(), MessengerManager.getInstance());
                    }
                }else{
                    if(o.getDatabaseOutputkey() == DatabaseOutputKeys.GET_BLOCKED) {
                        List<String> l = (List<String>) o.getObj();
                        UserManager.getInstance().getUserModel().setBlockedUsers(l);
                    }
                }
            }
        }
    }
}
