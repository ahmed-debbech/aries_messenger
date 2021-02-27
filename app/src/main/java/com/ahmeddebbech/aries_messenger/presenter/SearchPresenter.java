package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractSearch;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.database.DbSync;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.util.InputChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchPresenter extends Presenter implements ContractSearch.Presenter {

    ContractSearch.View act;
    public SearchPresenter(ContractSearch.View act){
        DbSync.syncUserConnections(UserManager.getInstance().getUserModel().getUid(), this);
        this.act = act;
    }
    @Override
    public void fillSearchResults(String text) {
        text = text.trim();
        if((!text.equals("")) && (!text.equals("@"))
                && (!InputChecker.isLonger(text ,60))) {
            DbConnector.connectToSearchForUsersWithName(text, this);
        }else{
            act.clearList();
        }
    }
    @Override
    public void returnData(Object o){
        if(o instanceof ArrayList) {
            ArrayList<ItemUser> list = (ArrayList<ItemUser>)o;
            act.showResults(list);
        }else{
            if(o instanceof HashMap){
                HashMap<String, String> m = (HashMap<String,String>)o;
                UserManager.getInstance().getUserModel().setConnections(m);
            }
        }
    }
}
