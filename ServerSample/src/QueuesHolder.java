// This class is a container for different queues with gyroscope and accelerometer data
// Different threads can use this container therefore its methods are synchronized

import java.util.ArrayList;
import java.util.List;

public class QueuesHolder {
	public QueuesHolder( Logger _logger ) {
		logger = _logger;
		queuesList = new ArrayList<GyroDataQueue>();
	}
	
	public synchronized GyroDataQueue AddNewQueue() {
		int indexOfAddedQueue = -1;
		GyroDataQueue newQueue = new GyroDataQueue();		
		if( queuesList != null && newQueue != null ) {
			queuesList.add(newQueue);
			indexOfAddedQueue = queuesList.size() - 1;
		} 
		if( indexOfAddedQueue < 0 ) {
			return null;
		} else {
			return queuesList.get(indexOfAddedQueue);
		}
	}
	
	public synchronized void PushDataToQueues(int x, int y, int z) {
		if(queuesList != null) {
			try {
				for( int i = 0; i < queuesList.size(); ++i ) {
					queuesList.get(i).Offer(x, y, z);
				}
			} catch( Exception e ) {
				logger.WriteLine(e.getMessage(), GuiMainThread.class.getName(), "PushDataToQueues" );
			}
		}
	}

	
	// -------- PRIVATE ---------------------
	private List<GyroDataQueue> queuesList = null;
	private Logger logger;
}
