package com.ahmeddebbech.aries_messenger.util;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.ahmeddebbech.aries_messenger.database.DbConnector;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.MessengerManager;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.threads.InitialDataRetrieverThread;
import com.google.firebase.auth.FirebaseAuth;

public class AppLifecycleObserver implements LifecycleObserver {
    public static final String TAG = AppLifecycleObserver.class.getName();

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onEnterForeground() {
        Log.d("APP_STATE", "Application is on foreground");
        UserManager.getInstance().setAvailabilityStatus(User.ONLINE);

        DbConnector.connectToGetUserAccessToken(UserManager.getInstance());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onEnterBackground() {
        Log.d("APP_STATE", "Application went to background");
        UserManager.getInstance().setAvailabilityStatus(User.NOT_ONLINE);
    }
}
