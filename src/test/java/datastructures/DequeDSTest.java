package datastructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

/**
 * DequeDSTest - Unit tests for Deque
 */
public class DequeDSTest {

    @Test
    public void testAddFrontAndRemoveFront() {
        DequeDS<Integer> deque = new DequeDS<>();

        deque.addFront(10);
        deque.addFront(20);
        deque.addFront(30);

        assertEquals(30, deque.removeFront());
        assertEquals(20, deque.removeFront());
        assertEquals(10, deque.removeFront());
    }

    @Test
    public void testAddRearAndRemoveRear() {
        DequeDS<Integer> deque = new DequeDS<>();

        deque.addRear(10);
        deque.addRear(20);
        deque.addRear(30);

        assertEquals(30, deque.removeRear());
        assertEquals(20, deque.removeRear());
        assertEquals(10, deque.removeRear());
    }

    @Test
    public void testMixedOperations() {
        DequeDS<Integer> deque = new DequeDS<>();

        deque.addFront(10);
        deque.addRear(20);
        deque.addFront(5);
        deque.addRear(25);

        assertEquals(5, deque.removeFront());
        assertEquals(10, deque.removeFront());
        assertEquals(20, deque.removeFront());
        assertEquals(25, deque.removeFront());
    }

    @Test
    public void testPeekFront() {
        DequeDS<Integer> deque = new DequeDS<>();

        deque.addFront(10);
        deque.addRear(20);

        assertEquals(10, deque.peekFront());
        assertEquals(10, deque.peekFront());  // Doesn't remove
    }

    @Test
    public void testPeekRear() {
        DequeDS<Integer> deque = new DequeDS<>();

        deque.addFront(10);
        deque.addRear(20);

        assertEquals(20, deque.peekRear());
        assertEquals(20, deque.peekRear());  // Doesn't remove
    }

    @Test
    public void testIsEmpty() {
        DequeDS<Integer> deque = new DequeDS<>();
        assertTrue(deque.isEmpty());

        deque.addFront(10);
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testSize() {
        DequeDS<Integer> deque = new DequeDS<>();

        assertEquals(0, deque.size());

        deque.addFront(10);
        assertEquals(1, deque.size());

        deque.addRear(20);
        assertEquals(2, deque.size());
    }

    @Test
    public void testClear() {
        DequeDS<Integer> deque = new DequeDS<>();

        deque.addFront(10);
        deque.addRear(20);
        deque.addFront(5);

        deque.clear();

        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());
    }

    @Test
    public void testRemoveFrontEmpty() {
        DequeDS<Integer> deque = new DequeDS<>();

        assertThrows(NoSuchElementException.class, () -> {
            deque.removeFront();
        });
    }

    @Test
    public void testRemoveRearEmpty() {
        DequeDS<Integer> deque = new DequeDS<>();

        assertThrows(NoSuchElementException.class, () -> {
            deque.removeRear();
        });
    }
}
