package algorithms.sorting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * SelectionSortTest - Unit tests for SelectionSort
 */
public class SelectionSortTest {

    @Test
    public void testSortBasic() {
        Integer[] array = {64, 34, 25, 12};

        SelectionSort.sort(array);

        assertEquals(12, array[0]);
        assertEquals(25, array[1]);
        assertEquals(34, array[2]);
        assertEquals(64, array[3]);
    }

    @Test
    public void testSortAlreadySorted() {
        Integer[] array = {1, 2, 3, 4};

        SelectionSort.sort(array);

        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
    }

    @Test
    public void testSortReverse() {
        Integer[] array = {4, 3, 2, 1};

        SelectionSort.sort(array);

        assertEquals(1, array[0]);
        assertEquals(2, array[1]);
        assertEquals(3, array[2]);
        assertEquals(4, array[3]);
    }

    @Test
    public void testSortWithCount() {
        Integer[] array = {5, 3, 8, 1};

        int comparisons = SelectionSort.sortWithCount(array);

        assertTrue(comparisons > 0);
        assertEquals(1, array[0]);
        assertEquals(8, array[3]);
    }

    @Test
    public void testSortDescending() {
        Integer[] array = {1, 5, 3, 2};

        SelectionSort.sortDescending(array);

        assertEquals(5, array[0]);
        assertEquals(3, array[1]);
        assertEquals(2, array[2]);
        assertEquals(1, array[3]);
    }

    @Test
    public void testSortEmpty() {
        Integer[] array = {};

        SelectionSort.sort(array);

        assertEquals(0, array.length);
    }

    @Test
    public void testSortSingle() {
        Integer[] array = {42};

        SelectionSort.sort(array);

        assertEquals(42, array[0]);
    }
}
