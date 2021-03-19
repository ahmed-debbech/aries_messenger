package com.ahmeddebbech.aries_messenger.contracts;

import com.ahmeddebbech.aries_messenger.model.User;

public interface ContractConversation {
    interface View{
        void retContactData(User u);
        void showHint();
    }
    interface Presenter{
        void loadData(String uid);
        void conversationExists(String uid);
    }
}
