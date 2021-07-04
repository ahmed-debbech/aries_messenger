package com.ahmeddebbech.aries_messenger.contracts;

import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.model.User;

import java.util.List;

public interface ContractConversation {
    interface View{
        void showUserData(User u);
        void showHint(Boolean res);
        void loadMessages(List<Message> list);
        void showError(String error);
        void clearField();
        void showTypingLabel(String id);
        void hideTypingLabel();
        void addNewMessage(Message m);
        void updateMessage(Message m);
    }
    interface Presenter{
        void sendMessage(String msg, String receiver);
        void loadUser(String uid);
        void getConversation(String uidA, String uidB);
        void trackIsTypingStatus();
        void sendTypingSignal(boolean signal);
        void trackNewMessages();
        void closeConversation();
    }
}
