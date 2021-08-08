package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractRequests;
import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestsPresenter extends Presenter implements ContractRequests.Presenter {
    ContractRequests.View act;

    public RequestsPresenter(ContractRequests.View act){
        this.act = act;
    }

    @Override
    public void returnData(DatabaseOutput o){
        if(o.getDatabaseOutputkey() == DatabaseOutputKeys.CONVERT_TO_USERS) {
            ArrayList<ItemUser> list = (ArrayList<ItemUser>)o.getObj();
            act.showResults(list);
        }else{
            if(o.getDatabaseOutputkey() == DatabaseOutputKeys.GET_CONNECTIONS){
                Map<String, String> list = (Map<String, String>)o.getObj();
                UserManager.getInstance().getUserModel().setConnections(list);
                List<String> listPending = UserManager.getInstance().getConnectionsByType(UserManager.PENDING);
                DbConnector.connectToConvertUidsToUsers(listPending, this);
            }
        }
    }

    @Override
    public void seekForPendingRequest() {
        DbConnector.connectToGetConnections(UserManager.getInstance().getUserModel().getUid(), this);
    }
}
