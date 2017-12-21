
public class Passenger {

	private float arrivalTime;
	
	/**
	 * creates a new Passenger with time they arrived in queue
	 * @param arrivalTime
	 */
	public Passenger(float arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public float getArrivalTime() {
		return arrivalTime;
	}
}
