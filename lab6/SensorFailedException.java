package eecs2030.lab6;

/**
 * Custom exception thrown when a weather sensor fails to report a measurement.
 * It keeps a reference to the failed sensor.
 *
 */
public class SensorFailedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 
	 * Your Task: Declare required fields to have a functioning class
	 */
	WSensor s;

	public SensorFailedException(WSensor s) {
		super("Sensor Failure");
		/* Your Task */
		this.s = s;
	}

	/**
	 * Returns a reference to the failed sensor
	 * 
	 * @return a reference to the failed sensor
	 */
	public WSensor getSensor() {
		/* Your Task */
		return s;
	}
}
