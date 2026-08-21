package scheduling;

import datastructures.PriorityQueueDS;
import model.WasteRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * PriorityScheduler - Priority queue scheduling
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class PriorityScheduler {
    private PriorityQueueDS<WasteRequest> pq;

    public PriorityScheduler() {
        this.pq = new PriorityQueueDS<>();
    }

    /**
     * Add request based on priority
     */
    public void addRequest(WasteRequest request) {
        pq.enqueue(request);
    }

    /**
     * Get highest priority request
     */
    public WasteRequest getNext() {
        if (pq.isEmpty()) {
            return null;
        }
        return pq.dequeue();
    }

    /**
     * Peek at highest priority request
     */
    public WasteRequest peek() {
        if (pq.isEmpty()) {
            return null;
        }
        return pq.peek();
    }

    /**
     * Check if empty
     */
    public boolean isEmpty() {
        return pq.isEmpty();
    }

    /**
     * Get size
     */
    public int size() {
        return pq.size();
    }

    /**
     * Clear queue
     */
    public void clear() {
        pq.clear();
    }
}
