package datastructures;

import java.util.NoSuchElementException;

/**
 * QueueDS - Custom queue implementation using linked list
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class QueueDS<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    public QueueDS() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    public void enqueue(T value) {
        Node<T> node = new Node<>(value);
        if (rear == null) {
            front = rear = node;
        } else {
            rear.next = node;
            rear = node;
        }
        size++;
    }

    public T dequeue() {
        if (front == null) {
            throw new NoSuchElementException("Queue is empty");
        }
        T value = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return value;
    }

    public T peek() {
        if (front == null) {
            throw new NoSuchElementException("Queue is empty");
        }
        return front.data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }
}
