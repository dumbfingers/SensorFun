package com.yeyaxi.android.sensorfun;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by yaxi on 02/05/2014.
 */
public class AlarmScheduler {

    public static void scheduleAlarm(Context context) {
        AlarmManager scheduler = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SensorService.class );
        intent.putExtra("Background", true);
        intent.putExtra("Record", true);
        PendingIntent scheduledIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //TODO Alarm to be fired up in 1-minute's interval
        scheduler.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5 * 1000, scheduledIntent);
    }

    public void cancelAlarm(Context context) {
        AlarmManager scheduler = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SensorService.class);
        PendingIntent scheduledIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        scheduler.cancel(scheduledIntent);
    }
}
