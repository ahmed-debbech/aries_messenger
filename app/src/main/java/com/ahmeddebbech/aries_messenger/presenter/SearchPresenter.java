package com.ahmeddebbech.aries_messenger.presenter;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.contracts.ContractSearch;
import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.database.DbSync;
import com.ahmeddebbech.aries_messenger.model.DatabaseOutput;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.util.InputChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchPresenter extends Presenter implements ContractSearch.Presenter {

    ContractSearch.View act;
    public SearchPresenter(ContractSearch.View act){
        //DbSync.syncUserConnections(UserManager.getInstance().getUserModel().getUid(), this);
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
    public void returnData(DatabaseOutput o){
        if(o.getDatabaseOutputkey() == DatabaseOutputKeys.SEARCH_ALL_USERS_BY_NAME) {
            ArrayList<ItemUser> list = (ArrayList<ItemUser>)o.getObj();
            act.showResults(list);
        }else{
            if(o.getDatabaseOutputkey() == DatabaseOutputKeys.GET_CONNECTIONS){
                HashMap<String, String> m = (HashMap<String,String>)o.getObj();
                UserManager.getInstance().getUserModel().setConnections(m);
            }
        }
    }
}
