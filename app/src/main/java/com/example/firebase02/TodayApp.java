package com.example.firebase02;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by Daniel Alvarez on 29/6/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class TodayApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}