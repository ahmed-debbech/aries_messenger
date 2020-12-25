package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.contracts.ContractSearch;
import com.ahmeddebbech.aries_messenger.database.DbBasic;

public class SearchPresenter implements ContractSearch.Presenter {

    ContractSearch.View act;
    public SearchPresenter(ContractSearch.View act){
        this.act = act;
    }
    @Override
    public void fillSearchResults(String text) {
        DbBasic.getAllUsersFromName(text);
    }
}
