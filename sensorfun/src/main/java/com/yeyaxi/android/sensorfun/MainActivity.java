package com.yeyaxi.android.sensorfun;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.actionbarsherlock.view.MenuItem;
import com.yeyaxi.android.sensorfun.util.SensorDataUtility;

import org.jraf.android.backport.switchwidget.Switch;

import java.util.List;

public class MainActivity extends BaseActivity {

	
	private SensorManager mSensorManager;
	private List<Sensor> deviceSensors;
	private static final String TAG = MainActivity.class.getSimpleName();
	


	private ToggleButton recordToggle;

    // For menu
//	private TableRow gpsMenuRow;
    private TableRow accelMenuRow;
    private TableRow gyroMenuRow;
    private TableRow gravityMenuRow;
    private TableRow linAccMenuRow;
    private TableRow magMenuRow;
    private TableRow rotVecMenuRow;
    private TableRow tempMenuRow;
    private TableRow lightMenuRow;
    private TableRow pressureMenuRow;
    private TableRow proxiMenuRow;
    private TableRow relaHumidMenuRow;
	// Menu's Switch
	private Switch accSwitch;
	private Switch gyroSwitch;
	private Switch gravitySwitch;
	private Switch linAccSwitch;
	private Switch magSwitch;
	private Switch rotVecSwitch;
	private Switch tempSwitch;
	private Switch lightSwitch;
	private Switch pressureSwitch;
	private Switch proxiSwitch;
	private Switch relaHumSwitch;
	
	private SensorService mBoundService;
	private boolean isBind = false;
	
	private BroadcastReceiver mReceiver;
	
	private DrawerLayout mDrawerLayout;
//	private SlidingMenu menu;
	private View mSwitchView;
	private View mainView;
	
	private ActionBarDrawerToggle mDrawerToggle;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		setContentView(R.layout.activity_main);
		setContentView(R.layout.drawer_main);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mainView = (View) findViewById(R.id.content_frame);
		

			
		// Do something for the sliding menu
//		menu = getSlidingMenu();
//		setSlidingActionBarEnabled(false);
		mSwitchView = (View) findViewById(R.id.left_drawer);

		accelMenuRow = (TableRow) mSwitchView.findViewById(R.id.menuRowAccel);
		gyroMenuRow = (TableRow) mSwitchView.findViewById(R.id.menuRowGyro);
		gravityMenuRow = (TableRow) mSwitchView.findViewById(R.id.menuRowGravity);
		linAccMenuRow = (TableRow) mSwitchView.findViewById(R.id.menuRowLinearAcc);
		magMenuRow = (TableRow) mSwitchView.findViewById(R.id.menuRowMagField);
		rotVecMenuRow = (TableRow) mSwitchView.findViewById(R.id.menuRowRotVec);
		tempMenuRow = (TableRow) mSwitchView.findViewById(R.id.menuRowAmbientTemp);
		lightMenuRow = (TableRow) mSwitchView.findViewById(R.id.menuRowLight);
		pressureMenuRow = (TableRow) mSwitchView.findViewById(R.id.menuRowPressure);
		proxiMenuRow = (TableRow) mSwitchView.findViewById(R.id.menuRowProximity);
		relaHumidMenuRow = (TableRow) mSwitchView.findViewById(R.id.menuRowRelaHumid);

		accSwitch = (Switch) mSwitchView.findViewById(R.id.accSwitch);
		gyroSwitch = (Switch) mSwitchView.findViewById(R.id.gyroSwitch);
		gravitySwitch = (Switch) mSwitchView.findViewById(R.id.gravitySwitch);
		linAccSwitch = (Switch) mSwitchView.findViewById(R.id.lineAccSwitch);
		magSwitch = (Switch) mSwitchView.findViewById(R.id.magFieldSwitch);
		rotVecSwitch = (Switch) mSwitchView.findViewById(R.id.rotVecSwitch);
		tempSwitch = (Switch) mSwitchView.findViewById(R.id.ambTempSwitch);
		lightSwitch = (Switch) mSwitchView.findViewById(R.id.lightSwitch);
		pressureSwitch = (Switch) mSwitchView.findViewById(R.id.pressureSwitch);
		proxiSwitch = (Switch) mSwitchView.findViewById(R.id.proxSwitch);
		relaHumSwitch = (Switch) mSwitchView.findViewById(R.id.relaHumidSwitch);
		
