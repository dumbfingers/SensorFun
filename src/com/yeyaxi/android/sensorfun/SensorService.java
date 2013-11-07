package com.yeyaxi.android.sensorfun;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.jraf.android.backport.switchwidget.Switch;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import au.com.bytecode.opencsv.CSVWriter;

import com.yeyaxi.android.sensorfun.util.SensorDataUtility;

/**
 * 
 * @author Yaxi Ye
 * @since Oct.11.2013
 */
public class SensorService extends Service implements SensorEventListener{

//	private String msg = "Test Msg";
	
	private SensorManager mSensorManager;
//	private List<Sensor> deviceSensors;
	private static final String TAG = SensorService.class.getSimpleName();
	private LocalBroadcastManager mLocalBroadcastManager;
	
	private float[] accVals = new float[3];
	private float[] tempVal = new float[3];
	private float[] gravVals = new float[3];
	private float[] gyroVals = new float[3];
	private float[] lightVal = new float[3];
	private float[] lineAccVals = new float[3];
	private float[] magVals = new float[3];
	private float[] presVal = new float[3];
	private float[] proxVal = new float[3];
	private float[] rhVal = new float[3];
	// Rotation Vector contains 5 elements
	private float[] rotVals = new float[5];
	// This is the main switch of writing data to storage
	private boolean toggleRecord = false;
	// These are the separate switches
	private boolean accToggle = true;
	private boolean gyroToggle = true;
	private boolean gravityToggle = true;
	private boolean linAccToggle = true;
	private boolean magToggle = true;
	private boolean rotVecToggle = true;
	private boolean tempToggle = true;
	private boolean lightToggle = true;
	private boolean pressureToggle = true;
	private boolean proxiToggle = true;
	private boolean relaHumToggle = true;
	// Background state
	private boolean isBackground = false;
	
	// This is the object that receives interactions from clients.  See
	// RemoteService for a more complete example.
	private final IBinder mBinder = new SensorBinder();
	
