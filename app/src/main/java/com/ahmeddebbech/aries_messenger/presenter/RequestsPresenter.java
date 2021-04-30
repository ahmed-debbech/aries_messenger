package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractRequests;
import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.ItemUser;

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
        }
    }

    @Override
    public void seekForPendingRequest() {
        /*we get all connections from the model if the list is not refreshed or
        empty we go fetch from the database*/
        Map<String, String> map = UserManager.getInstance().getUserModel().getConnections();
        if((map == null) || (map.isEmpty())){
            DatabaseOutput doo = new DatabaseOutput(DatabaseOutputKeys.CONVERT_TO_USERS, new ArrayList<ItemUser>());
            returnData(doo);
        }else{
            List<String> listPending = UserManager.getInstance().getPendingConnections();
            //we convert the UIDs strings to actual user data from DB
            DbConnector.connectToConvertUidsToUsers(listPending, this);
        }
    }
}
