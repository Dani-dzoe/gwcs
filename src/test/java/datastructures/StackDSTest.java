package datastructures;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

public class StackDSTest {
    private StackDS<Integer> stack;

    @BeforeEach
    public void setUp() {
        stack = new StackDS<>();
    }

    @Test
    public void testPushAndPop() {
        stack.push(10);
        stack.push(20);
        stack.push(30);
        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
    }

    @Test
    public void testPeek() {
        stack.push(10);
        stack.push(20);
        assertEquals(20, stack.peek());
        assertEquals(20, stack.peek());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty());
        stack.push(10);
        assertFalse(stack.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(0, stack.size());
        stack.push(10);
        assertEquals(1, stack.size());
    }

    @Test
    public void testClear() {
        stack.push(10);
        stack.push(20);
        stack.clear();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testPopEmpty() {
        assertThrows(NoSuchElementException.class, () -> stack.pop());
    }

    @Test
    public void testPeekEmpty() {
        assertThrows(NoSuchElementException.class, () -> stack.peek());
    }
}
