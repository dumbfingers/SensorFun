package com.yeyaxi.android.sensorfun;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.LineGraphView;
import com.yeyaxi.android.sensorfun.util.SensorDataUtility;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class PlotActivity extends Activity {
	
	private SensorManager mSensorManager;
	private SensorService mBoundService;
	private boolean isBind = false;
	
	private BroadcastReceiver mReceiver;
	
	private String sensorType = "";
	private float[] sensorVal;
	
	private GraphView mGraphView;
	private GraphViewSeries xSeries;
	private GraphViewSeries ySeries;
	private GraphViewSeries zSeries;
	
	private GraphViewData xData;
	private GraphViewData yData;
	private GraphViewData zData;
	
	private int counter = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plot);
		
		
		Bundle b = getIntent().getExtras();
		
		if (b != null) {
			sensorType = b.getString("sensorType");
		}
		
		
		if (findViewById(R.id.fragment_container) != null) {
			
			if (savedInstanceState != null) {
				return;
			}		
		}
		
		mReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getFloatArrayExtra(sensorType) != null) {
					sensorVal = intent.getFloatArrayExtra(sensorType);
					++counter;
					
					xData = new GraphViewData(counter, sensorVal[0]);
					if (xSeries != null)
						xSeries.appendData(xData, false, 500);
					else {
						GraphViewData[] data = {xData};
						xSeries = new GraphViewSeries(data);
					}
				}
			}
		};
		

		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("SensorData"));
		// bind the service
		doBindService();
		
		if (isBind == true) {
			// Draw plot
			drawPlot();
		}
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
	
	private void drawPlot() {
		
		mGraphView = new LineGraphView(this, sensorType);
		mGraphView.addSeries(xSeries);
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.fragment_container);
		
		layout.addView(mGraphView);

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
//			mSensorManager = mBoundService.getSensorManager();
			isBind = true;
		}
	};
	
	private void doBindService() {
		bindService(new Intent(this, SensorService.class), mConnection, Context.BIND_AUTO_CREATE);
	}
	
	private void doUnbindService() {
		unbindService(mConnection);
	}
}
