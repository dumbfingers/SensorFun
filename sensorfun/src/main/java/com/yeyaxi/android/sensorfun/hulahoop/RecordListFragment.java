package com.yeyaxi.android.sensorfun.hulahoop;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.yeyaxi.android.sensorfun.hulahoop.util.RecordListAdapter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the {Callbacks}
 * interface.
 */
public class RecordListFragment extends SherlockListFragment {

    private OnRecordListFragmentInteractionListener mListener;
    private ArrayList<File> fileArrayList = new ArrayList<File>();
    private RecordListAdapter adapter;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecordListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new RecordListAdapter(getSherlockActivity(), R.layout.item_record, fileArrayList);

        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        File f = getStorageDirectory();
        if (f != null)  {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    if (filename.lastIndexOf('.') > 0) {
                        int lastIndex = filename.lastIndexOf('.');
                        String str = filename.substring(lastIndex);
                        if (str.equals(".csv"))
                            return true;
                    }
                    return false;
                }
            };

            File[] files = f.listFiles(filter);

            for (File file : files) {
                fileArrayList.add(file);
            }

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRecordListFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement OnSensorFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onRecordListFragmentInteraction();
        }
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
    public interface OnRecordListFragmentInteractionListener {
        // Update argument type and name
        public void onRecordListFragmentInteraction();
    }

    private File getStorageDirectory() {

        // This will create a public folder under the sdcard root
        File f = new File(Environment.getExternalStorageDirectory(), "/SensorFun/");
        if (f.exists() == false) {
            if (f.mkdirs() == true) {
                return f;
            } else {
                return null;
            }
        } else {
            return f;
        }

    }

}
