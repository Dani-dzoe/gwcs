package gh.edu.ug.wastesys.ds;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class SinglyLinkedList<T> implements Iterable<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public void addFirst(T value) {
        Node<T> node = new Node<>(value);
        node.next = head;
        head = node;
        if (tail == null) {
            tail = node;
        }
        size++;
    }

    public void addLast(T value) {
        Node<T> node = new Node<>(value);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public void insertAfter(T target, T value) {
        Node<T> current = findNode(target);
        if (current == null) {
            throw new IllegalArgumentException("target not found");
        }
        Node<T> node = new Node<>(value);
        node.next = current.next;
        current.next = node;
        if (tail == current) {
            tail = node;
        }
        size++;
    }

    public T remove(T value) {
        if (head == null) {
            throw new NoSuchElementException("list is empty");
        }
        if (equalsValue(head.value, value)) {
            return removeFirst();
        }
        Node<T> previous = head;
        Node<T> current = head.next;
        while (current != null) {
            if (equalsValue(current.value, value)) {
                previous.next = current.next;
                if (tail == current) {
                    tail = previous;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        throw new IllegalArgumentException("value not found");
    }

    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("list is empty");
        }
        T value = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return value;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> findNode(T target) {
        Node<T> current = head;
        while (current != null) {
            if (equalsValue(current.value, target)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private boolean equalsValue(T left, T right) {
        return left == null ? right == null : left.equals(right);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }

    private static final class Node<T> {
        private final T value;
        private Node<T> next;

        private Node(T value) {
            this.value = value;
        }
    }
}
