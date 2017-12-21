
public class BusStop {

	/**
	 * array of 15 passengers queues, 1 for each bus stop
	 */
	PassengerQueue[] p_stop = new PassengerQueue[15];
	
	/**
	 * assigns passenger queue to each bus stop
	 */
	public BusStop() {
		for (int i = 0; i < 15; i++)
			p_stop[i] = new PassengerQueue(i);
	}
	
	/**
	 * @param i
	 * @return queue size of bus stop i
	 */
	public int getStopQueueSize(int i) {
		return p_stop[i].queueSize();
	}
	
	/**
	 * adds another person to each queue at 'time' by calling .person(time) from PassengerQueue
	 * @param time
	 */
	public void addPassenger(float time) {
		for (int i = 0; i < 15; i++) {
			p_stop[i].person(time);
		}
	}
	
	/**
	 * get passenger queue associated with bus stop indicated by stopNum param
	 * @param stopNum
	 * @return p_stop[stopNum]
	 */
	public PassengerQueue stopQueue(int stopNum) {
		return p_stop[stopNum];
	}
}
