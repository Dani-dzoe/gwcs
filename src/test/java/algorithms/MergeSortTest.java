package algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Comparator;
import algorithms.sorting.MergeSort;
public class MergeSortTest {

    @Test
    void testNormalArray() {
        Integer[] arr = { 45, 12, 89, 23, 7 };
        MergeSort.sort(arr, Comparator.naturalOrder());
        assertArrayEquals(new Integer[]{ 7, 12, 23, 45, 89 }, arr);
    }

    @Test
    void testEmptyAndSingleElement() {
        Integer[] empty = {};
        MergeSort.sort(empty, Comparator.naturalOrder());
        assertEquals(0, empty.length);

        Integer[] single = { 99 };
        MergeSort.sort(single, Comparator.naturalOrder());
        assertEquals(99, single[0]);
    }

    @Test
    void testDuplicates() {
        Integer[] duplicates = { 5, 2, 5, 1, 2 };
        MergeSort.sort(duplicates, Comparator.naturalOrder());
        assertArrayEquals(new Integer[]{ 1, 2, 2, 5, 5 }, duplicates);
    }
}