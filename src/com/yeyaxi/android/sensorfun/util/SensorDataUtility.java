package com.yeyaxi.android.sensorfun.util;

import java.text.DecimalFormat;

/**
 * 
 * @author Yaxi Ye
 * @version 1.0
 * @since Oct 11 2013
 *
 */
public class SensorDataUtility {
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String roundData(float data) {
		DecimalFormat df = new DecimalFormat("#.####");
		return df.format((double) data);
	}
	
	//TODO Low-pass filter to be implemented here
}
