package scheduling;

import datastructures.CircularQueueDS;
import model.WasteRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * CircularScheduler - Circular queue scheduling
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class CircularScheduler {
    private CircularQueueDS<WasteRequest> queue;

    public CircularScheduler(int capacity) {
        this.queue = new CircularQueueDS<>(capacity);
    }

    /**
     * Add request to queue
     */
    public boolean addRequest(WasteRequest request) {
        if (queue.isFull()) {
            return false;
        }
        queue.enqueue(request);
        return true;
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
     * Check if full
     */
    public boolean isFull() {
        return queue.isFull();
    }

    /**
     * Get size
     */
    public int size() {
        return queue.size();
    }

    /**
     * Get capacity
     */
    public int capacity() {
        return queue.capacity();
    }

    /**
     * Clear queue
     */
    public void clear() {
        queue.clear();
    }
}