		accSwitch.setChecked(true);
		gyroSwitch.setChecked(true);
		gravitySwitch.setChecked(true);
		linAccSwitch.setChecked(true);
		magSwitch.setChecked(true);
		rotVecSwitch.setChecked(true);
		tempSwitch.setChecked(true);
		lightSwitch.setChecked(true);
		pressureSwitch.setChecked(true);
		proxiSwitch.setChecked(true);
		relaHumSwitch.setChecked(true);
		
		recordToggle.setOnCheckedChangeListener(checkedChangeListener);
		
		accSwitch.setOnCheckedChangeListener(checkedChangeListener);
		gyroSwitch.setOnCheckedChangeListener(checkedChangeListener);
		gravitySwitch.setOnCheckedChangeListener(checkedChangeListener);
		linAccSwitch.setOnCheckedChangeListener(checkedChangeListener);
		magSwitch.setOnCheckedChangeListener(checkedChangeListener);
		rotVecSwitch.setOnCheckedChangeListener(checkedChangeListener);
		tempSwitch.setOnCheckedChangeListener(checkedChangeListener);
		lightSwitch.setOnCheckedChangeListener(checkedChangeListener);
		pressureSwitch.setOnCheckedChangeListener(checkedChangeListener);
		proxiSwitch.setOnCheckedChangeListener(checkedChangeListener);
		relaHumSwitch.setOnCheckedChangeListener(checkedChangeListener);
		
		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				// TODO Auto-generated method stub
				getSupportActionBar().setTitle(getTitle());
				super.onDrawerClosed(view);
			}

			public void onDrawerOpened(View drawerView) {
				// Set the title on the action when drawer open
				getSupportActionBar().setTitle("Options");
				super.onDrawerOpened(drawerView);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
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
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
        if (item.getItemId() == android.R.id.home) {
        	 
            if (mDrawerLayout.isDrawerOpen(mSwitchView)) {
                mDrawerLayout.closeDrawer(mSwitchView);
            } else {
                mDrawerLayout.openDrawer(mSwitchView);
            }
        }
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}
	
	OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (buttonView.getId() == R.id.toggleRecord) {
				
				mBoundService.toggleRecord(isChecked);
				
			} else {
				
				mBoundService.singleToggle(buttonView.getId(), isChecked);
				
				switch (buttonView.getId()) {
				case R.id.accSwitch:
					accelRow.setEnabled(isChecked);
					break;
				case R.id.gyroSwitch:
					gyroRow.setEnabled(isChecked);
					break;
				case R.id.gravitySwitch:
					gravityRow.setEnabled(isChecked);
					break;
				case R.id.lineAccSwitch:
					linAccRow.setEnabled(isChecked);
					break;
				case R.id.magFieldSwitch:
					magRow.setEnabled(isChecked);
					break;
				case R.id.rotVecSwitch:
					rotVecRow.setEnabled(isChecked);
					break;
				case R.id.ambTempSwitch:
					tempRow.setEnabled(isChecked);
					break;
				case R.id.lightSwitch:
					lightRow.setEnabled(isChecked);
					break;
				case R.id.pressureSwitch:
					pressureRow.setEnabled(isChecked);
					break;
				case R.id.proxSwitch:
					proxiRow.setEnabled(isChecked);
					break;
				case R.id.relaHumidSwitch:
					relaHumidRow.setEnabled(isChecked);
					break;
				}
			}
		}
	};
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// Cancel the alarm schedule
		cancelAlarm();
		// bind the service
		doBindService();
		// Register the receiver
		LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("SensorData"));

	}
	
	@Override
	protected void onPause() {
		super.onPause();
		// onPause invoked, set flag so that we can use alarm manager
		// If record toggle is ON means we need to fire up the alarm manager to schedule the recordings
		if (recordToggle.isChecked() == true) {
			
			mBoundService.setBackground(true);
			
			scheduleAlarm();
		}
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
	}
	
	@Override
	protected void onDestroy() {
		doUnbindService();
		super.onDestroy();
	}
	
	OnClickListener rowClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			
			case R.id.tableRowGPS:
				
				break;
				
			case R.id.tableRowAccel:
				startPlotActivity("accelerometer");
				break;

			case R.id.tableRowGyro:
				startPlotActivity("gyroscope");
				break;
				
			case R.id.tableRowGravity:
				startPlotActivity("gravity");
				break;
				
			case R.id.tableRowLinearAcc:
				startPlotActivity("linear_acceleration");
				break;
				
			case R.id.tableRowMagField:
				startPlotActivity("magnetic_field");
				break;
				
			case R.id.tableRowRotVec:
				startPlotActivity("rotation_vector");
				break;
				
			case R.id.tableRowAmbientTemp:
				startPlotActivity("ambient_temperature");
				break;
				
			case R.id.tableRowLight:
				startPlotActivity("light");
				break;
				
			case R.id.tableRowPressure:
				startPlotActivity("pressure");
				break;
				
			case R.id.tableRowProximity:
				startPlotActivity("proximity");
				break;
				
			case R.id.tableRowRelaHumid:
				startPlotActivity("relative_humidity");
				break;
				
			}
		}
	};
	
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
	
	private ServiceConnection mConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			isBind = false;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mBoundService = ((SensorService.SensorBinder) service).getService();
