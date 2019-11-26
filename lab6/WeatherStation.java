package eecs2030.lab6;

import java.util.ArrayList;
import java.util.List;

/**
 * A weather station contains sets of weather sensors to measure various weather
 * parameters and publishes the readings to a list of subscribed weather
 * observers.
 * 
 * The weather station pulls the readings from the sensors. Usually there is a
 * set of redundant sensors for each weather parameter such as temperature and
 * pressure. The value reported by the weather station is the average consensus
 * of the readings, i.e. the average of the available measurements. In case any
 * sensor fails, its reading is eliminated from the average and this sensor will
 * be fixed. The weather station notifies the subscribed observers for any
 * updates in the weather parameters.
 * 
 */
public class WeatherStation {

	/*
	 * Your Task: Declare an attribute for the list of subscribed weather observers.
	 */
	private List<WeatherObserver> observers;
	/*
	 * Your Task: Declare attributes for the lists of weather sensors.
	 */
	private List<TemperatureSensor> tempSensors;
	private List<PressureSensor> pressureSensors;

	/**
	 * Initialize a new weather station with no sensors installed.
	 * 
	 */
	public WeatherStation() {
		/* Your Task */
		observers = new ArrayList<WeatherObserver>();
		tempSensors = new ArrayList<TemperatureSensor>();
		pressureSensors = new ArrayList<PressureSensor>();
	}

	/**
	 * Subscribe the input weather observer o as one of the observers of the current
	 * weather station.
	 * 
	 * @param o a weather observer
	 */
	public void subscribe(WeatherObserver o) {
		/* Your Task */
		o.setWeatherStation(this);
		observers.add(o);
	}

	/**
	 * Unsubscribe the input weather observer o from the list of observers of the
	 * current weather station. Assume that the input o is an already-subscribed
	 * observer.
	 * 
	 * @param o a weather observer
	 */
	public void unsubscribe(WeatherObserver o) {
		/* Your Task */
		o.setWeatherStation(null);
		observers.remove(o);
	}

	/**
	 * Get the list of subscribed weather observers.
	 * 
	 * @return an array of subscribed weather observers.
	 */
	public WeatherObserver[] getObservers() {
		/* Your Task */
		int i = 0;
		WeatherObserver[] observersArray = new WeatherObserver[observers.size()];
		for (WeatherObserver observer : observers) {
			observersArray[i] = observer;
			i++;
		}
		return observersArray;
	}

	/**
	 * Publish the latest readings of weather data to all subscribed observers. That
	 * is, perform an update on each subscribed observer.
	 */
	public void publish() {
		/* Your Task */
		for (WeatherObserver observer : observers) {
			observer.update();
		}
	}

	/**
	 * Adds temperature sensor to the corresponding sensor list
	 * 
	 * @param ts temperature sensor to be added
	 */
	public void addTempSensor(TemperatureSensor ts) {
		/* Your Task */
		tempSensors.add(ts);
	}

	/**
	 * Adds pressure sensor to the corresponding sensor list
	 * 
	 * @param ps pressure sensor to be added
	 */
	public void addPressSensor(PressureSensor ps) {
		/* Your Task */
		pressureSensors.add(ps);
	}

	/**
	 * Removes the temperature sensor from the corresponding sensor list
	 * 
	 * @param ts temperature sensor to be removed
	 */
	public void removeTempSensor(TemperatureSensor ts) {
		/* Your Task */
		tempSensors.remove(ts);
	}

	/**
	 * Removes the pressure sensor from the corresponding sensor list
	 * 
	 * @param ps pressure sensor to be removed
	 */
	public void removePressSensor(PressureSensor ps) {
		/* Your Task */
		pressureSensors.remove(ps);
	}

	/**
	 * Get the consensus of temperature measurements.
	 * 
	 * @return latest temperature measure
	 */
	public double getTemperature() {
		/* Your Task */
		double sum = 0;

		for (TemperatureSensor ts : tempSensors) {
			sum += ts.get();
		}

		return (sum / tempSensors.size());
	}

	/**
	 * Get the consensus of pressure measurements.
	 * 
	 * @return latest pressure measure
	 */
	public double getPressure() {
		/* Your Task */
		double sum = 0;
		for (PressureSensor ps : pressureSensors) {
			sum += ps.get();
		}

		return (sum / pressureSensors.size());
	}
}