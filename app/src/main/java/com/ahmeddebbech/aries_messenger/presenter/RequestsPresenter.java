package com.ahmeddebbech.aries_messenger.presenter;

import com.ahmeddebbech.aries_messenger.contracts.ContractRequests;
import com.ahmeddebbech.aries_messenger.contracts.ContractSearch;
import com.ahmeddebbech.aries_messenger.model.ItemList;

import java.util.ArrayList;

public class RequestsPresenter extends Presenter implements ContractRequests.Presenter {
    ContractRequests.View act;

    public RequestsPresenter(ContractRequests.View act){
        this.act = act;
    }

    @Override
    public void returnData(Object o){
        if(o instanceof ArrayList) {
            ArrayList<ItemList> list = (ArrayList<ItemList>)o;
            act.showResults(list);
        }
    }

}
