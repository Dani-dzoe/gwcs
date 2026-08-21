package scheduling;

import datastructures.QueueDS;
import model.WasteRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * FIFOScheduler - First-In-First-Out scheduling
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity:
 * - Enqueue: O(1)
 * - Dequeue: O(1)
 */
public class FIFOScheduler {
    private QueueDS<WasteRequest> queue;

    public FIFOScheduler() {
        this.queue = new QueueDS<>();
    }

    /**
     * Add request to queue
     */
    public void addRequest(WasteRequest request) {
        queue.enqueue(request);
    }

    /**
     * Get next request
     */
    public WasteRequest getNext() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.dequeue();
    }

    /**
     * Peek at next request
     */
    public WasteRequest peek() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.peek();
    }

    /**
     * Check if empty
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Get size
     */
    public int size() {
        return queue.size();
    }

    /**
     * Get all requests in order
     */
    public List<WasteRequest> getAll() {
        List<WasteRequest> result = new ArrayList<>();
        QueueDS<WasteRequest> temp = new QueueDS<>();

        while (!queue.isEmpty()) {
            WasteRequest req = queue.dequeue();
            result.add(req);
            temp.enqueue(req);
        }

        queue = temp;
        return result;
    }

    /**
     * Clear queue
     */
    public void clear() {
        queue.clear();
    }
}
