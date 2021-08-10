package com.ahmeddebbech.aries_messenger.contracts;

import android.os.Bundle;

import com.ahmeddebbech.aries_messenger.model.ItemUser;

import java.util.List;

public interface ContractConnectionsFrag {
    interface View{
        void showContacts(List<ItemUser> list);
        void showNewMessageBadge();
        void sendResult(Bundle result);
    }
    interface Presenter{
        void loadContacts(String uid);
        void checkNewMessages();
    }
}
