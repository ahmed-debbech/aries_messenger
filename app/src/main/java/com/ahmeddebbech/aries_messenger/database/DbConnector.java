package com.ahmeddebbech.aries_messenger.database;

import com.ahmeddebbech.aries_messenger.views.activities.LoginActivity;
import com.ahmeddebbech.aries_messenger.model.User;
import com.google.firebase.auth.FirebaseUser;

public class DbConnector {


    public static void connectToSignIn(FirebaseUser user, LoginActivity loginActivity) {
        DbUtil.userExists(user,loginActivity);
    }
    public static void connectToRegister(User user){
        DbBasic.addUserToDatabase(user);
    }
}
