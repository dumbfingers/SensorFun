package com.yeyaxi.android.sensorfun;

import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.yeyaxi.android.sensorfun.util.TabsPagerAdapter;

public class MainActivity extends BaseActivity implements
        ActionBar.TabListener,
        SensorListFragment.OnSensorFragmentInteractionListener,
        RecordListFragment.OnRecordListFragmentInteractionListener,
        RecordOptionDialogFragment.OnRecordOptionDialogFragmentInteractionListener {

	private static final String TAG = MainActivity.class.getSimpleName();

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

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // When swiping between pages, select the corresponding tab
                getSupportActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
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

        switch (item.getItemId()) {
            case R.id.action_record_all:
                SherlockDialogFragment dialog = new RecordOptionDialogFragment("All Sensors");
                dialog.show(getSupportFragmentManager(), "RecordOptionDialogFragment");
                return true;
            default:
                return false;
        }
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
        AlarmScheduler.scheduleAlarm(this, 5);
        sendBroadcast(new Intent(BaseActivity.ACTION_RECORD_ALL));
	}
	
	@Override
	protected void onDestroy() {
        // cancel any alarms
//        AlarmScheduler.cancelAlarm(this);
		super.onDestroy();
	}


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

    @Override
    public void onRecordListFragmentInteraction() {

    }

    @Override
    public void onDialogFragmentInteraction(boolean startRecord) {
        if (startRecord == true) {
            // start the service
            Intent intent = new Intent(this, SensorService.class);
            intent.putExtra("sensorType", Sensor.TYPE_ALL);
            startService(intent);
        }
    }
}
