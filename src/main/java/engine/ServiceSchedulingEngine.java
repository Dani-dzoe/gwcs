package engine;

import model.ServiceRequest; 
import algorithms.sorting.QuickSort;
import java.util.*; 

public class ServiceSchedulingEngine {

    private Queue<ServiceRequest> incomingQueue;

    public ServiceSchedulingEngine() {
        this.incomingQueue = new LinkedList<>();
    }

    // Add incoming request to FIFO queue
    public void receiveRequest(ServiceRequest request) {
        incomingQueue.add(request);
    }

    // Convert queue to array, sort by priority using QuickSort, and return dispatch order
    public ServiceRequest[] generateDispatchSchedule() {
        ServiceRequest[] requests = incomingQueue.toArray(new ServiceRequest[0]);

        // Comparator: Higher priority levels come first
        Comparator<ServiceRequest> priorityComparator = (r1, r2) -> 
            Integer.compare(r2.getPriorityLevel(), r1.getPriorityLevel());

        // Sort using your custom QuickSort implementation
        QuickSort.sort(requests, priorityComparator);

        return requests;
    }

    
}
