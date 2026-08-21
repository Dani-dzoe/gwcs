package datastructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

/**
 * HeapTest - Unit tests for Min-Heap
 */
public class HeapTest {

    @Test
    public void testInsertAndGetMin() {
        Heap<Integer> heap = new Heap<>();

        heap.insert(50);
        heap.insert(30);
        heap.insert(70);
        heap.insert(20);
        heap.insert(40);

        assertEquals(20, heap.getMin());
    }

    @Test
    public void testExtractMin() {
        Heap<Integer> heap = new Heap<>();

        heap.insert(50);
        heap.insert(30);
        heap.insert(70);
        heap.insert(20);
        heap.insert(40);

        assertEquals(20, heap.extractMin());
        assertEquals(30, heap.extractMin());
        assertEquals(40, heap.extractMin());
        assertEquals(50, heap.extractMin());
        assertEquals(70, heap.extractMin());
    }

    @Test
    public void testIsEmpty() {
        Heap<Integer> heap = new Heap<>();
        assertTrue(heap.isEmpty());

        heap.insert(50);
        assertFalse(heap.isEmpty());

        heap.extractMin();
        assertTrue(heap.isEmpty());
    }

    @Test
    public void testSize() {
        Heap<Integer> heap = new Heap<>();

        assertEquals(0, heap.size());

        heap.insert(50);
        assertEquals(1, heap.size());

        heap.insert(30);
        assertEquals(2, heap.size());
    }

    @Test
    public void testClear() {
        Heap<Integer> heap = new Heap<>();

        heap.insert(50);
        heap.insert(30);
        heap.insert(70);

        heap.clear();

        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
    }

    @Test
    public void testExtractMinEmpty() {
        Heap<Integer> heap = new Heap<>();

        assertThrows(NoSuchElementException.class, () -> {
            heap.extractMin();
        });
    }

    @Test
    public void testGetMinEmpty() {
        Heap<Integer> heap = new Heap<>();

        assertThrows(NoSuchElementException.class, () -> {
            heap.getMin();
        });
    }

    @Test
    public void testHeapSort() {
        Heap<Integer> heap = new Heap<>();

        heap.insert(50);
        heap.insert(30);
        heap.insert(70);
        heap.insert(20);
        heap.insert(40);

        java.util.List<Integer> sorted = heap.heapSort();

        assertEquals(5, sorted.size());
        assertEquals(20, sorted.get(0));
        assertEquals(30, sorted.get(1));
        assertEquals(40, sorted.get(2));
        assertEquals(50, sorted.get(3));
        assertEquals(70, sorted.get(4));
    }

    @Test
    public void testLargeInsert() {
        Heap<Integer> heap = new Heap<>();

        for (int i = 100; i > 0; i--) {
            heap.insert(i);
        }

        assertEquals(100, heap.size());
        assertEquals(1, heap.getMin());
    }
}
