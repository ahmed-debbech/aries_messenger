package com.ahmeddebbech.aries_messenger.contracts;

import com.ahmeddebbech.aries_messenger.model.ItemUser;

import java.util.ArrayList;

public interface ContractSearch {
    interface  View{
        void showResults(ArrayList<ItemUser> listOfItems);
        void clearList();
    }
    interface Presenter{
        void fillSearchResults(String text);
    }
}
