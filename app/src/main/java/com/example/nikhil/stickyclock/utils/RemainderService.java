package com.example.nikhil.stickyclock.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.example.nikhil.stickyclock.R;
import com.example.nikhil.stickyclock.model.TimeZoneItem;
import com.example.nikhil.stickyclock.ui.WelcomeActivity;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

/**
 * Created by nikhilkumar_s on 6/1/2016.
 */
public class RemainderService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null)
            return START_STICKY;


        Calendar c = Calendar.getInstance();
        String message;

        ArrayList<TimeZoneItem> countriesList = DataStore.getInstance(getApplicationContext()).getTimeZones();

        for (int i = countriesList.size() - 1; i >= 0; i--) {

            TimeZoneItem item = countriesList.get(i);
            TimeZone tz = TimeZone.getTimeZone(item.getTimezoneID());
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            sdf.setTimeZone(tz);
            String currentDateTimeString = sdf.format(c.getTime());
            Intent mainActivityIntent = new Intent(getApplicationContext(), WelcomeActivity.class);
            message = item.getMessage();
            if (message != null && StringUtils.isNotEmpty(message))
                showNotificationMessage(message + "(" + item.getCity() + ")", currentDateTimeString, mainActivityIntent, i, Utils.getCountryFlag(countriesList.get(i).getCountryID()));
            else
                showNotificationMessage("Time in " + item.getCity() + " right now",
                        currentDateTimeString, mainActivityIntent, i, Utils.getCountryFlag(countriesList.get(i).getCountryID()));
        }


        return START_STICKY;
    }

    public void showNotificationMessage(String title, String message, Intent intent, int notificationID, int icon) {

        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return;

        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());

        if (DataStore.getInstance(getApplicationContext()).isIconNeeded()) {

            Notification notificationForLockScreen = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), icon))
                    .setContentText(message)
                    .setPriority(Notification.PRIORITY_MAX)
                    .build();

            Notification notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), icon))
                    .setContentText(message)
                    .setPublicVersion(notificationForLockScreen)
                    .setPriority(Notification.PRIORITY_MAX)
                    .build();

            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID, notification);

        } else {
            Notification notificationForLockScreen = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), icon))
                    .setContentText(message)
                    .setPriority(Notification.PRIORITY_MAX)
                    .build();

            Notification notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), icon))
                    .setContentText(message)
                    .setPublicVersion(notificationForLockScreen)
                    .setPriority(Notification.PRIORITY_MIN)
                    .build();

            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID, notification);

        }

    }

}
