package com.androidworks.nikhil.stickyclock.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by nikhilkumar_s on 6/1/2016.
 */
public class RemainderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, RemainderService.class);
        context.startService(service);
    }
}
