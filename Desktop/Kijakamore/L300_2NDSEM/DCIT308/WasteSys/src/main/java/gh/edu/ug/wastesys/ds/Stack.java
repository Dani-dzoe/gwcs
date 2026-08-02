package gh.edu.ug.wastesys.ds;

import java.util.NoSuchElementException;

public final class Stack<T> {
    private final SinglyLinkedList<T> list = new SinglyLinkedList<>();

    public void push(T value) {
        list.addFirst(value);
    }

    public T pop() {
        return list.removeFirst();
    }

    public T peek() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("stack is empty");
        }
        return list.iterator().next();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
