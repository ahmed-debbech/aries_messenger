package com.ahmeddebbech.aries_messenger.presenter;

import android.os.Bundle;

import com.ahmeddebbech.aries_messenger.contracts.ContractConnectionsFrag;
import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Map;

public class ConnectionsFragPresenter extends Presenter implements ContractConnectionsFrag.Presenter {

    private ContractConnectionsFrag.View frag;

    public ConnectionsFragPresenter(ContractConnectionsFrag.View v){
        this.frag = v;
    }
    @Override
    public void loadContacts(String uid) {
        DbConnector.connectToGetConnections(FirebaseAuth.getInstance().getCurrentUser().getUid(), this);
    }

    @Override
    public void returnData(DatabaseOutput dot) {
        if(dot.getDatabaseOutputkey() == DatabaseOutputKeys.CONVERT_TO_USERS){
            List<ItemUser> lis = (List<ItemUser>)dot.getObj();
            frag.showContacts(lis);
        }else{
            if(dot.getDatabaseOutputkey() == DatabaseOutputKeys.GET_CONNECTIONS){
                Map<String, String> l = (Map<String, String>)dot.getObj();
                //if(GeneralUtils.twoStringMapsEqual(l, UserManager.getInstance().getUserModel().getConnections()) == false) {
                UserManager.getInstance().getUserModel().setConnections(l);
                List<String> penders = UserManager.getInstance().getConnectionsByType(UserManager.PENDING);
                if(!penders.isEmpty()) {
                    Bundle result = new Bundle();
                    result.putBoolean("result", true);
                    frag.sendResult(result);
                }else{
                    Bundle result = new Bundle();
                    result.putBoolean("result", false);
                    frag.sendResult(result);
                }
                DbConnector.connectToConvertUidsToUsers(UserManager.getInstance().getConnectionsByType(UserManager.CONNECTED), this);
                //}
            }
        }
    }
}
