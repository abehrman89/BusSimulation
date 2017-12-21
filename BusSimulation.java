
public class BusSimulation {

	private BusStop busStops = new BusStop();
	private Bus[] buses;
	
	public static void main (String args[]) {
		BusSimulation busSim = new BusSimulation();
		busSim.simulation();
	}
	
	public void simulation(){
		
		PassengerQueue stopCount;	//used when calculating average queue sizes
		PassengerQueue tempQueue;	//used to access queues for each bus stop
		
		float timeToAdd;	//time each passenger is added to passenger queues at bus stops
		int i, nextBus, stop, stopNum, busNum, busLoc;
		int interval = 0;	//used when calculating average queue sizes and current bus locations
		int tempAvg = 0;	//used when calculating average queue sizes
		int tempMax, tempMin;	//used when calculating maximum and minimum queue sizes
		int queueSize;
		
		int[][] averageQueueSize = new int[32][15];
		int[][] busLocation = new int[32][15];
		
		//used to keep distances between adjacent buses equal
		//comment out variable to see initial conditions
		boolean allNotBoarding = true;
		
		//creates and initializes 5 buses at stops 0, 3, 6, 9, 12
		buses = new Bus[5];
		for (i = 0; i < 5; i++) {
			buses[i] = new Bus(i);
			buses[i].setCurrentBusStop(i*3);
		}
		
		//adds person to queue at specified bus stops at start time (1)
		for (int initialAdd = 0; initialAdd < 15; initialAdd+=2) {
			tempQueue = busStops.stopQueue(initialAdd);
			tempQueue.person(1);
		}
		
		for (int clock = 1; clock < 28801; clock++) {
			
			//mean arrival time is 5 persons/min --> 5/60 = 1/12
			//creates random time since last person arrived at adds person to each queue
			timeToAdd = (float)(clock - (Math.random()*12));
			if (clock % 12 == 0) {
				busStops.addPassenger(timeToAdd);
			}
			
			//loops through each bus checking for status of isTraveling, isBoarding, and isWaiting
			for (i = 0; i < 5; i++) {

				/*
				 *isTraveling == true
				 */
				if (buses[i].getTraveling()) {
					if(buses[i].updateTravelTime()){	//if travelTime % 300 != 0, bus is still traveling, so move on to next bus
						continue;
					}
					else {
						if (i + 1 == 5) {	//determine which is the bus directly in front of the current bus
							nextBus = 0;
						}
						else {
							nextBus = i + 1;
						}
						if(buses[nextBus].getCurrentBusStop() == buses[i].getNextBusStop()) {	//if there's a bus at the next stop, set current bus to waiting
							buses[i].setWaiting(true);
							buses[i].setTraveling(false);
							buses[i].setBoarding(false);
						}
						else {
							buses[i].setNextBusStop(clock);
							stop = buses[i].getCurrentBusStop();	//get bus stop of current bus
							tempQueue = busStops.stopQueue(stop);	//get queue of current bus stop
							if (tempQueue.queueSize() == 0) {		//if queue is empty, set boarding to false
								buses[i].setBoarding(false);
								//buses[i].setTraveling(true);	//commented out for solution to adjacent distance condition
								buses[i].setWaiting(false);
							}
							else {
								buses[i].setBoarding(true);		//queue is not empty, set current bus to boarding
								buses[i].setTraveling(false);
								buses[i].setWaiting(false);

								buses[i].updateBoardTime();					//increment boarding time for passengers
								if (buses[i].getBoardTime() % 2 == 0){		//if boardingTime % 2 == 0, another passenger has boarded the bus/left the queue
									tempQueue.removePassenger();
								}
							}
						}
					}
				}
				else if (buses[i].getBoarding()) {
					stop = buses[i].getCurrentBusStop();	//get bus stop of current bus
					tempQueue = busStops.stopQueue(stop);	//get queue of current bus stop
					if (tempQueue.queueSize() == 0) {		//if queue is empty, set boarding to false
						buses[i].setBoarding(false);
						//buses[i].setTraveling(true);	//commented out for solution to adjacent distance condition
						buses[i].setWaiting(false);
					}
					else {
						buses[i].updateBoardTime();					//increment boarding time for passengers
						if (buses[i].getBoardTime() % 2 == 0){		//if boardingTime % 2 == 0, another passenger has boarded the bus/left the queue
							tempQueue.removePassenger();
						}
					}
				}
				else {					//buses[i].getIsWaiting() == true
					if (i + 1 == 5) {	//determine which bus is directly in front of the current bus
						nextBus = 0;
					}
					else {
						nextBus = i + 1;
					}
					
					if (buses[nextBus].getCurrentBusStop() == buses[i].getNextBusStop()) { 	//if there is still a bus boarding at next stop, move on to next bus
						buses[i].setTraveling(false);
						buses[i].setBoarding(false);		
					}
					else {
						buses[i].setNextBusStop(clock);				//current bus arrives at next bus stop
						stop = buses[i].getCurrentBusStop();	//get bus stop of current bus
						tempQueue = busStops.stopQueue(stop);	//get queue of current bus stop
						if (tempQueue.queueSize() == 0) {		//if queue is empty, set boarding to false
							buses[i].setBoarding(false);
							//buses[i].setTraveling(true);	//commented out for solution to adjacent distance condition
							buses[i].setWaiting(false);
						}
						else {
							buses[i].setBoarding(true);
							buses[i].setTraveling(false);
							buses[i].setWaiting(false);
							
							buses[i].updateBoardTime();					//increment boarding time for passengers
							if (buses[i].getBoardTime() % 2 == 0){		//if boardingTime % 2 == 0, another passenger has boarded the bus/left the queue
								tempQueue.removePassenger();
							}
						}
					}
				}
			}
			
			/*
			 * solution to adjacent bus distance condition
			 * check if any buses are still boarding passengers
			 * if any are still boarding, no buses can move
			 * if all buses are done boarding, all buses can start traveling to next stop
			 */
			//to see simulation without adjacent distance solution being comment block here
			for (i = 0; i < 5; i++) {
				if(buses[i].getBoarding()) {
					allNotBoarding = false;
				}
			}
			
			if (allNotBoarding) {
				for (i = 0; i < 5; i++) {
					buses[i].setTraveling(true);
				}
			}
			else {
				for (i = 0; i < 5; i++) {
					buses[i].setTraveling(false);
				}
			}
			//end comment block here to see simulation without adjacent distance solution
			
			//updating averageQueueSize and busLocation arrays
			//every 900 seconds, pull the sizes of each queue and current locations of each bus
			if (clock % 900 == 0) {	
				for (stopNum = 0; stopNum < 15; stopNum++) {
					stopCount = busStops.stopQueue(stopNum);
					queueSize = stopCount.queueSize();
					averageQueueSize[interval][stopNum] = queueSize;
				}
				
				for (busNum = 0; busNum < 5; busNum++) {
					busLocation[interval][busNum] = buses[busNum].getCurrentBusStop();
				}
				interval++;
			}
		}
		
		//print the averageQueueSize array
		System.out.println("Average Queue Size\n");
		for (stopNum = 0; stopNum < 15; stopNum++) {
			if (stopNum < 10) {
				System.out.print((stopNum) + "  | ");
			}
			else {
				System.out.print((stopNum) + " | ");
			}
			for (i = 0; i < 32; i++) {
				System.out.print(averageQueueSize[i][stopNum] + ", ");
				tempAvg += averageQueueSize[i][stopNum];
			}
			System.out.print("= ");
			System.out.print("");
			tempAvg = tempAvg / 32;
			System.out.print(tempAvg);
			System.out.println();
			tempAvg = 0;
		}
		System.out.println();
		
		//print the maximum queue size for each array
		System.out.println("Maximum Queue Sizes");
		for (stopNum = 0; stopNum < 15; stopNum++) {
			if (stopNum < 10) {
				System.out.print("Stop " + (stopNum) + "  | ");
			}
			else {
				System.out.print("Stop " + (stopNum) + " | ");
			}
			i = 0;
			tempMax = averageQueueSize[0][stopNum];
			for (i = 0; i < 32; i++) {
				if (averageQueueSize[i][stopNum] > tempMax) {
					tempMax = averageQueueSize[i][stopNum];
				}
			}
			System.out.println(tempMax);
		}
		System.out.println();
		
		//print the minimum queue size for each array
		System.out.println("Minimum Queue Sizes");
		for (stopNum = 0; stopNum < 15; stopNum++) {
			if (stopNum < 10) {
				System.out.print("Stop " + (stopNum) + "  | ");
			}
			else {
				System.out.print("Stop " + (stopNum) + " | ");
			}
			i = 0;
			tempMin = averageQueueSize[0][stopNum];
			for (i = 0; i < 32; i++) {
				if (averageQueueSize[i][stopNum] < tempMin) {
					tempMin = averageQueueSize[i][stopNum];
				}
			}
			System.out.println(tempMin);
		}
		System.out.println();
		
		//print the busLocation array
		System.out.println("Bus Stops");
		for (busLoc = 0; busLoc < 5; busLoc++) {
			System.out.print("Bus " + busLoc + " | ");
			for (i = 0; i < 32; i++) {
				System.out.print(busLocation[i][busLoc] + ", ");
			}
			System.out.println();
		}
	}
}
