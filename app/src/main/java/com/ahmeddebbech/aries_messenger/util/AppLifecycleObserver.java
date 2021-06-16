package com.ahmeddebbech.aries_messenger.util;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;

public class AppLifecycleObserver implements LifecycleObserver {
    public static final String TAG = AppLifecycleObserver.class.getName();

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onEnterForeground() {
        //run the code we need
        Log.d("$ddd", "to fore");
        UserManager.getInstance().setAvailabilityStatus(User.ONLINE);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onEnterBackground() {
        //run the code we need
        Log.d("$ddd", "to back");
        UserManager.getInstance().setAvailabilityStatus(User.NOT_ONLINE);
    }
}
