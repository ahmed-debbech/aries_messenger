package com.ahmeddebbech.aries_messenger.util;

import android.app.Application;

import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.firebase.database.FirebaseDatabase;

public class CoreApplication extends Application {

    private static final String TAG = CoreApplication.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();

        AppLifecycleObserver appLifecycleObserver = new AppLifecycleObserver();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(appLifecycleObserver);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
