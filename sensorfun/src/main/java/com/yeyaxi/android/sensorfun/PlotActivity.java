package com.yeyaxi.android.sensorfun;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.hardware.Sensor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockActivity;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Date;


public class PlotActivity extends SherlockActivity {

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
    private TimeSeries xChartSeries; // x axis
    private TimeSeries yChartSeries; // y axis
    private TimeSeries zChartSeries; // z axis
    private XYSeriesRenderer xSeriesRenderer;
    private XYSeriesRenderer ySeriesRenderer;
    private XYSeriesRenderer zSeriesRenderer;

    private static final int DATA_NUM = 150;

    private int counter = 0;

    private String chartTitle;

    private boolean isChartReady = false;

    private LinearLayout layout;
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


        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getFloatArrayExtra(String.valueOf(sensorType)) != null) {
                    // sensor value array
                    sensorVal = intent.getFloatArrayExtra(String.valueOf(sensorType));
                    long timestamp = intent.getLongExtra("timestamp", 0);
                    if (isChartReady == true && layout.getChildCount() > 0) {
                        addChartData(sensorVal, timestamp);
                        graphicalView.repaint();
                    }
                }
            }
        };

        // bind the service
        doBindService();

    }

    @Override
    protected void onResume() {
        super.onResume();
        layout = (LinearLayout) findViewById(R.id.chart);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("SensorData"));
        if (graphicalView == null) {
            initChart();
            graphicalView = ChartFactory.getTimeChartView(PlotActivity.this, dataSet, dataRenderer, "HH:mm:ss.SSS");
            layout.addView(graphicalView);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        isChartReady = false;
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
        xChartSeries = new TimeSeries("x");
        yChartSeries = new TimeSeries("y");
        zChartSeries = new TimeSeries("z");
        dataSet.addSeries(xChartSeries);
        dataSet.addSeries(yChartSeries);
        dataSet.addSeries(zChartSeries);

        xSeriesRenderer = new XYSeriesRenderer();
        xSeriesRenderer.setColor(Color.parseColor("#99cc00"));

        ySeriesRenderer = new XYSeriesRenderer();
        ySeriesRenderer.setColor(Color.parseColor("#ffbb33"));

        zSeriesRenderer = new XYSeriesRenderer();
        zSeriesRenderer.setColor(Color.parseColor("#ff4444"));

        dataRenderer.addSeriesRenderer(xSeriesRenderer);
        dataRenderer.addSeriesRenderer(ySeriesRenderer);
        dataRenderer.addSeriesRenderer(zSeriesRenderer);

        dataRenderer.setBackgroundColor(XYMultipleSeriesRenderer.NO_COLOR);
        dataRenderer.setLabelsColor(Color.parseColor("#0099cc"));
        dataRenderer.setChartTitle(chartTitle);
        isChartReady = true;
    }

    private void addChartData(float[] dataArray, long timestamp) {
        if (timestamp != 0) {
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
            Date date = new Date(timestamp);

            // TODO add chart data
            if (sensorType == Sensor.TYPE_PRESSURE ||
                    sensorType == Sensor.TYPE_PROXIMITY ||
                    sensorType == Sensor.TYPE_RELATIVE_HUMIDITY ||
                    sensorType == Sensor.TYPE_LIGHT ||
                    sensorType == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                // 1-d array
                xChartSeries.add(date, dataArray[0]);

            } else {

                xChartSeries.add(date, dataArray[0]);
                yChartSeries.add(date, dataArray[1]);
                zChartSeries.add(date, dataArray[2]);
            }
        }

    }
}
