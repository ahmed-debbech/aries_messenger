package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.contracts.ContractSearch;
import com.ahmeddebbech.aries_messenger.database.DbBasic;
import com.ahmeddebbech.aries_messenger.model.SearchItem;

import java.util.ArrayList;

public class SearchPresenter implements ContractSearch.Presenter {

    ContractSearch.View act;
    public SearchPresenter(ContractSearch.View act){
        this.act = act;
    }
    @Override
    public void fillSearchResults(String text) {
        if((!text.equals("")) && (!text.equals("@"))) {
            DbBasic.searchAllUsersFromName(text, this);
        }
    }
    public void returnDataFromDB(ArrayList<SearchItem> list){
        act.showResults(list);
    }
}
