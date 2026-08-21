package datastructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

/**
 * CircularQueueDSTest - Unit tests for Circular Queue
 */
public class CircularQueueDSTest {

    @Test
    public void testEnqueueAndDequeue() {
        CircularQueueDS<Integer> queue = new CircularQueueDS<>(5);

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        assertEquals(10, queue.dequeue());
        assertEquals(20, queue.dequeue());
        assertEquals(30, queue.dequeue());
    }

    @Test
    public void testWrapAround() {
        CircularQueueDS<Integer> queue = new CircularQueueDS<>(5);

        queue.enqueue(10);
        queue.enqueue(20);
        queue.dequeue();  // Front moves
        queue.enqueue(30);  // Should wrap around
        queue.enqueue(40);

        assertEquals(20, queue.dequeue());
        assertEquals(30, queue.dequeue());
        assertEquals(40, queue.dequeue());
    }

    @Test
    public void testIsFull() {
        CircularQueueDS<Integer> queue = new CircularQueueDS<>(3);

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        assertTrue(queue.isFull());
    }

    @Test
    public void testEnqueueFull() {
        CircularQueueDS<Integer> queue = new CircularQueueDS<>(3);

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        assertThrows(IllegalStateException.class, () -> {
            queue.enqueue(40);
        });
    }

    @Test
    public void testIsEmpty() {
        CircularQueueDS<Integer> queue = new CircularQueueDS<>(5);
        assertTrue(queue.isEmpty());

        queue.enqueue(10);
        assertFalse(queue.isEmpty());
    }

    @Test
    public void testPeek() {
        CircularQueueDS<Integer> queue = new CircularQueueDS<>(5);

        queue.enqueue(10);
        queue.enqueue(20);

        assertEquals(10, queue.peek());
        assertEquals(10, queue.peek());  // Peek doesn't remove
    }

    @Test
    public void testClear() {
        CircularQueueDS<Integer> queue = new CircularQueueDS<>(5);

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        queue.clear();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    public void testDequeueEmpty() {
        CircularQueueDS<Integer> queue = new CircularQueueDS<>(5);

        assertThrows(NoSuchElementException.class, () -> {
            queue.dequeue();
        });
    }
}
