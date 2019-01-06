package com.yayandroid.locationmanager.sample;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.yayandroid.locationmanager.LocationManager;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LocationManager.enableLog(true);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
