package scheduling;

import datastructures.DequeDS;
import model.WasteRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * DequeScheduler - Double-ended queue scheduling
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class DequeScheduler {
    private DequeDS<WasteRequest> deque;

    public DequeScheduler() {
        this.deque = new DequeDS<>();
    }

    /**
     * Add urgent request to front
     */
    public void addUrgent(WasteRequest request) {
        deque.addFront(request);
    }

    /**
     * Add normal request to back
     */
    public void addNormal(WasteRequest request) {
        deque.addRear(request);
    }

    /**
     * Get next request
     */
    public WasteRequest getNext() {
        if (deque.isEmpty()) {
            return null;
        }
        return deque.removeFront();
    }

    /**
     * Peek at next request
     */
    public WasteRequest peek() {
        if (deque.isEmpty()) {
            return null;
        }
        return deque.peekFront();
    }

    /**
     * Check if empty
     */
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    /**
     * Get size
     */
    public int size() {
        return deque.size();
    }

    /**
     * Clear deque
     */
    public void clear() {
        deque.clear();
    }
}
