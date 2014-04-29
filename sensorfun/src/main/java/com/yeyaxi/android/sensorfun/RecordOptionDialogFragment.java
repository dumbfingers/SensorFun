package com.yeyaxi.android.sensorfun;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.yeyaxi.android.sensorfun.RecordOptionDialogFragment.OnRecordOptionDialogFragmentInteractionListener} interface
 * to handle interaction events.
 *
 */
public class RecordOptionDialogFragment extends SherlockDialogFragment {

    private OnRecordOptionDialogFragmentInteractionListener mListener;

    private String sensorString;

    public RecordOptionDialogFragment() {
        // Required empty public constructor
    }

    public RecordOptionDialogFragment(String sensorString) {
        this.sensorString = sensorString;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_record_options, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
                .setIcon(R.drawable.ic_action_save)
                .setTitle("Start Recording")
                // Add action buttons
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (mListener != null) {
                            mListener.onDialogFragmentInteraction(true);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RecordOptionDialogFragment.this.getDialog().cancel();
                    }
                });
        TextView textView = (TextView) v.findViewById(R.id.textViewSensorSelected);
        textView.setText(sensorString);
        return builder.create();
    }

    //  Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onDialogFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRecordOptionDialogFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnRecordOptionDialogFragmentInteractionListener");
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
    public interface OnRecordOptionDialogFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onDialogFragmentInteraction(boolean startRecord);
    }

}