	public class SensorBinder extends Binder {
		SensorService getService() {
			return SensorService.this;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		regSensorListeners();

	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			if (bundle.containsKey("Background")) {
				isBackground = true;
			}
			
			if (bundle.containsKey("Record")) {
				toggleRecord = true;
			}
		}
		
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		Log.i(TAG, "Received start id " + startId + ": " + intent);
		return START_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind Service");
		return mBinder;
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "Sensor Service Destroyed.");
		super.onDestroy();
	}
	
	public SensorManager getSensorManager() {
		return mSensorManager;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		Sensor mSensor = event.sensor;
		
		if (mSensor.getType() == Sensor.TYPE_ACCELEROMETER && accToggle) {
			
			accVals = SensorDataUtility.lowPass(event.values, accVals, 0.1f);
			
			sendMessage("accelerometer", accVals);
			recordToFile("accelerometer", accVals);
			
		} else if (mSensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE && tempToggle) {

			// By giving alpha as 1.0f, we're receiving the value without any filter
			tempVal = SensorDataUtility.lowPass(event.values, tempVal, 1.0f);

			sendMessage("ambient_temperature", tempVal);
			
			recordToFile("ambient_temperature", tempVal);

		} else if (mSensor.getType() == Sensor.TYPE_GRAVITY && gravityToggle) {
			
			gravVals = SensorDataUtility.lowPass(event.values, gravVals, 0.6f);
			
			sendMessage("gravity", gravVals);
			recordToFile("gravity", gravVals);
			
		} else if (mSensor.getType() == Sensor.TYPE_GYROSCOPE && gyroToggle) {
			
			gyroVals = SensorDataUtility.lowPass(event.values, gyroVals, 0.6f);
			
			sendMessage("gyroscope", gyroVals);
			recordToFile("gyroscope", gyroVals);
			
		} else if (mSensor.getType() == Sensor.TYPE_LIGHT && lightToggle) {
			
			// By giving alpha as 1.0f, we're receiving the value without any filter
			lightVal = SensorDataUtility.lowPass(event.values, lightVal, 1.0f);
			
			sendMessage("light", lightVal);
			recordToFile("light", lightVal);
			
		} else if (mSensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION && linAccToggle) {
			
			lineAccVals = SensorDataUtility.lowPass(event.values, lineAccVals, 0.6f);
			
			sendMessage("linear_acceleration", lineAccVals);
			recordToFile("linear_acceleration", lineAccVals);
			
		} else if (mSensor.getType() == Sensor.TYPE_MAGNETIC_FIELD && magToggle) {
			
			magVals = SensorDataUtility.lowPass(event.values, magVals, 0.2f);
			
			sendMessage("magnetic_field", magVals);
			recordToFile("magnetic_field", magVals);
			
		} else if (mSensor.getType() == Sensor.TYPE_PRESSURE && pressureToggle) {
			
			presVal = SensorDataUtility.lowPass(event.values, presVal, 0.5f);
			
			sendMessage("pressure", presVal);
			recordToFile("pressure", presVal);
			
		} else if (mSensor.getType() == Sensor.TYPE_PROXIMITY && proxiToggle) {
			
			proxVal = SensorDataUtility.lowPass(event.values, proxVal, 1.0f);
			
			sendMessage("proximity", proxVal);
			recordToFile("proximity", proxVal);
			
		} else if (mSensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY && relaHumToggle) {
			
			rhVal = SensorDataUtility.lowPass(event.values, rhVal, 1.0f);
			
			sendMessage("relative_humidity", rhVal);
			recordToFile("relative_humidity", rhVal);
			
		} else if (mSensor.getType() == Sensor.TYPE_ROTATION_VECTOR && rotVecToggle) {
			rotVals = SensorDataUtility.lowPass(event.values, rotVals, 0.6f);
			sendMessage("rotation_vector", rotVals);
			recordToFile("rotation_vector", rotVals);
		}
		
		// If the record toggle is ON and in background, we'll need to self-kill the service
		if (toggleRecord == true && isBackground == true) {
			mSensorManager.unregisterListener(this);
			Log.d(TAG, "Sensor Service self-killed.");
			stopSelf();
		}
	}

	@SuppressLint("InlinedApi")
	private void regSensorListeners() {
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
//			Log.i(TAG, "Accelerometer");
			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
				//			Log.i(TAG, "Ambient Temperature");
				mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), SensorManager.SENSOR_DELAY_NORMAL);
			}
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null) {
//			Log.i(TAG, "Gravity");
			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
//			Log.i(TAG, "Gyroscope");
			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
//			Log.i(TAG, "Light");
			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
//			Log.i(TAG, "Linear Acceleration");
			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
//			Log.i(TAG, "Magnetic Field");
			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
//		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null)
//			Log.i(TAG, "Orientation");
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
//			Log.i(TAG, "Pressure");
			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
//			Log.i(TAG, "Proximity");
			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
				//			Log.i(TAG, "Relative Humidity");
				mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY), SensorManager.SENSOR_DELAY_NORMAL);
			}
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null) {
//			Log.i(TAG, "Rotation Vector");
			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);

		}
	}
	
	public void toggleRecord(boolean toggle) {
		this.toggleRecord = toggle;
	}
	
	public void setBackground(boolean state) {
		this.isBackground = state;
	}
	
	public void singleToggle(int toggleID, boolean toggle) {
		switch (toggleID) {
		case R.id.accSwitch:
			accToggle = toggle;
			break;
		case R.id.gyroSwitch:
			gyroToggle = toggle;
			break;
		case R.id.gravitySwitch:
			gravityToggle = toggle;
			break;
		case R.id.lineAccSwitch:
			linAccToggle = toggle;
			break;
		case R.id.magFieldSwitch:
			magToggle = toggle;
			break;
		case R.id.rotVecSwitch:
			rotVecToggle = toggle;
			break;
		case R.id.ambTempSwitch:
			tempToggle = toggle;
			break;
		case R.id.lightSwitch:
			lightToggle = toggle;
			break;
		case R.id.pressureSwitch:
			pressureToggle = toggle;
			break;
		case R.id.proxSwitch:
			proxiToggle = toggle;
			break;
		case R.id.relaHumidSwitch:
			relaHumToggle = toggle;
			break;
		}
	}
	
	private void sendMessage(String sensorName, float[] values) {
		Intent intent = new Intent("SensorData");
		intent.putExtra(sensorName, values);
		mLocalBroadcastManager.sendBroadcast(intent);
	}
	
	/**
	 * 
	 * @param sensorName
	 * @param values
	 * @throws IOException
	 */
	private void recordToFile(String sensorName, float[] values){
		// First check the storage state
		if (checkStorageState() == true && toggleRecord == true) {
			try {
			File f = new File(getStorageDirectory(), sensorName + ".csv");
			CSVWriter csvWriter = new CSVWriter(new FileWriter(f, true));
//			String[] header = "Time#x#y#z".split("#");
//			csvWriter.writeNext(header);
			long date = new Date().getTime();
			csvWriter.writeNext(new String[]{Long.toString(date), 
											Float.toString(values[0]), 
											Float.toString(values[1]), 
											Float.toString(values[2])});
			csvWriter.close();
			
//			Log.d(TAG, "Record: " + date);
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Check the External Storage state
	 * @return true if the external storage is available and readable;
	 * 			false if the external storage state is either unavailable or not readable
	 */
	private boolean checkStorageState() {
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but all we need
			//  to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		
		return (mExternalStorageAvailable && mExternalStorageWriteable);
	}
	
	private File getStorageDirectory() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			return getExternalFilesDir(null);
		} else {
			return new File(Environment.getExternalStorageDirectory(), 
					"/Android/data/" + this.getPackageName() + "/files/");
		}
	}
}
