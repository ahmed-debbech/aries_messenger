package com.ahmeddebbech.aries_messenger.database;

import com.ahmeddebbech.aries_messenger.model.Message;
import com.ahmeddebbech.aries_messenger.model.User;
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

    public static void connectToCheckIfConversationExists(String uid, Presenter pres){
        DbUtil.checkConvExists(uid, pres);
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
}
