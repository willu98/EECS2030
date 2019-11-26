package eecs2030.lab6;

import java.util.ArrayList;
import java.util.List;

/**
 * A weather statistics app calculates the minimum, maximum, and average
 * temperature read so far from its subscribed weather station, if any.
 */
public class StatisticsApp extends WeatherObserver {

	/*
	 * Your Task: Declare required fields to have a functioning class
	 */
	private double temp = 0;
	private double min = 0;
	private double max = 0;
	private double ave = 0;
	private List<Double> readings = new ArrayList<Double>();

	private WeatherStation ws;

	/**
	 * Update the reading of this weather observer. Update the current temperature
	 * and the maximum, minimum, and average accordingly.
	 */
	public void update() {
		/* Your Task */
		double sum = 0;

		// getting new temp
		temp = ws.getTemperature();

		if (readings.size() == 0) {
			max = temp;
			min = temp;
		}

		// setting new max and min if it is achieved
		if (temp > max) {
			max = temp;
		}
		if (temp < min) {
			min = temp;
		}

		// adding to list of readings
		readings.add(temp);

		// reading through all temps
		for (Double reading : readings) {
			sum += reading;
		}

		this.ave = (sum / readings.size());

	}

	/* See the method description in the parent class */
	public WeatherStation getWeatherStation() {
		/* Your Task */
		return ws;
	}

	/* See the method description in the parent class */
	public void setWeatherStation(WeatherStation ws) {
		/* Your Task */
		this.ws = ws;
	}

	/**
	 * Get the minimum temperature based on the readings so far.
	 * 
	 * @return minimum temperature based on the readings so far
	 */
	public double getMinTemperature() {
		/* Your Task */
		return min;
	}

	/**
	 * Get the maximum temperature based on the readings so far.
	 * 
	 * @return maximum temperature based on the readings so far
	 */
	public double getMaxTemperature() {
		/* Your Task */
		return max;
	}

	/**
	 * Get the average temperature based on the readings so far.
	 * 
	 * @return average temperature based on the readings so far
	 */
	public double getAverageTemperature() {
		/* Your Task */
		return ave;
	}
}
