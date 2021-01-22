package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.contracts.ContractRequests;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.ItemUser;

import java.util.ArrayList;
import java.util.List;

public class RequestsPresenter extends Presenter implements ContractRequests.Presenter {
    ContractRequests.View act;

    public RequestsPresenter(ContractRequests.View act){
        this.act = act;
    }

    @Override
    public void returnData(Object o){
        if(o instanceof ArrayList) {
            ArrayList<ItemUser> list = (ArrayList<ItemUser>)o;
            for(ItemUser it : list){
                System.out.println("activity " + it.getUid());
            }
            act.showResults(list);
        }
    }

    @Override
    public void seekForPendingRequest(String text) {
        /*we get all connections from the model if the list is not refreshed or
        empty we go fetch from the database*/
        List<String> list = UserManager.getInstance().getUserModel().getConnections();
        if((list == null) || (list.isEmpty())){
            //fetching the DB
        }else{
            List<String> listPending = UserManager.getInstance().getPendingConnections();
            DbConnector.connectToConvertUidsToUsers(listPending, this);
        }
    }
}
