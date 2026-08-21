package datastructures;

import java.util.NoSuchElementException;

/**
 * StackDS - Custom stack implementation using linked list
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class StackDS<T> {
    private Node<T> top;
    private int size;

    public StackDS() {
        this.top = null;
        this.size = 0;
    }

    public void push(T value) {
        Node<T> node = new Node<>(value);
        node.next = top;
        top = node;
        size++;
    }

    public T pop() {
        if (top == null) {
            throw new NoSuchElementException("Stack is empty");
        }
        T value = top.data;
        top = top.next;
        size--;
        return value;
    }

    public T peek() {
        if (top == null) {
            throw new NoSuchElementException("Stack is empty");
        }
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        top = null;
        size = 0;
    }
}
