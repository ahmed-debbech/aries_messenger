package com.ahmeddebbech.aries_messenger.contracts;

import com.ahmeddebbech.aries_messenger.model.SearchItem;
import com.ahmeddebbech.aries_messenger.views.adapter.SearchAdapter;

import java.util.ArrayList;

public interface ContractSearch {
    interface  View{
        void showResults(ArrayList<SearchItem> listOfItems);
    }
    interface Presenter{
        void fillSearchResults(String text);
    }
}
