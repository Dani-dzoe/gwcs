package datastructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {

    @Test
    void shouldAddElementToEmptyArray() {
        DynamicArray<Integer> array = new DynamicArray<>();
        array.add(10);
        assertEquals(1, array.size());
        assertEquals(10, array.get(0));
    }

    @Test
    void shouldResizeWhenExceedingCapacity() {
        DynamicArray<String> array = new DynamicArray<>();
        for (int i = 0; i < 15; i++) {
            array.add("Item " + i);
        }
        assertEquals(15, array.size());
        assertEquals("Item 14", array.get(14));
    }

    @Test
    void shouldThrowExceptionWhenGettingInvalidIndex() {
        DynamicArray<Double> array = new DynamicArray<>();
        array.add(1.0);
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(-1));
    }

    @Test
    void shouldInsertElementAtIndex() {
        DynamicArray<Character> array = new DynamicArray<>();
        array.add('a');
        array.add('c');
        array.add(1, 'b');
        assertEquals(3, array.size());
        assertEquals('b', array.get(1));
    }

    @Test
    void shouldRemoveElementAtIndex() {
        DynamicArray<Integer> array = new DynamicArray<>();
        array.add(10);
        array.add(20);
        array.add(30);
        array.remove(1);
        assertEquals(2, array.size());
        assertEquals(30, array.get(1));
    }

    @Test
    void shouldCheckContainsCorrectly() {
        DynamicArray<String> array = new DynamicArray<>();
        array.add("Apple");
        array.add("Banana");
        assertTrue(array.contains("Apple"));
        assertFalse(array.contains("Orange"));
    }
}
