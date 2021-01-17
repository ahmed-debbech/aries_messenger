package com.ahmeddebbech.aries_messenger.database;

import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.LoginPresenter;
import com.ahmeddebbech.aries_messenger.presenter.Presenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;

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
    public static void connectToSearchForUsersWithName(String text, Presenter pres){DbBasic.searchAllUsersFromName(text, pres);}
    public static void connectToGetConnections(String uid, Presenter pres){
        DbBasic.getConnections(uid, pres);
    }
    public static void connectToRemoveContact(String uid, int delUid, Presenter pres){
        DbBasic.removeContact(uid, delUid, pres);
    }
}
