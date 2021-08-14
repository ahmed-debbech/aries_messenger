package com.ahmeddebbech.aries_messenger.contracts;

import com.ahmeddebbech.aries_messenger.model.ItemUser;

import java.util.ArrayList;

public interface ContractBlocked {
    interface Presenter{
        void getBlockedConnections();
    }
    interface View{
        void showBlockedConnections(ArrayList<ItemUser> result);
    }
}
