package gh.edu.ug.wastesys.ds;

import java.util.NoSuchElementException;

public final class CircularQueue<T> {
    private final Object[] elements;
    private int front;
    private int rear;
    private int size;

    public CircularQueue(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.elements = new Object[capacity];
    }

    public void enqueue(T value) {
        if (size == elements.length) {
            throw new IllegalStateException("queue is full");
        }
        elements[rear] = value;
        rear = (rear + 1) % elements.length;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("queue is empty");
        }
        T value = (T) elements[front];
        elements[front] = null;
        front = (front + 1) % elements.length;
        size--;
        return value;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
