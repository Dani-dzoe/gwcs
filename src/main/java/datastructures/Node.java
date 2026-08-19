package datastructures;

/**
 * Basic node for our doubly linked list.
 */
public class Node<T> {
    public T data;
    public Node<T> prev; // points back
    public Node<T> next; // points forward

    public Node(T data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}
