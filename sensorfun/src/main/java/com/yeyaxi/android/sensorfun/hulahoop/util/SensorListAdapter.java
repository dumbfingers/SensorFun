package com.yeyaxi.android.sensorfun.hulahoop.util;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yeyaxi.android.sensorfun.hulahoop.R;

import java.util.List;

/**
 * Created by yaxi on 23/04/2014.
 */
public class SensorListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List list;
    private LayoutInflater inflater;
    private ViewHolder holder = null;

    public SensorListAdapter(Context context, int layout, List list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null)
            return list.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {

            view = inflater.inflate(layout, parent, false);
            holder = new ViewHolder();

            holder.textSensorName = (TextView) view.findViewById(R.id.textViewSensorName);
            holder.textSensorInfo = (TextView) view.findViewById(R.id.textViewSensorInfo);
            holder.imageSensorIcon = (ImageView) view.findViewById(R.id.imageViewSensorIcon);

            view.setTag(holder);

        } else {
            holder = (ViewHolder)view.getTag();
        }

        Sensor sensor = (Sensor)list.get(position);
        holder.textSensorName.setText(sensor.getName());
        holder.textSensorInfo.setText("Vendor: " + sensor.getVendor() + "\n" +
                                        "Resolution: " + sensor.getResolution() + "\n" +
                                        "Max. Range: " + sensor.getMaximumRange() + "\n" +
                                        "Min. Delay: " + sensor.getMinDelay());


        return view;
    }

    static class ViewHolder {
        TextView textSensorName;
        TextView textSensorInfo;
        ImageView imageSensorIcon;
    }
}
