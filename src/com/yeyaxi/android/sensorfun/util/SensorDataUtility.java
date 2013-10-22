package com.yeyaxi.android.sensorfun.util;

import java.text.DecimalFormat;

/**
 * 
 * @author Yaxi Ye
 * @version 1.0
 * @since Oct 15 2013
 *
 */
public class SensorDataUtility {
	
	float alpha = 0.1f;
	
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
	/**
	 * Simple low-pass filter
	 * @param current
	 * @param last
	 * @return
	 */
	float lowPass(float current, float last) {
		
		return last * (1.0f - alpha) + current * alpha; 
	}
	/**
	 * Kalman Filter
	 * @param guess A guess value of sensor (or previous calculated value)
	 * @param measurement Measured value from sensor (or current sensor output)
	 * @param guess_variance guessed sensor value (or previously calculated weight)
	 * @param sensor_variance sensor's variance
	 * @return caculated value
	 */
	public static float kalmanFilter(float guess, float measurement, float guess_variance, float sensor_variance) {
		
		float weight = calcWeight(guess_variance, sensor_variance);
		
		float estimate = calcEstimate(guess_variance, weight, measurement);
		
		return estimate;
	}
	
	/**
	 * 
	 * @param guess_variance
	 * @param sensor_variance
	 * @return
	 */
	public static float calcWeight(float guess_variance, float sensor_variance) {
		return guess_variance / (guess_variance + sensor_variance);
	}
	
	/**
	 * 
	 * @param guess
	 * @param weight
	 * @param measurement
	 * @return
	 */
	public static float calcEstimate(float guess, float weight, float measurement) {
		return guess + weight * (measurement - guess);
	}
	
	/**
	 * 
	 * @param guess_variance
	 * @param sensor_variance
	 * @return
	 */
	public static float calcEstVariance(float guess_variance, float sensor_variance) {
		return guess_variance*sensor_variance / (guess_variance+sensor_variance);
	}
}
