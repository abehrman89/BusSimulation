import java.util.*;
public class PassengerQueue {

	private Queue<Passenger> queue;
	private int queueNum;
	
	/**
	 * initializes passenger queues for bus stops
	 * each queue initialized as a linked list
	 * @param queueNum
	 */
	public PassengerQueue(int queueNum) {
		this.queueNum = queueNum;
		queue = new LinkedList <Passenger>();
	}
	
	public int getQueueNum() {
		return queueNum;
	}
	
	public int queueSize(){
		return queue.size();
	}
	
	/**
	 * adds new passenger to queue at 'time'
	 * @param time
	 */
	public void person(float time) {
		queue.add(new Passenger(time));
	}
	
	public void removePassenger() {
		queue.remove();
	}
}
