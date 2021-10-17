package com.ahmeddebbech.aries_messenger.threads;

import android.util.Log;

import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.Presenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.google.firebase.auth.FirebaseAuth;

public class InitialDataRetrieverThread implements Runnable{

    private Presenter calling_presenter;
    private String user_uid;

    public InitialDataRetrieverThread(String uid, Presenter pres){
        this.user_uid = uid;
        this.calling_presenter = pres;
    }
    @Override
    public void run() {
        DbConnector.connectToGetUserData(user_uid, calling_presenter);
        DbConnector.connectToGetConnections(user_uid, calling_presenter);
        DbConnector.connectToGetConversationsIds(user_uid, calling_presenter);
        DbConnector.connectToGetBlockedUsers(user_uid, calling_presenter);
        UserManager.getInstance().getAccessTokenFromDb();
    }
}
