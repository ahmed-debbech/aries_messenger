package com.ahmeddebbech.aries_messenger.contracts;

import com.ahmeddebbech.aries_messenger.model.ItemUser;

import java.util.List;

public interface ContractConnectionsFrag {
    interface View{
        void showContacts(List<ItemUser> list);
    }
    interface Presenter{
        void loadContacts(String uid);
    }
}
