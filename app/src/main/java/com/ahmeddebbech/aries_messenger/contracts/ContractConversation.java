package com.ahmeddebbech.aries_messenger.contracts;

import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.model.User;

import java.util.List;

public interface ContractConversation {
    interface View{
        void retContactData(User u);
        void showHint(Boolean res);
        void loadMessages(List<Message> list);
    }
    interface Presenter{
        void sendMessage(String msg);
        void loadData(String uid);
        void conversationExists(String uid);
        void getConversationMetadata(String uidA, String uidB);
    }
}
