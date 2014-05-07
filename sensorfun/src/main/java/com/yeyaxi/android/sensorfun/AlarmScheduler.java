package com.yeyaxi.android.sensorfun;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by yaxi on 02/05/2014.
 */
public class AlarmScheduler {

    private static final String TAG = AlarmScheduler.class.getSimpleName();
    private static final int SECOND_TO_MILLISECOND = 1000;

    public static void scheduleAlarm(Context context, int intervalInSeconds) {
        AlarmManager scheduler = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, SensorService.class);
//        intent.putExtra("Background", true);
//        intent.putExtra("Record", true);
//        intent.putExtra("alarm", true);
//        PendingIntent scheduledIntent = PendingIntent.getService(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intent = new Intent("alarm");
        PendingIntent scheduledIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //TODO Alarm to be fired up in 1-minute's interval
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        // cancel any previous alarm
        scheduler.cancel(scheduledIntent);
        scheduler.setInexactRepeating(
                AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), intervalInSeconds * SECOND_TO_MILLISECOND, scheduledIntent);
        Log.d(TAG, "Alarm set to be fired in every " + intervalInSeconds + " seconds.");
    }

    public static void cancelAlarm(Context context) {
        AlarmManager scheduler = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SensorService.class);
        PendingIntent scheduledIntent = PendingIntent.getService(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        scheduler.cancel(scheduledIntent);
        Log.d(TAG, "Alarm cancelled.");
    }
}
