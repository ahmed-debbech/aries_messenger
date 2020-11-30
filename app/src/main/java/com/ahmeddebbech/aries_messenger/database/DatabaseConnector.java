package com.ahmeddebbech.aries_messenger.database;

import com.ahmeddebbech.aries_messenger.activities.LoginActivity;
import com.ahmeddebbech.aries_messenger.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DatabaseConnector {


    public static void connectToSignIn(FirebaseUser user, LoginActivity loginActivity) {
        UtilDB.userExists(user,loginActivity);
    }
    public static void connectToRegister(User user){
        Database.addUserToDatabase(user);
    }
}
