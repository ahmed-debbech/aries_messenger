package com.ahmeddebbech.aries_messenger.database;

import com.ahmeddebbech.aries_messenger.model.Conversation;
import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.ConversationPresenter;
import com.ahmeddebbech.aries_messenger.presenter.LoginPresenter;
import com.ahmeddebbech.aries_messenger.presenter.Presenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;

import java.util.List;

public class DbConnector {
    public static void connectToSignIn(LoginPresenter log) {
        DbUtil.userExistsInSignIn(log);
    }
    public static void connectToRegister(User user){
        DbBasic.addUserToDatabase(user);
    }
    public static void connectToAddNewContact(String uidUser, String uidAdded, Presenter pres){
        DbBasic.addContact(uidUser, uidAdded,pres);
    }
    public static void connectToModifyUser(User u){DbBasic.modifyUser(u);}
    public static void connectToGetUserData(String uid, Presenter pres){
        DbBasic.getUserData(uid, pres);
    }
    public static void connectToSearchForUsersWithName(String text, Presenter pres){DbBasic.searchAllUsersByName(text, pres);}
    public static void connectToGetConnections(String uid, Presenter pres){
        DbBasic.getConnections(uid, pres);
    }
    public static void connectToRemoveContact(String uid, String delUid, Presenter pres){
        DbBasic.removeContact(uid, delUid, pres);
    }
    public static void connectToConvertUidsToUsers(List<String> uids, Presenter pres){
        DbBasic.convertUidsToUsers(uids ,pres);
    }
    public static void connectToAcceptContact(String uidUser, String addedUid, Presenter pres){
        DbBasic.acceptContact( uidUser,  addedUid,  pres);
    }

    public static void connectToGetOneConversation(String uidA, String uidB, Presenter pres){
        DbConversations.getConversation(uidA, uidB, pres);
    }
    public static void connectToGetMessages(String conv_id, Presenter presenter){
        DbConversations.getMessages(conv_id, presenter);
    }
    public static void connectToGetLastSeenIndex(String uid, String convid, Presenter pres){
        DbConversations.getLastSeenIndex(uid, convid, pres);
    }
    public static void connectToSendListOfMessages(List<Message> list, String conv){
        DbConversations.sendListOfMessages(list, conv);
    }
    public static void connectToCheckNewMessages(String uid, Presenter pres){
        DbConversations.checkNewMessages(uid, pres);
    }
    public static void connectToGetConversationsIds(String uid, Presenter pres){
        DbConversations.getConversationsIds(uid, pres);
    }

    public static void connectToEditMsg(String id, String msg_id, String msg_cont) {
        DbConversations.editMsg(id, msg_id, msg_cont);
    }

    public static void connectToCreateConversation(Conversation cv, Presenter pres) {
        DbConversations.createConversation(cv,pres);
    }

    public static void sendMessage(String id, Message m) {
        DbConversations.sendMessage(id,m);
    }

    public static void connectToTrackWhosTyping(String id, ConversationPresenter conversationPresenter) {
        DbConversations.trackWhosTyping(id, conversationPresenter);
    }
    public static void connectToGetUserByUid(String uid, Presenter pres, boolean isSingleEvent){
        DbBasic.getUserByUid(uid,pres, isSingleEvent);
    }
    public static void connectToSendTypingSignal(String uid, String convid, boolean signal){
        DbConversations.sendTypingSignal(uid, convid, signal);
    }

    public static void connectToSetAvailabilityStatus(String uid, int status) {
        DbBasic.setAvailabilityStatus(uid, status);
    }
    public static void connectToGetNewMessage(String convid, Presenter pres){
        DbConversations.getNewMessages(convid ,pres);
    }
}
