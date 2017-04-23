package com.androidworks.nikhil.stickyclock.utils;

/**
 * Created by Nikhil on 06-Oct-16.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataStore.getInstance(getApplicationContext());
        Utils.loadMap();
    }
}
