package com.yeyaxi.android.sensorfun.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yeyaxi.android.sensorfun.RecordListFragment;
import com.yeyaxi.android.sensorfun.SensorFragment;

/**
 * Created by yaxi on 24/04/2014.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                // sensor list
                return new SensorFragment();
            case 1:
                // record list
                return new RecordListFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
