package com.yeyaxi.android.sensorfun;

import android.R.color;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;

public class PlotActivity extends Activity {
	
	private static final String TAG = PlotActivity.class.getSimpleName();
//	private SensorManager mSensorManager;
	private SensorService mBoundService;
	private boolean isBind = false;
	
	private BroadcastReceiver mReceiver;
	
	private String sensorType = "";
	private float[] sensorVal;
	
	private GraphView mGraphView;
	private GraphViewSeries xSeries;
	private GraphViewSeries ySeries;
	private GraphViewSeries zSeries;
	
//	private GraphViewData xData;
//	private GraphViewData yData;
//	private GraphViewData zData;
	
	private static final int DATA_NUM = 150;
	
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
//					Log.d(TAG, "" + sensorVal[0]);
					++counter;
					xSeries.appendData(new GraphViewData(counter, sensorVal[0]), true, 500);
					ySeries.appendData(new GraphViewData(counter, sensorVal[1]), true, 500);
					zSeries.appendData(new GraphViewData(counter, sensorVal[2]), true, 500);

					mGraphView.redrawAll();
				}
			}
		};
		
		
		mGraphView = new LineGraphView(this, sensorType);
		mGraphView.setScrollable(true);
		mGraphView.setScalable(true);
		mGraphView.setViewPort(0, 500);
		mGraphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
		mGraphView.getGraphViewStyle().setVerticalLabelsWidth(100);
		mGraphView.setShowLegend(true);
		 
		// Init the series with empty data
		xSeries = new GraphViewSeries("x", new GraphViewSeriesStyle(Color.CYAN, 3), new GraphViewData[]{});
//		xSeries.getStyle().color = Color.CYAN;
		
		ySeries = new GraphViewSeries("y", new GraphViewSeriesStyle(Color.MAGENTA, 3), new GraphViewData[]{});
		zSeries = new GraphViewSeries("z", new GraphViewSeriesStyle(Color.RED, 3), new GraphViewData[]{});

		mGraphView.addSeries(xSeries);
		mGraphView.addSeries(ySeries);
		mGraphView.addSeries(zSeries);

		
		LinearLayout layout = (LinearLayout) findViewById(R.id.fragment_container);
		
		layout.addView(mGraphView);
		
		// bind the service
		doBindService();
		
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
