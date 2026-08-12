package datastructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LinkedListDSTest {

    @Test
    void shouldAddFirstElement() {
        LinkedListDS<Integer> list = new LinkedListDS<>();
        list.addFirst(10);
        assertEquals(1, list.size());
        assertEquals(10, list.getFirst());
        assertEquals(10, list.getLast());
    }

    @Test
    void shouldAddLastElement() {
        LinkedListDS<String> list = new LinkedListDS<>();
        list.addLast("A");
        list.addLast("B");
        assertEquals(2, list.size());
        assertEquals("A", list.getFirst());
        assertEquals("B", list.getLast());
    }

    @Test
    void shouldThrowExceptionWhenRemovingFromEmptyList() {
        LinkedListDS<Double> list = new LinkedListDS<>();
        assertThrows(IllegalStateException.class, list::removeFirst);
        assertThrows(IllegalStateException.class, list::removeLast);
    }

    @Test
    void shouldRemoveElementCorrectly() {
        LinkedListDS<Character> list = new LinkedListDS<>();
        list.addLast('x');
        list.addLast('y');
        list.addLast('z');
        list.remove('y');
        assertEquals(2, list.size());
        assertEquals('x', list.getFirst());
        assertEquals('z', list.getLast());
    }

    @Test
    void shouldClearList() {
        LinkedListDS<Integer> list = new LinkedListDS<>();
        list.addLast(1);
        list.addLast(2);
        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertThrows(IllegalStateException.class, list::getFirst);
    }

    @Test
    void shouldCheckContainsCorrectly() {
        LinkedListDS<String> list = new LinkedListDS<>();
        list.addLast("Apple");
        list.addLast("Banana");
        assertTrue(list.contains("Apple"));
        assertFalse(list.contains("Orange"));
    }
}
