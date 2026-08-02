package gh.edu.ug.wastesys.ds;

import java.util.NoSuchElementException;

public final class Deque<T> {
    private final CircularQueue<T> frontBuffer;
    private final CircularQueue<T> rearBuffer;

    public Deque(int capacity) {
        this.frontBuffer = new CircularQueue<>(capacity);
        this.rearBuffer = new CircularQueue<>(capacity);
    }

    public void addFront(T value) {
        rearBuffer.enqueue(value);
    }

    public void addRear(T value) {
        frontBuffer.enqueue(value);
    }

    public T removeFront() {
        if (!frontBuffer.isEmpty()) {
            return frontBuffer.dequeue();
        }
        if (!rearBuffer.isEmpty()) {
            return rearBuffer.dequeue();
        }
        throw new NoSuchElementException("deque is empty");
    }

    public T removeRear() {
        if (!rearBuffer.isEmpty()) {
            return rearBuffer.dequeue();
        }
        if (!frontBuffer.isEmpty()) {
            return frontBuffer.dequeue();
        }
        throw new NoSuchElementException("deque is empty");
    }
}