//			mBoundService.getMsg();
			mSensorManager = mBoundService.getSensorManager();
			isBind = true;
			detectSensors();
		}
	};
	
	private void doBindService() {
		bindService(new Intent(this, SensorService.class), mConnection, Context.BIND_AUTO_CREATE);
	}
	
	private void doUnbindService() {
		unbindService(mConnection);
	}
	
	private void detectSensors() {		
		deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		
//		Log.d(TAG, "" + deviceSensors);
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) {
//			Log.i(TAG, "Accelerometer");
			accelRow.setVisibility(View.GONE);
			accelMenuRow.setVisibility(View.GONE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) == null) {
//			Log.i(TAG, "Ambient Temperature");
			tempRow.setVisibility(View.GONE);
			tempMenuRow.setVisibility(View.GONE);

//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) == null) {
//			Log.i(TAG, "Gravity");
			gravityRow.setVisibility(View.GONE);
			gravityMenuRow.setVisibility(View.GONE);

//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null) {
//			Log.i(TAG, "Gyroscope");
			gyroRow.setVisibility(View.GONE);
			gyroMenuRow.setVisibility(View.GONE);

//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) == null) {
//			Log.i(TAG, "Light");
			lightRow.setVisibility(View.GONE);
			lightMenuRow.setVisibility(View.GONE);
			
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) == null) {
//			Log.i(TAG, "Linear Acceleration");
			linAccRow.setVisibility(View.GONE);
			linAccMenuRow.setVisibility(View.GONE);

//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) == null) {
//			Log.i(TAG, "Magnetic Field");
			magRow.setVisibility(View.GONE);
			magMenuRow.setVisibility(View.GONE);
			
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
//		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) == null)
//			Log.i(TAG, "Orientation");
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) == null) {
//			Log.i(TAG, "Pressure");
			pressureRow.setVisibility(View.GONE);
			pressureMenuRow.setVisibility(View.GONE);

//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null) {
//			Log.i(TAG, "Proximity");
			proxiRow.setVisibility(View.GONE);
			proxiMenuRow.setVisibility(View.GONE);

//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) == null) {
//			Log.i(TAG, "Relative Humidity");
			relaHumidRow.setVisibility(View.GONE);
			relaHumidMenuRow.setVisibility(View.GONE);

//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) == null) {
//			Log.i(TAG, "Rotation Vector");
			rotVecRow.setVisibility(View.GONE);
			rotVecMenuRow.setVisibility(View.GONE);

//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);

		}
	}
}
