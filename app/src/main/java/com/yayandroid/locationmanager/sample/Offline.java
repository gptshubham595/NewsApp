package com.yayandroid.locationmanager.sample;

import android.app.Application;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class Offline extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
}

}