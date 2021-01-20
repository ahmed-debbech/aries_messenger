package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.contracts.ContractSearch;
import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.ItemList;
import com.ahmeddebbech.aries_messenger.util.InputChecker;

import java.util.ArrayList;

public class SearchPresenter extends Presenter implements ContractSearch.Presenter {

    ContractSearch.View act;
    public SearchPresenter(ContractSearch.View act){
        this.act = act;
    }
    @Override
    public void fillSearchResults(String text) {
        text = text.trim();
        text = text.toLowerCase();
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
            ArrayList<ItemList> list = (ArrayList<ItemList>)o;
            act.showResults(list);
        }
    }
}
