package com.yeyaxi.android.sensorfun;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	
	private SensorManager mSensorManager;
	private List<Sensor> deviceSensors;
	private static final String TAG = MainActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		detectSensors();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	@Override
	protected void onStart() {
		super.onStart();
		
		
	}
	
	
	private void detectSensors() {		
		deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		
		Log.d(TAG, "" + deviceSensors);
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
			Log.i(TAG, "Accelerometer");
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null)
			Log.i(TAG, "Ambient Temperature");
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null)
			Log.i(TAG, "Gravity");
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null)
			Log.i(TAG, "Gyroscope");
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null)
			Log.i(TAG, "Light");
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null)
			Log.i(TAG, "Linear Acceleration");
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null)
			Log.i(TAG, "Magnetic Field");
//		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null)
//			Log.i(TAG, "Orientation");
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null)
			Log.i(TAG, "Pressure");
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null)
			Log.i(TAG, "Proximity");
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null)
			Log.i(TAG, "Relative Humidity");
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null)
			Log.i(TAG, "Rotation Vector");
	}
}
