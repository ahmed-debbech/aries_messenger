package com.ahmeddebbech.aries_messenger.contracts;

import com.ahmeddebbech.aries_messenger.model.AriesError;
import com.ahmeddebbech.aries_messenger.model.ItemUser;

import java.util.ArrayList;

public interface ContractSearch {
    interface  View{
        void showResults(ArrayList<ItemUser> listOfItems);
        void clearList();
        void showError(AriesError er);
    }
    interface Presenter{
        void fillSearchResults(String text);
    }
}
