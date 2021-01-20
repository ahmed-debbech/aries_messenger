package com.ahmeddebbech.aries_messenger.contracts;

import com.ahmeddebbech.aries_messenger.model.ItemList;
import com.ahmeddebbech.aries_messenger.views.adapter.SearchAdapter;

import java.util.ArrayList;

public interface ContractSearch {
    interface  View{
        void showResults(ArrayList<ItemList> listOfItems);
        void clearList();
    }
    interface Presenter{
        void fillSearchResults(String text);
    }
}
