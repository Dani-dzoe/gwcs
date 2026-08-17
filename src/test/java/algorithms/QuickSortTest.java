package algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Comparator;
import algorithms.sorting.QuickSort; 

public class QuickSortTest {

    @Test
    void testNormalArray() {
        Integer[] arr = { 30, 10, 50, 20, 40 };
        QuickSort.sort(arr, Comparator.naturalOrder());
        assertArrayEquals(new Integer[]{ 10, 20, 30, 40, 50 }, arr);
    }

    @Test
    void testSortedArray() {
        Integer[] sorted = { 1, 2, 3, 4, 5 };
        QuickSort.sort(sorted, Comparator.naturalOrder());
        assertArrayEquals(new Integer[]{ 1, 2, 3, 4, 5 }, sorted);
    }

    @Test
    void testDuplicates() {
        Integer[] duplicates = { 4, 1, 4, 2, 1 };
        QuickSort.sort(duplicates, Comparator.naturalOrder());
        assertArrayEquals(new Integer[]{ 1, 1, 2, 4, 4 }, duplicates);
    }
}