package com.yeyaxi.android.sensorfun;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	
	private SensorManager mSensorManager;
	private List<Sensor> deviceSensors;
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private TextView accelerometerTextView;
	private TextView ambTempTextView;
	private TextView gyroTextView;
	private TextView gravityTextView;
	private TextView lightTextView;
	private TextView magneticTextView;
	private TextView linearAccTextView;
	private TextView pressureTextView;
	private TextView proxiTextView;
	private TextView relatHumidTextView;
	private TextView rotVecTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		accelerometerTextView = (TextView) findViewById(R.id.textAccelerometer);
		ambTempTextView = (TextView) findViewById(R.id.textAmbientTemp);
		gyroTextView = (TextView) findViewById(R.id.textGyroscope);
		gravityTextView = (TextView) findViewById(R.id.textGravity);
		lightTextView = (TextView) findViewById(R.id.textLight);
		magneticTextView = (TextView) findViewById(R.id.textMagField);
		linearAccTextView = (TextView) findViewById(R.id.textLinearAcc);
		pressureTextView = (TextView) findViewById(R.id.textPressure);
		proxiTextView = (TextView) findViewById(R.id.textProximity);
		relatHumidTextView = (TextView) findViewById(R.id.textRelaHumid);
		rotVecTextView = (TextView) findViewById(R.id.textRotVect);
		
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
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
//			Log.i(TAG, "Accelerometer");
			accelerometerTextView.setVisibility(View.VISIBLE);
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
//			Log.i(TAG, "Ambient Temperature");
			ambTempTextView.setVisibility(View.VISIBLE);
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null) {
//			Log.i(TAG, "Gravity");
			gravityTextView.setVisibility(View.VISIBLE);
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
//			Log.i(TAG, "Gyroscope");
			gyroTextView.setVisibility(View.VISIBLE);
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
//			Log.i(TAG, "Light");
			lightTextView.setVisibility(View.VISIBLE);
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
//			Log.i(TAG, "Linear Acceleration");
			linearAccTextView.setVisibility(View.VISIBLE);
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
//			Log.i(TAG, "Magnetic Field");
			magneticTextView.setVisibility(View.VISIBLE);
		}
		
//		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null)
//			Log.i(TAG, "Orientation");
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
//			Log.i(TAG, "Pressure");
			pressureTextView.setVisibility(View.VISIBLE);
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
//			Log.i(TAG, "Proximity");
			proxiTextView.setVisibility(View.VISIBLE);
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
//			Log.i(TAG, "Relative Humidity");
			relatHumidTextView.setVisibility(View.VISIBLE);
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null) {
//			Log.i(TAG, "Rotation Vector");
			rotVecTextView.setVisibility(View.VISIBLE);
		}
	}
}
