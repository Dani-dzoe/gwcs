package datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

public class QueueDSTest {
    private QueueDS<Integer> queue;

    @BeforeEach
    public void setUp() {
        queue = new QueueDS<>();
    }

    @Test
    public void testEnqueueAndDequeue() {
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        assertEquals(10, queue.dequeue());
        assertEquals(20, queue.dequeue());
        assertEquals(30, queue.dequeue());
    }

    @Test
    public void testFIFO() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
    }

    @Test
    public void testPeek() {
        queue.enqueue(10);
        queue.enqueue(20);
        assertEquals(10, queue.peek());
        assertEquals(10, queue.peek());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.enqueue(10);
        assertFalse(queue.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(0, queue.size());
        queue.enqueue(10);
        assertEquals(1, queue.size());
    }

    @Test
    public void testClear() {
        queue.enqueue(10);
        queue.enqueue(20);
        queue.clear();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testDequeueEmpty() {
        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
    }

    @Test
    public void testPeekEmpty() {
        assertThrows(NoSuchElementException.class, () -> queue.peek());
    }
}
