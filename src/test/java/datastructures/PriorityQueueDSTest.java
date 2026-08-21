package datastructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

/**
 * PriorityQueueDSTest - Unit tests for Priority Queue
 */
public class PriorityQueueDSTest {

    @Test
    public void testEnqueueAndDequeue() {
        PriorityQueueDS<Integer> pq = new PriorityQueueDS<>();

        pq.enqueue(50);
        pq.enqueue(30);
        pq.enqueue(70);
        pq.enqueue(20);

        assertEquals(20, pq.dequeue());
        assertEquals(30, pq.dequeue());
        assertEquals(50, pq.dequeue());
        assertEquals(70, pq.dequeue());
    }

    @Test
    public void testPeek() {
        PriorityQueueDS<Integer> pq = new PriorityQueueDS<>();

        pq.enqueue(50);
        pq.enqueue(30);
        pq.enqueue(70);

        assertEquals(30, pq.peek());
        assertEquals(30, pq.peek());  // Peek doesn't remove
        assertEquals(3, pq.size());
    }

    @Test
    public void testIsEmpty() {
        PriorityQueueDS<Integer> pq = new PriorityQueueDS<>();
        assertTrue(pq.isEmpty());

        pq.enqueue(50);
        assertFalse(pq.isEmpty());

        pq.dequeue();
        assertTrue(pq.isEmpty());
    }

    @Test
    public void testSize() {
        PriorityQueueDS<Integer> pq = new PriorityQueueDS<>();

        assertEquals(0, pq.size());

        pq.enqueue(50);
        assertEquals(1, pq.size());

        pq.enqueue(30);
        assertEquals(2, pq.size());
    }

    @Test
    public void testClear() {
        PriorityQueueDS<Integer> pq = new PriorityQueueDS<>();

        pq.enqueue(50);
        pq.enqueue(30);
        pq.enqueue(70);

        pq.clear();

        assertTrue(pq.isEmpty());
        assertEquals(0, pq.size());
    }

    @Test
    public void testDequeueEmpty() {
        PriorityQueueDS<Integer> pq = new PriorityQueueDS<>();

        assertThrows(NoSuchElementException.class, () -> {
            pq.dequeue();
        });
    }

    @Test
    public void testPeekEmpty() {
        PriorityQueueDS<Integer> pq = new PriorityQueueDS<>();

        assertThrows(NoSuchElementException.class, () -> {
            pq.peek();
        });
    }
}
