package com.yeyaxi.android.sensorfun.hulahoop.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yeyaxi.android.sensorfun.hulahoop.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by yaxi on 24/04/2014.
 */
public class RecordListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List list;
    private LayoutInflater inflater;
    private ViewHolder holder = null;

    public RecordListAdapter(Context context, int layout, List list) {
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

            holder.textSensorName = (TextView) view.findViewById(R.id.textViewRecordSensorName);
            holder.textUpdateTime = (TextView) view.findViewById(R.id.textViewUpdateTime);

            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        File file = (File) list.get(position);

        if (file != null) {
            holder.textSensorName.setText(file.getName());
            // get the file changed time
            long time = file.lastModified();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);
            String timeString = sdf.format(new Date(time));
            holder.textUpdateTime.setText("Last updated: " + timeString);
        }

        return view;
    }

    static class ViewHolder {
        TextView textSensorName;
        TextView textUpdateTime;
    }
}
