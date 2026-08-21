package datastructures;

import java.util.NoSuchElementException;

/**
 * DequeDS - Double-ended queue implementation
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity:
 * - Add/Remove Front/Back: O(1)
 */
public class DequeDS<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    public DequeDS() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /**
     * Add to front
     */
    public void addFront(T value) {
        Node<T> node = new Node<>(value);

        if (front == null) {
            front = rear = node;
        } else {
            node.next = front;
            front.prev = node;
            front = node;
        }

        size++;
    }

    /**
     * Add to rear
     */
    public void addRear(T value) {
        Node<T> node = new Node<>(value);

        if (rear == null) {
            front = rear = node;
        } else {
            rear.next = node;
            node.prev = rear;
            rear = node;
        }

        size++;
    }

    /**
     * Remove from front
     */
    public T removeFront() {
        if (front == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        T value = front.data;

        if (front == rear) {
            front = rear = null;
        } else {
            front = front.next;
            front.prev = null;
        }

        size--;
        return value;
    }

    /**
     * Remove from rear
     */
    public T removeRear() {
        if (rear == null) {
            throw new NoSuchElementException("Deque is empty");
        }

        T value = rear.data;

        if (front == rear) {
            front = rear = null;
        } else {
            rear = rear.prev;
            rear.next = null;
        }

        size--;
        return value;
    }

    /**
     * Peek at front
     */
    public T peekFront() {
        if (front == null) {
            throw new NoSuchElementException("Deque is empty");
        }
        return front.data;
    }

    /**
     * Peek at rear
     */
    public T peekRear() {
        if (rear == null) {
            throw new NoSuchElementException("Deque is empty");
        }
        return rear.data;
    }

    /**
     * Check if empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Get size
     */
    public int size() {
        return size;
    }

    /**
     * Clear deque
     */
    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = front;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
