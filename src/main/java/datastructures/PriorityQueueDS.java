package datastructures;

import java.util.NoSuchElementException;

/**
 * PriorityQueueDS - Priority queue using heap
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity:
 * - Enqueue: O(log n)
 * - Dequeue: O(log n)
 * - Peek: O(1)
 */
public class PriorityQueueDS<T extends Comparable<T>> {
    private Heap<T> heap;

    public PriorityQueueDS() {
        this.heap = new Heap<>();
    }

    /**
     * Add element with priority
     */
    public void enqueue(T value) {
        heap.insert(value);
    }

    /**
     * Remove and return highest priority element
     */
    public T dequeue() {
        return heap.extractMin();
    }

    /**
     * Peek at highest priority element
     */
    public T peek() {
        return heap.getMin();
    }

    /**
     * Check if empty
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Get size
     */
    public int size() {
        return heap.size();
    }

    /**
     * Clear queue
     */
    public void clear() {
        heap.clear();
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
