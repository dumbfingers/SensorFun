package com.yeyaxi.android.sensorfun;

import java.util.List;

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
import android.view.View;

public class SensorService extends Service implements SensorEventListener{

	private String msg = "Test Msg";
	
	private SensorManager mSensorManager;
	private List<Sensor> deviceSensors;
	private static final String TAG = SensorService.class.getSimpleName();
	private LocalBroadcastManager mLocalBroadcastManager;
	
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
		return mBinder;
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
//			Log.i(TAG, "Accelerometer");
			sendMessage("accelerometer", event.values);
			
		} else if (mSensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
//			Log.i(TAG, "Accelerometer");
			sendMessage("ambient_temperature", event.values);

		} else if (mSensor.getType() == Sensor.TYPE_GRAVITY) {
//			Log.i(TAG, "Accelerometer");
			sendMessage("gravity", event.values);

		} else if (mSensor.getType() == Sensor.TYPE_GYROSCOPE) {
//			Log.i(TAG, "Accelerometer");
			sendMessage("gyroscope", event.values);

		} else if (mSensor.getType() == Sensor.TYPE_LIGHT) {
//			Log.i(TAG, "Accelerometer");
			sendMessage("light", event.values);

		} else if (mSensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
//			Log.i(TAG, "Accelerometer");
			sendMessage("linear_acceleration", event.values);

		} else if (mSensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
//			Log.i(TAG, "Accelerometer");
			sendMessage("magnetic_field", event.values);

		} else if (mSensor.getType() == Sensor.TYPE_PRESSURE) {
//			Log.i(TAG, "Accelerometer");
			sendMessage("pressure", event.values);

		} else if (mSensor.getType() == Sensor.TYPE_PROXIMITY) {
//			Log.i(TAG, "Accelerometer");
			sendMessage("proximity", event.values);

		} else if (mSensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
//			Log.i(TAG, "Accelerometer");
			sendMessage("relative_humidity", event.values);

		} else if (mSensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
//			Log.i(TAG, "Accelerometer");
			sendMessage("rotation_vector", event.values);
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
