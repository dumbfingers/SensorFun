package com.yeyaxi.android.sensorfun;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.yeyaxi.android.sensorfun.util.TabsPagerAdapter;

public class MainActivity extends BaseActivity implements
        ActionBar.TabListener,
        SensorFragment.OnSensorFragmentInteractionListener,
        RecordListFragment.OnRecordListFragmentInteractionListener {

	private static final String TAG = MainActivity.class.getSimpleName();

    private TabHost tabHost;
    private ViewPager viewPager;
    private TabsPagerAdapter tabsPagerAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(tabsPagerAdapter);

        // set action bar navigation mode as tabs
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        getSupportActionBar()
                .addTab(getSupportActionBar()
                        .newTab()
                        .setText("Available Sensors")
                        .setTabListener(this));
        getSupportActionBar()
                .addTab(getSupportActionBar()
                        .newTab()
                        .setText("Record List")
                        .setTabListener(this));

	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//		// Pass the event to ActionBarDrawerToggle, if it returns
//		// true, then it has handled the app icon touch event
//        if (item.getItemId() == android.R.id.home) {
//
//            if (mDrawerLayout.isDrawerOpen(mSwitchView)) {
//                mDrawerLayout.closeDrawer(mSwitchView);
//            } else {
//                mDrawerLayout.openDrawer(mSwitchView);
//            }
//        }
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}
	

	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		// onPause invoked, set flag so that we can use alarm manager
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	

	
	private void scheduleAlarm() {
		AlarmManager scheduler = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(this, SensorService.class );
		intent.putExtra("Background", true);
		intent.putExtra("Record", true);
		PendingIntent scheduledIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		//TODO Alarm to be fired up in 1-minute's interval
		scheduler.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5 * 1000, scheduledIntent);
	}
	
	private void cancelAlarm() {
		AlarmManager scheduler = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(this,SensorService.class );
		PendingIntent scheduledIntent = PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		scheduler.cancel(scheduledIntent);
	}
	
	private void startPlotActivity(String sensorType) {
		Intent i = new Intent(this, PlotActivity.class);
		i.putExtra("sensorType", sensorType);
		startActivity(i);
	}
	
//	private ServiceConnection mConnection = new ServiceConnection() {
//
//		@Override
//		public void onServiceDisconnected(ComponentName name) {
//			isBind = false;
//		}
//
//		@Override
//		public void onServiceConnected(ComponentName name, IBinder service) {
//			mBoundService = ((SensorService.SensorBinder) service).getService();
////			mBoundService.getMsg();
//			mSensorManager = mBoundService.getSensorManager();
//			isBind = true;
////			detectSensors();
//		}
//	};
	
//	private void doBindService() {
//		bindService(new Intent(this, SensorService.class), mConnection, Context.BIND_AUTO_CREATE);
//	}
//
//	private void doUnbindService() {
//		unbindService(mConnection);
//	}


    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
