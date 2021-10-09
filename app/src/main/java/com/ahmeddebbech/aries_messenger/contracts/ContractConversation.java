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
        void pushMessageToScreen(Message m);
    }
    interface Presenter{
        void sendMessage(String msg, String receiver);
        void initChat(String uidB);
        void getConversation(String uidA, String uidB);
        void trackIsTypingStatus(String uid);
        void sendTypingSignal(boolean signal);
        void trackNewMessages(String uid);
        void closeConversation();
        void blockConnection(String uid);
    }
}
