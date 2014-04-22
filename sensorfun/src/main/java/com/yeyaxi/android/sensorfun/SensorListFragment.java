package com.yeyaxi.android.sensorfun;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragment;
import com.yeyaxi.android.sensorfun.util.SensorDataUtility;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SensorListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SensorListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SensorListFragment extends SherlockFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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




    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SensorListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SensorListFragment newInstance(String param1, String param2) {
        SensorListFragment fragment = new SensorListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public SensorListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.fragment_sensor_list, container, false);

        accValX = (TextView) mainView.findViewById(R.id.accValX);
        accValY = (TextView) mainView.findViewById(R.id.accValY);
        accValZ = (TextView) mainView.findViewById(R.id.accValZ);

        ambTempVal = (TextView) mainView.findViewById(R.id.tempVal);

        gyroValX = (TextView) mainView.findViewById(R.id.gyroValX);
        gyroValY = (TextView) mainView.findViewById(R.id.gyroValY);
        gyroValZ = (TextView) mainView.findViewById(R.id.gyroValZ);

        gravityX = (TextView) mainView.findViewById(R.id.gravityValX);
        gravityY = (TextView) mainView.findViewById(R.id.gravityValY);
        gravityZ = (TextView) mainView.findViewById(R.id.gravityValZ);

        lightVal = (TextView) mainView.findViewById(R.id.lightVal);

        magValX = (TextView) mainView.findViewById(R.id.magValX);
        magValY = (TextView) mainView.findViewById(R.id.magValY);
        magValZ = (TextView) mainView.findViewById(R.id.magValZ);

        linearAccX = (TextView) mainView.findViewById(R.id.linAccValX);
        linearAccY = (TextView) mainView.findViewById(R.id.linAccValY);
        linearAccZ = (TextView) mainView.findViewById(R.id.linAccValZ);

        pressureVal = (TextView) mainView.findViewById(R.id.pressureVal);

        proxiVal = (TextView) mainView.findViewById(R.id.proxiVal);

        relatHumidVal = (TextView) mainView.findViewById(R.id.relaHumidVal);

        rotVecValX = (TextView) mainView.findViewById(R.id.rotValX);
        rotVecValY = (TextView) mainView.findViewById(R.id.rotValY);
        rotVecValZ = (TextView) mainView.findViewById(R.id.rotValZ);

//        recordToggle = (ToggleButton) mainView.findViewById(R.id.toggleRecord);

        // Init for table rows
        gpsRow = (TableRow) mainView.findViewById(R.id.tableRowGPS);
        accelRow = (TableRow) mainView.findViewById(R.id.tableRowAccel);
        gyroRow = (TableRow) mainView.findViewById(R.id.tableRowGyro);
        gravityRow = (TableRow) mainView.findViewById(R.id.tableRowGravity);
        linAccRow = (TableRow) mainView.findViewById(R.id.tableRowLinearAcc);
        magRow = (TableRow) mainView.findViewById(R.id.tableRowMagField);
        rotVecRow = (TableRow) mainView.findViewById(R.id.tableRowRotVec);
        tempRow = (TableRow) mainView.findViewById(R.id.tableRowAmbientTemp);
        lightRow = (TableRow) mainView.findViewById(R.id.tableRowLight);
        pressureRow = (TableRow) mainView.findViewById(R.id.tableRowPressure);
        proxiRow = (TableRow) mainView.findViewById(R.id.tableRowProximity);
        relaHumidRow = (TableRow) mainView.findViewById(R.id.tableRowRelaHumid);


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
        // Inflate the layout for this fragment
        return mainView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
