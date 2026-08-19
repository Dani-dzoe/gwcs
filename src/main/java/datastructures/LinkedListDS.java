package datastructures;

/**
 * A standard doubly linked list.
 * Built from scratch so we don't cheat by using java.util.LinkedList!
 *
 * @param <T> what we're putting in the list
 */
public class LinkedListDS<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedListDS() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Drop a node right at the front (O(1))
    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = tail = newNode; // first item goes here
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // Stick a node at the very end (O(1) because we keep a tail pointer)
    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = tail = newNode; // list was empty anyway
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    // Pop the first item off
    public void removeFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty, nothing to remove");
        }
        
        if (head == tail) {
            head = tail = null; // only one item left
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
    }

    // Pop the last item off
    public void removeLast() {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty, nothing to remove");
        }
        
        if (head == tail) {
            head = tail = null; 
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
    }

    // Search for a value and remove the first match we see
    public void remove(T value) {
        Node<T> current = head;
        
        while (current != null) {
            // Check for nulls safely before calling .equals
            if ((value == null && current.data == null) || (value != null && value.equals(current.data))) {
                
                // Edge cases: is it at the start or end?
                if (current == head) {
                    removeFirst();
                } else if (current == tail) {
                    removeLast();
                } else {
                    // It's in the middle, snip it out
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    size--;
                }
                return; // done!
            }
            current = current.next;
        }
    }

    public T getFirst() {
        if (isEmpty()) throw new IllegalStateException("List is empty");
        return head.data;
    }

    public T getLast() {
        if (isEmpty()) throw new IllegalStateException("List is empty");
        return tail.data;
    }

    // Scan through to see if we have this value
    public boolean contains(T value) {
        Node<T> current = head;
        while (current != null) {
            if ((value == null && current.data == null) || (value != null && value.equals(current.data))) {
                return true;
            }
            current = current.next; // keep moving forward
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Reset everything
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
}
