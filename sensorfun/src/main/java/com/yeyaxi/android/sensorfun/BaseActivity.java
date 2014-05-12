package com.yeyaxi.android.sensorfun;

import android.app.ActivityManager;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class BaseActivity extends SherlockFragmentActivity{

    public static final String ACTION_RECORD = "com.yeyaxi.android.sensorfun.ACTION_RECORD";
    public static final String ACTION_RECORD_ALL = "com.yeyaxi.android.sensorfun.ACTION_RECORD_ALL";
    public static final String ACTION_STOP = "com.yeyaxi.android.sensorfun.ACTION_STOP";
    public static final String ACTION_WAKE = "com.yeyaxi.android.sensorfun.ACTION_WAKE";
    public static final String MSG_SENSOR_DATA = "com.yeyaxi.android.sensorfun.MSG_SENSOR";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setBehindContentView(R.layout.slide_menu);
//
//		SlidingMenu sm = getSlidingMenu();
//		sm.setShadowWidthRes(R.dimen.shadow_width);
//		sm.setShadowDrawable(R.drawable.shadow);
//		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//		sm.setFadeDegree(0.35f);
//		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

    public static boolean isRecordServiceRunning(ActivityManager manager) {
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SensorService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
