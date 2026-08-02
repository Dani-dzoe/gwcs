package gh.edu.ug.wastesys.ds;

import java.util.NoSuchElementException;

public final class ArrayQueue<T> {
    private final DynamicArray<T> data = new DynamicArray<>();
    private int head;

    public void enqueue(T value) {
        data.insert(value);
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }
        T value = data.get(head);
        data.remove(head);
        return value;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return data.size() - head;
    }
}
