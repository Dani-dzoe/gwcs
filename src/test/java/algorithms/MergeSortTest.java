package algorithms.sorting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {

    @Test
    public void testSortBasic() {
        Integer[] array = {64, 34, 25, 12, 22};
        MergeSort.sort(array);
        assertEquals(12, array[0]);
        assertEquals(22, array[1]);
        assertEquals(25, array[2]);
        assertEquals(34, array[3]);
        assertEquals(64, array[4]);
    }

    @Test
    public void testSortAlreadySorted() {
        Integer[] array = {1, 2, 3, 4, 5};
        MergeSort.sort(array);
        assertEquals(1, array[0]);
        assertEquals(5, array[4]);
    }

    @Test
    public void testSortReverseSorted() {
        Integer[] array = {5, 4, 3, 2, 1};
        MergeSort.sort(array);
        assertEquals(1, array[0]);
        assertEquals(5, array[4]);
    }

    @Test
    public void testSortWithDuplicates() {
        Integer[] array = {3, 1, 4, 1, 5, 9, 2, 6};
        MergeSort.sort(array);
        assertEquals(1, array[0]);
        assertEquals(1, array[1]);
        assertEquals(9, array[7]);
    }

    @Test
    public void testSortEmpty() {
        Integer[] array = {};
        MergeSort.sort(array);
        assertEquals(0, array.length);
    }

    @Test
    public void testSortSingleElement() {
        Integer[] array = {42};
        MergeSort.sort(array);
        assertEquals(42, array[0]);
    }

    @Test
    public void testSortLarge() {
        Integer[] array = new Integer[100];
        for (int i = 0; i < 100; i++) array[i] = (int)(Math.random() * 1000);
        MergeSort.sort(array);
        for (int i = 0; i < 99; i++) {
            assertTrue(array[i] <= array[i + 1]);
        }
    }
}
