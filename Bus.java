
public class Bus {

	private int busNum, currentBusStop, arrives;
	private int travelTime = 0;
	private int boardTime = 0;
	private Boolean waiting, traveling, boarding;
	
	/**
	 * initializes buses with its number (busNum)
	 * all boolean variables set to false
	 * arrives = time when bus arrives at next bus stop
	 * @param busNum
	 */
	public Bus (int busNum) {
		this.busNum = busNum;
		this.arrives = 0;
		this.waiting = false;
		this.traveling = false;
		this.boarding = true;
	}
	
	public int getBusNum() {
		return busNum;
	}
	
	public void setCurrentBusStop (int currentBusStop) {
		this.currentBusStop = currentBusStop;
	}
	
	public int getCurrentBusStop() {
		return currentBusStop;
	}
	
	/**
	 * used for determining whether a passenger has boarded
	 */
	public void updateBoardTime() {
		boardTime += 1;
	}
	
	public int getBoardTime() {
		return boardTime;
	}
	
	public int getTravelTime() {
		return travelTime;
	}
	
	/**
	 * increments bus travel time between stops
	 * if time == 300, bus has arrived at stop and is no longer traveling
	 * @return isTraveling
	 */
	public Boolean updateTravelTime() {
		travelTime += 1;
		if (travelTime == 300) {
			traveling = false;
			travelTime = 0;
		}
		return traveling;
	}
	
	/**
	 * bus has arrived at next stop, so update bus stop
	 * set arrival time at next stop
	 */
	public void setNextBusStop(int arrives) {
		if (currentBusStop == 14) {
			currentBusStop = 0;
		} 
		else {
			currentBusStop += 1;
		}
		this.arrives = arrives;
	}
	
	public int getNextBusStop() {
		if (currentBusStop == 14) {
			return 0;
		}
		return currentBusStop + 1;
	}
	
	/**
	 * returns most recent bus stop arrival time
	 * @return arrives
	 */
	public int getArrival() {
		return this.arrives;
	}
	
	/**
	 * if bus has arrived at next bus stop but another bus is still boarding passengers,
	 * the arriving bus must wait (since buses cannot pass each other)
	 * @param waiting
	 */
	public void setWaiting (Boolean waiting) {
		this.waiting = waiting;
	}
	
	public Boolean getWaiting() {
		return waiting;
	}
	
	/**
	 * variable determining if bus is currently traveling between bus stops
	 * @param boolean traveling
	 */
	public void setTraveling (Boolean traveling) {
		this.traveling = traveling;
	}
	
	public Boolean getTraveling() {
		return traveling;
	}
	
	/**
	 * variable determining if bus is currently boarding passengers
	 * @param boarding
	 */
	public void setBoarding (Boolean boarding) {
		this.boarding = boarding;
	}
	
	public Boolean getBoarding() {
		return boarding;
	}
}
