package com.ahmeddebbech.aries_messenger.database;

import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.LoginPresenter;

public class DbConnector {


    public static void connectToSignIn(LoginPresenter log) {
        DbUtil.userExistsInSignIn(log);
    }
    public static void connectToRegister(User user){
        DbBasic.addUserToDatabase(user);
    }
}
