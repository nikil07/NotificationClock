package com.example.nikhil.stickyclock.utils;

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
       // Log.d("nikhil",intent.getExtras().getString("test"));
       // if(intent.getStringExtra(Constants.KEY)!=null)
        //service.putExtra(Constants.KEY,intent.getStringExtra(Constants.KEY));
        context.startService(service);
    }
}
