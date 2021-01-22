package com.ahmeddebbech.aries_messenger.contracts;

import com.ahmeddebbech.aries_messenger.model.ItemUser;

import java.util.ArrayList;

public interface ContractRequests {
    interface  View{
        void showResults(ArrayList<ItemUser> listOfItems);
    }
    interface Presenter{
       void seekForPendingRequest();
    }
}
