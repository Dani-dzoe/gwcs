package datastructures;

import java.util.NoSuchElementException;

/**
 * CircularQueueDS - Circular queue implementation using array
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity:
 * - Enqueue: O(1)
 * - Dequeue: O(1)
 * - Peek: O(1)
 */
public class CircularQueueDS<T> {
    private T[] array;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public CircularQueueDS(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.array = (T[]) new Object[capacity];
        this.capacity = capacity;
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    /**
     * Add element to back
     */
    public void enqueue(T value) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }

        rear = (rear + 1) % capacity;
        array[rear] = value;
        size++;
    }

    /**
     * Remove element from front
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        T value = array[front];
        array[front] = null;
        front = (front + 1) % capacity;
        size--;

        return value;
    }

    /**
     * Peek at front element
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return array[front];
    }

    /**
     * Check if empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Check if full
     */
    public boolean isFull() {
        return size == capacity;
    }

    /**
     * Get size
     */
    public int size() {
        return size;
    }

    /**
     * Get capacity
     */
    public int capacity() {
        return capacity;
    }

    /**
     * Clear queue
     */
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            array[i] = null;
        }
        front = 0;
        rear = -1;
        size = 0;
    }

    /**
     * Get front index
     */
    public int getFront() {
        return front;
    }

    /**
     * Get rear index
     */
    public int getRear() {
        return rear;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            sb.append(array[index]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
