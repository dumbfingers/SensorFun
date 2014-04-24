package com.yeyaxi.android.sensorfun;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;


public class PlotActivity extends Activity {
	
	private static final String TAG = PlotActivity.class.getSimpleName();
//	private SensorManager mSensorManager;
	private SensorService mBoundService;
	private boolean isBind = false;
	
	private BroadcastReceiver mReceiver;
	
	private int sensorType;
	private float[] sensorVal;
	
//	private GraphView mGraphView;
//	private GraphViewSeries xSeries = null;
//	private GraphViewSeries ySeries = null;
//	private GraphViewSeries zSeries = null;
	
//	private GraphViewData xData;
//	private GraphViewData yData;
//	private GraphViewData zData;

    private GraphicalView graphicalView;
    private XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
    private XYMultipleSeriesRenderer dataRenderer = new XYMultipleSeriesRenderer();
    private XYSeries chartSeries;
    private XYSeriesRenderer seriesRenderer;

	private static final int DATA_NUM = 150;
	
	private int counter = 0;

    private String chartTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plot);

		Bundle b = getIntent().getExtras();
		
		if (b != null) {
            // get the sensor type
			sensorType = b.getInt("sensorType");
            // set the chart title
            switch (sensorType) {
                case Sensor.TYPE_ACCELEROMETER:
                    chartTitle = "Accelerometer";
                    break;
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    chartTitle = "Ambient Temperature";
                    break;
                case Sensor.TYPE_GAME_ROTATION_VECTOR:
                    chartTitle = "Game Rotation Vector";
                    break;
                case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
                    chartTitle = "Geomagnetic Rotation Vector";
                    break;
                case Sensor.TYPE_GRAVITY:
                    chartTitle = "Gravity";
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    chartTitle = "Gyroscope";
                    break;
                case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
                    chartTitle = "Gyroscope Uncalibrated";
                    break;
                case Sensor.TYPE_LIGHT:
                    chartTitle = "Light";
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    chartTitle = "Linear Acceleration";
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    chartTitle = "Magnetic Field";
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
                    chartTitle = "Magnetic Field Uncalibrated";
                    break;
                case Sensor.TYPE_PRESSURE:
                    chartTitle = "Pressure";
                    break;
                case Sensor.TYPE_PROXIMITY:
                    chartTitle = "Proximity";
                    break;
                case Sensor.TYPE_RELATIVE_HUMIDITY:
                    chartTitle = "Relative Humidity";
                    break;
                case Sensor.TYPE_ROTATION_VECTOR:
                    chartTitle = "Rotation Vector";
                    break;
                case Sensor.TYPE_SIGNIFICANT_MOTION:
                    chartTitle = "Significant Motion";
                    break;
                case Sensor.TYPE_STEP_COUNTER:
                    chartTitle = "Step Counter";
                    break;
                case Sensor.TYPE_STEP_DETECTOR:
                    chartTitle = "Step Detector";
                    break;
            }
		}



		
//		if (findViewById(R.id.fragment_container) != null) {
//
//			if (savedInstanceState != null) {
//				return;
//			}
//		}
		
		mReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getFloatArrayExtra(String.valueOf(sensorType)) != null) {
					sensorVal = intent.getFloatArrayExtra(String.valueOf(sensorType));
//					++counter;
//					// These sensors are only one dimensional
					if (sensorType == Sensor.TYPE_PRESSURE ||
							sensorType == Sensor.TYPE_PROXIMITY ||
							sensorType == Sensor.TYPE_RELATIVE_HUMIDITY ||
							sensorType == Sensor.TYPE_LIGHT ||
							sensorType == Sensor.TYPE_AMBIENT_TEMPERATURE) {
//						xSeries.appendData(new GraphViewData(counter, sensorVal[0]), true, 500);
					} else {
////						Log.d(TAG, "" + sensorVal[0]);
//						xSeries.appendData(new GraphViewData(counter, sensorVal[0]), true, 500);
//						ySeries.appendData(new GraphViewData(counter, sensorVal[1]), true, 500);
//						zSeries.appendData(new GraphViewData(counter, sensorVal[2]), true, 500);
					}
//					mGraphView.redrawAll();
				}
			}
		};
				
//		mGraphView = new LineGraphView(this, sensorType);
//		mGraphView.setScrollable(true);
//		mGraphView.setScalable(true);
//		mGraphView.setViewPort(0, 500);
//		// Set style of vertical labels
//		mGraphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
//		mGraphView.getGraphViewStyle().setVerticalLabelsWidth(200);
//		// Set style of horizontal labels
//		mGraphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
//		mGraphView.setShowLegend(true);
//		mGraphView.setLegendAlign(LegendAlign.BOTTOM);
//		mGraphView.setLegendWidth(200);
//
//		// These sensors are only one dimensional
//		if (sensorType.equals("pressure") ||
//				sensorType.equals("proximity") ||
//				sensorType.equals("relative_humidity") ||
//				sensorType.equals("light") ||
//				sensorType.equals("ambient_temperature")) {
//
//			if (sensorType.equals("pressure")) {
//				xSeries = new GraphViewSeries("mBar", new GraphViewSeriesStyle(Color.BLUE, 3), new GraphViewData[]{});
//			} else if (sensorType.equals("proximity")) {
//				xSeries = new GraphViewSeries("centimetres", new GraphViewSeriesStyle(Color.BLUE, 3), new GraphViewData[]{});
//				mGraphView.setVerticalLabels(new String[]{"Far", "Near"});
//			} else if (sensorType.equals("relative_humidity")) {
//				xSeries = new GraphViewSeries("RH%", new GraphViewSeriesStyle(Color.BLUE, 3), new GraphViewData[]{});
//			} else if (sensorType.equals("light")) {
//				xSeries = new GraphViewSeries("lux", new GraphViewSeriesStyle(Color.BLUE, 3), new GraphViewData[]{});
//			} else if (sensorType.equals("ambient_temperature")) {
//				xSeries = new GraphViewSeries("Celsius", new GraphViewSeriesStyle(Color.BLUE, 3), new GraphViewData[]{});
//			}
//
//		} else {
//			// Init the series with empty data
//			xSeries = new GraphViewSeries("x", new GraphViewSeriesStyle(Color.BLUE, 3), new GraphViewData[]{});
//			ySeries = new GraphViewSeries("y", new GraphViewSeriesStyle(Color.GREEN, 3), new GraphViewData[]{});
//			zSeries = new GraphViewSeries("z", new GraphViewSeriesStyle(Color.RED, 3), new GraphViewData[]{});
//		}
//
//		if (xSeries != null)
//			mGraphView.addSeries(xSeries);
//		if (ySeries != null)
//			mGraphView.addSeries(ySeries);
//		if (zSeries != null)
//			mGraphView.addSeries(zSeries);
//
//
//		LinearLayout layout = (LinearLayout) findViewById(R.id.fragment_container);
//
//		layout.addView(mGraphView);
//
		// bind the service
		doBindService();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("SensorData"));
        if (graphicalView == null) {
            initChart();
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

    private void initChart() {
        chartSeries = new XYSeries(chartTitle);
        dataSet.addSeries(chartSeries);
        seriesRenderer = new XYSeriesRenderer();
        dataRenderer.addSeriesRenderer(seriesRenderer);

    }

    private void addChartData(float[] dataArray) {
        // TODO add chart data
//        chartSeries.add();
    }

}
