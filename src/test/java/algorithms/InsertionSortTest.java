package algorithms.sorting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class InsertionSortTest {

    @Test
    void shouldSortUnsortedArray() {
        int[] array = {5, 2, 9, 1, 5, 6};
        InsertionSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 5, 5, 6, 9}, array);
    }

    @Test
    void shouldHandleAlreadySortedArray() {
        int[] array = {1, 2, 3, 4, 5};
        InsertionSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }

    @Test
    void shouldHandleReverseSortedArray() {
        int[] array = {5, 4, 3, 2, 1};
        InsertionSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] array = {};
        InsertionSort.sort(array);
        assertArrayEquals(new int[]{}, array);
    }

    @Test
    void shouldHandleSingleElementArray() {
        int[] array = {7};
        InsertionSort.sort(array);
        assertArrayEquals(new int[]{7}, array);
    }

    @Test
    void shouldHandleDuplicateValues() {
        int[] array = {3, 1, 3, 2, 1};
        InsertionSort.sort(array);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3}, array);
    }
}