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
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.yeyaxi.android.sensorfun.util.SensorDataUtility;

public class MainActivity extends BaseActivity {

	
	private SensorManager mSensorManager;
	private List<Sensor> deviceSensors;
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private TextView accValX;
	private TextView accValY;
	private TextView accValZ;
	
	private TextView ambTempVal;
	
	private TextView gyroValX;
	private TextView gyroValY;
	private TextView gyroValZ;

	private TextView gravityX;
	private TextView gravityY;
	private TextView gravityZ;

	private TextView lightVal;
	
	private TextView magValX;
	private TextView magValY;
	private TextView magValZ;

	private TextView linearAccX;
	private TextView linearAccY;
	private TextView linearAccZ;

	private TextView pressureVal;
	
	private TextView proxiVal;
	
	private TextView relatHumidVal;
	
	private TextView rotVecValX;
	private TextView rotVecValY;
	private TextView rotVecValZ;

	private ToggleButton recordToggle;
	// For table rows
	private TableRow gpsRow;
	private TableRow accelRow;
	private TableRow gyroRow;
	private TableRow gravityRow;
	private TableRow linAccRow;
	private TableRow magRow;
	private TableRow rotVecRow;
	private TableRow tempRow;
	private TableRow lightRow;
	private TableRow pressureRow;
	private TableRow proxiRow;
	private TableRow relaHumidRow;
	
	private SensorService mBoundService;
	private boolean isBind = false;
	
	private BroadcastReceiver mReceiver;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		accValX = (TextView) findViewById(R.id.accValX);
		accValY = (TextView) findViewById(R.id.accValY);
		accValZ = (TextView) findViewById(R.id.accValZ);
		
		ambTempVal = (TextView) findViewById(R.id.tempVal);
		
		gyroValX = (TextView) findViewById(R.id.gyroValX);
		gyroValY = (TextView) findViewById(R.id.gyroValY);
		gyroValZ = (TextView) findViewById(R.id.gyroValZ);
		
		gravityX = (TextView) findViewById(R.id.gravityValX);
		gravityY = (TextView) findViewById(R.id.gravityValY);
		gravityZ = (TextView) findViewById(R.id.gravityValZ);
		
		lightVal = (TextView) findViewById(R.id.lightVal);
		
		magValX = (TextView) findViewById(R.id.magValX);
		magValY = (TextView) findViewById(R.id.magValY);
		magValZ = (TextView) findViewById(R.id.magValZ);
		
		linearAccX = (TextView) findViewById(R.id.linAccValX);
		linearAccY = (TextView) findViewById(R.id.linAccValY);
		linearAccZ = (TextView) findViewById(R.id.linAccValZ);

		pressureVal = (TextView) findViewById(R.id.pressureVal);
		
		proxiVal = (TextView) findViewById(R.id.proxiVal);
		
		relatHumidVal = (TextView) findViewById(R.id.relaHumidVal);
		
		rotVecValX = (TextView) findViewById(R.id.rotValX);
		rotVecValY = (TextView) findViewById(R.id.rotValY);
		rotVecValZ = (TextView) findViewById(R.id.rotValZ);
		
		recordToggle = (ToggleButton) findViewById(R.id.toggleRecord);
		
		// Init for table rows
		gpsRow = (TableRow) findViewById(R.id.tableRowGPS);
		accelRow = (TableRow) findViewById(R.id.tableRowAccel);
		gyroRow = (TableRow) findViewById(R.id.tableRowGyro);
		gravityRow = (TableRow) findViewById(R.id.tableRowGravity);
		linAccRow = (TableRow) findViewById(R.id.tableRowLinearAcc);
		magRow = (TableRow) findViewById(R.id.tableRowMagField);
		rotVecRow = (TableRow) findViewById(R.id.tableRowRotVec);
		tempRow = (TableRow) findViewById(R.id.tableRowAmbientTemp);
		lightRow = (TableRow) findViewById(R.id.tableRowLight);
		pressureRow = (TableRow) findViewById(R.id.tableRowPressure);
		proxiRow = (TableRow) findViewById(R.id.tableRowProximity);
		relaHumidRow = (TableRow) findViewById(R.id.tableRowRelaHumid);
		
		
		// Register GPS row listener first
		gpsRow.setOnClickListener(rowClickListener);
		// Other sensors
		accelRow.setOnClickListener(rowClickListener);
		gyroRow.setOnClickListener(rowClickListener);
		gravityRow.setOnClickListener(rowClickListener);
		linAccRow.setOnClickListener(rowClickListener);
		magRow.setOnClickListener(rowClickListener);
		rotVecRow.setOnClickListener(rowClickListener);
		tempRow.setOnClickListener(rowClickListener);
		lightRow.setOnClickListener(rowClickListener);
		pressureRow.setOnClickListener(rowClickListener);
		proxiRow.setOnClickListener(rowClickListener);
		relaHumidRow.setOnClickListener(rowClickListener);
		
		mReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {

				if (intent.getFloatArrayExtra("accelerometer") != null) {
					float[] accFloats = intent.getFloatArrayExtra("accelerometer");
					accValX.setText(SensorDataUtility.roundData(accFloats[0])); 
					accValY.setText(SensorDataUtility.roundData(accFloats[1])); 
					accValZ.setText(SensorDataUtility.roundData(accFloats[2]));
				}
				if (intent.getFloatArrayExtra("magnetic_field") != null) {
					float[] magFloats = intent.getFloatArrayExtra("magnetic_field");
					magValX.setText(SensorDataUtility.roundData(magFloats[0])); 
					magValY.setText(SensorDataUtility.roundData(magFloats[1])); 
					magValZ.setText(SensorDataUtility.roundData(magFloats[2])); 

				}
				if (intent.getFloatArrayExtra("gyroscope") != null) {
					float[] gyroFloats = intent.getFloatArrayExtra("gyroscope");
					gyroValX.setText(SensorDataUtility.roundData(gyroFloats[0]));
					gyroValY.setText(SensorDataUtility.roundData(gyroFloats[1]));
					gyroValZ.setText(SensorDataUtility.roundData(gyroFloats[2]));

				}
				if (intent.getFloatArrayExtra("light") != null) {
					float[] lightFloats = intent.getFloatArrayExtra("light");
					lightVal.setText(SensorDataUtility.roundData(lightFloats[0]));
				}
				if (intent.getFloatArrayExtra("pressure") != null) {
					float[] pressureFloats = intent.getFloatArrayExtra("pressure");
					pressureVal.setText(SensorDataUtility.roundData(pressureFloats[0]));
				}
				if (intent.getFloatArrayExtra("proximity") != null) {
					float[] proxiFloats = intent.getFloatArrayExtra("proximity");
					proxiVal.setText(SensorDataUtility.roundData(proxiFloats[0]));
				}
				if (intent.getFloatArrayExtra("gravity") != null) {
					float[] gravityFloats = intent.getFloatArrayExtra("gravity");
					gravityX.setText(SensorDataUtility.roundData(gravityFloats[0]));
					gravityY.setText(SensorDataUtility.roundData(gravityFloats[1]));
					gravityZ.setText(SensorDataUtility.roundData(gravityFloats[2]));
				}
				if (intent.getFloatArrayExtra("linear_acceleration") != null) {
					float[] linearAccFloats = intent.getFloatArrayExtra("linear_acceleration");
					linearAccX.setText(SensorDataUtility.roundData(linearAccFloats[0]));
					linearAccY.setText(SensorDataUtility.roundData(linearAccFloats[1]));
					linearAccZ.setText(SensorDataUtility.roundData(linearAccFloats[2]));
				}
				if (intent.getFloatArrayExtra("rotation_vector") != null) {
					float[] rotVecFloats = intent.getFloatArrayExtra("rotation_vector");
					rotVecValX.setText(SensorDataUtility.roundData(rotVecFloats[0]));
					rotVecValY.setText(SensorDataUtility.roundData(rotVecFloats[1]));
					rotVecValZ.setText(SensorDataUtility.roundData(rotVecFloats[2]));
				}
				if (intent.getFloatArrayExtra("relative_humidity") != null) {
					float[] relatHumidFloats = intent.getFloatArrayExtra("relative_humidity");
					relatHumidVal.setText(SensorDataUtility.roundData(relatHumidFloats[0]));
				}
				if (intent.getFloatArrayExtra("ambient_temperature") != null) {
					float[] ambTempFloats = intent.getFloatArrayExtra("ambient_temperature");
					ambTempVal.setText(SensorDataUtility.roundData(ambTempFloats[0]));
				}
			}
		};
		
		// bind the service
		doBindService();
		
		recordToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mBoundService.toggleRecord(isChecked);
			}
		});
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	
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
		
		Log.d(TAG, "" + deviceSensors);
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) {
//			Log.i(TAG, "Accelerometer");
			accelRow.setVisibility(View.GONE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) == null) {
//			Log.i(TAG, "Ambient Temperature");
			tempRow.setVisibility(View.GONE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) == null) {
//			Log.i(TAG, "Gravity");
			gravityRow.setVisibility(View.GONE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null) {
//			Log.i(TAG, "Gyroscope");
			gyroRow.setVisibility(View.GONE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) == null) {
//			Log.i(TAG, "Light");
			lightRow.setVisibility(View.GONE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) == null) {
//			Log.i(TAG, "Linear Acceleration");
			linAccRow.setVisibility(View.GONE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) == null) {
//			Log.i(TAG, "Magnetic Field");
			magRow.setVisibility(View.GONE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
//		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) == null)
//			Log.i(TAG, "Orientation");
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) == null) {
//			Log.i(TAG, "Pressure");
			pressureRow.setVisibility(View.GONE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null) {
//			Log.i(TAG, "Proximity");
			proxiRow.setVisibility(View.GONE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) == null) {
//			Log.i(TAG, "Relative Humidity");
			relaHumidRow.setVisibility(View.GONE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY), SensorManager.SENSOR_DELAY_NORMAL);

		}
		
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) == null) {
//			Log.i(TAG, "Rotation Vector");
			rotVecRow.setVisibility(View.GONE);
//			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);

		}
	}
}
