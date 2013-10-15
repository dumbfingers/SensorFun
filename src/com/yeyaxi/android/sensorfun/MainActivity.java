package com.yeyaxi.android.sensorfun;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
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
	
	private SensorService mBoundService;
	private boolean isBind = false;
	
	private BroadcastReceiver mReceiver;
	
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
		
		mReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {

				if (intent.getFloatArrayExtra("accelerometer") != null) {
					float[] accFloats = intent.getFloatArrayExtra("accelerometer");
					accelerometerTextView.setText("Accelerometer: " + 
							accFloats[0] + 
							" " + accFloats[1] + 
							" " + accFloats[2]);
				}
				if (intent.getFloatArrayExtra("magnetic_field") != null) {
					float[] magFloats = intent.getFloatArrayExtra("magnetic_field");
					magneticTextView.setText("Magnetic Field: " + 
								magFloats[0] +
							" " + magFloats[1] + 
							" " + magFloats[2]);
				}
				if (intent.getFloatArrayExtra("gyroscope") != null) {
					float[] gyroFloats = intent.getFloatArrayExtra("gyroscope");
					gyroTextView.setText("Gyroscope: " + 
							gyroFloats[0] +
							" " + gyroFloats[1] + 
							" " + gyroFloats[2]);
				}
				if (intent.getFloatArrayExtra("light") != null) {
					float[] lightFloats = intent.getFloatArrayExtra("light");
					lightTextView.setText("Light: " + 
							lightFloats[0]);
				}
				if (intent.getFloatArrayExtra("pressure") != null) {
					float[] pressureFloats = intent.getFloatArrayExtra("pressure");
					pressureTextView.setText("Pressure: " + 
							pressureFloats[0]);
				}
				if (intent.getFloatArrayExtra("proximity") != null) {
					float[] proxiFloats = intent.getFloatArrayExtra("proximity");
					proxiTextView.setText("Proximity: " + 
							proxiFloats[0]);
				}
				if (intent.getFloatArrayExtra("gravity") != null) {
					float[] gravityFloats = intent.getFloatArrayExtra("gravity");
					gravityTextView.setText("Gravity: " + 
							gravityFloats[0] +
							" " + gravityFloats[1] + 
							" " + gravityFloats[2]);
				}
				if (intent.getFloatArrayExtra("linear_acceleration") != null) {
					float[] linearAccFloats = intent.getFloatArrayExtra("linear_acceleration");
					linearAccTextView.setText("Linear Acceleration: " + 
							linearAccFloats[0] +
							" " + linearAccFloats[1] + 
							" " + linearAccFloats[2]);
				}
				if (intent.getFloatArrayExtra("rotation_vector") != null) {
					float[] rotVecFloats = intent.getFloatArrayExtra("rotation_vector");
					rotVecTextView.setText("Rotation Vector: " + 
							rotVecFloats[0] +
							" " + rotVecFloats[1] + 
							" " + rotVecFloats[2]);
				}
				if (intent.getFloatArrayExtra("relative_humidity") != null) {
					float[] relatHumidFloats = intent.getFloatArrayExtra("relative_humidity");
					relatHumidTextView.setText("Relative Humidity: " + 
							relatHumidFloats[0]);
				}
				if (intent.getFloatArrayExtra("ambient_temperature") != null) {
					float[] ambTempFloats = intent.getFloatArrayExtra("ambient_temperature");
					ambTempTextView.setText("Ambient Temperature: " + 
							ambTempFloats[0]);
				}
			}
		};
		
		// bind the service
		doBindService();

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
	
	@Override
	protected void onResume() {
		super.onResume();
		LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("SensorData"));
	}
	
	@Override
	protected void onPause() {
	  super.onPause();
	  LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
	}
	
	@Override
	protected void onDestroy() {
		doUnbindService();
		super.onDestroy();
	}
	
	
	private ServiceConnection mConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			isBind = false;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
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
		
		Log.d(TAG, "" + deviceSensors);
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
//			Log.i(TAG, "Accelerometer");
			accelerometerTextView.setVisibility(View.VISIBLE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
//			Log.i(TAG, "Ambient Temperature");
			ambTempTextView.setVisibility(View.VISIBLE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null) {
//			Log.i(TAG, "Gravity");
			gravityTextView.setVisibility(View.VISIBLE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
//			Log.i(TAG, "Gyroscope");
			gyroTextView.setVisibility(View.VISIBLE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
//			Log.i(TAG, "Light");
			lightTextView.setVisibility(View.VISIBLE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
//			Log.i(TAG, "Linear Acceleration");
			linearAccTextView.setVisibility(View.VISIBLE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
//			Log.i(TAG, "Magnetic Field");
			magneticTextView.setVisibility(View.VISIBLE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
//		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null)
//			Log.i(TAG, "Orientation");
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
//			Log.i(TAG, "Pressure");
			pressureTextView.setVisibility(View.VISIBLE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
//			Log.i(TAG, "Proximity");
			proxiTextView.setVisibility(View.VISIBLE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
//			Log.i(TAG, "Relative Humidity");
			relatHumidTextView.setVisibility(View.VISIBLE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null) {
//			Log.i(TAG, "Rotation Vector");
			rotVecTextView.setVisibility(View.VISIBLE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);

		}
	}
}
