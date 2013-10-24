package com.yeyaxi.android.sensorfun;

import com.yeyaxi.android.sensorfun.util.SensorDataUtility;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

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
	// Roatation Vector contains 5 elements
	private float[] rotVals = new float[5];

	
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
		
		mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		regSensorListeners();

	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		Sensor mSensor = event.sensor;
		
		if (mSensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			
			accVals = SensorDataUtility.lowPass(event.values, accVals);
			
			sendMessage("accelerometer", accVals);
			
		} else if (mSensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {

			tempVal = SensorDataUtility.lowPass(event.values, tempVal);

			sendMessage("ambient_temperature", tempVal);

		} else if (mSensor.getType() == Sensor.TYPE_GRAVITY) {
			
			gravVals = SensorDataUtility.lowPass(event.values, gravVals);
			
			sendMessage("gravity", gravVals);

		} else if (mSensor.getType() == Sensor.TYPE_GYROSCOPE) {
			
			gyroVals = SensorDataUtility.lowPass(event.values, gyroVals);
			
			sendMessage("gyroscope", gyroVals);

		} else if (mSensor.getType() == Sensor.TYPE_LIGHT) {
			
			lightVal = SensorDataUtility.lowPass(event.values, lightVal);
			
			sendMessage("light", lightVal);

		} else if (mSensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
			
			lineAccVals = SensorDataUtility.lowPass(event.values, lineAccVals);
			
			sendMessage("linear_acceleration", lineAccVals);

		} else if (mSensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			
			magVals = SensorDataUtility.lowPass(event.values, magVals);
			
			sendMessage("magnetic_field", magVals);

		} else if (mSensor.getType() == Sensor.TYPE_PRESSURE) {
			
			presVal = SensorDataUtility.lowPass(event.values, presVal);
			
			sendMessage("pressure", presVal);

		} else if (mSensor.getType() == Sensor.TYPE_PROXIMITY) {
			
			proxVal = SensorDataUtility.lowPass(event.values, proxVal);
			
			sendMessage("proximity", proxVal);

		} else if (mSensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
			
			rhVal = SensorDataUtility.lowPass(event.values, rhVal);
			
			sendMessage("relative_humidity", rhVal);

		} else if (mSensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
			rotVals = SensorDataUtility.lowPass(event.values, rotVals);
			sendMessage("rotation_vector", rotVals);
		}
	}

	private void regSensorListeners() {
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
//			Log.i(TAG, "Accelerometer");
			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
//			Log.i(TAG, "Ambient Temperature");
			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), SensorManager.SENSOR_DELAY_NORMAL);

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
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
//			Log.i(TAG, "Relative Humidity");
			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null) {
//			Log.i(TAG, "Rotation Vector");
			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);

		}
	}
	
	private void sendMessage(String sensorName, float[] values) {
		Intent intent = new Intent("SensorData");
		intent.putExtra(sensorName, values);
		mLocalBroadcastManager.sendBroadcast(intent);
	}
}
