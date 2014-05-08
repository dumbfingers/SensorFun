package com.yeyaxi.android.sensorfun;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class BaseActivity extends SherlockFragmentActivity{

    public static final String ACTION_RECORD = "com.yeyaxi.android.sensorfun.action_record";
    public static final String ACTION_STOP = "com.yeyaxi.android.sensorfun.action_stop";
    public static final String ACTION_WAKE = "com.yeyaxi.android.sensorfun.action_wake";
    public static final String MSG_SENSOR_DATA = "com.yeyaxi.android.sensorfun.msg_sensor_data";

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
}
