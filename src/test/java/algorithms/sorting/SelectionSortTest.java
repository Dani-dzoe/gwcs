package algorithms.sorting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SelectionSortTest {

    @Test
    void shouldSortUnsortedArrayAscending() {
        Integer[] array = {5, 3, 8, 1, 9};
        SelectionSort.sort(array);
        assertArrayEquals(new Integer[]{1, 3, 5, 8, 9}, array);
    }

    @Test
    void shouldLeaveAlreadySortedArrayUnchanged() {
        Integer[] array = {1, 2, 3, 4, 5};
        SelectionSort.sort(array);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, array);
    }

    @Test
    void shouldSortReverseSortedArray() {
        Integer[] array = {9, 7, 5, 3, 1};
        SelectionSort.sort(array);
        assertArrayEquals(new Integer[]{1, 3, 5, 7, 9}, array);
    }

    @Test
    void shouldHandleEmptyArray() {
        Integer[] array = {};
        SelectionSort.sort(array);
        assertArrayEquals(new Integer[]{}, array);
    }

    @Test
    void shouldHandleSingleElementArray() {
        Integer[] array = {42};
        SelectionSort.sort(array);
        assertArrayEquals(new Integer[]{42}, array);
    }

    @Test
    void shouldHandleArrayWithDuplicates() {
        Integer[] array = {4, 2, 4, 1, 4};
        SelectionSort.sort(array);
        assertArrayEquals(new Integer[]{1, 2, 4, 4, 4}, array);
    }

    @Test
    void shouldSortArrayOfStrings() {
        String[] array = {"banana", "apple", "date", "cherry"};
        SelectionSort.sort(array);
        assertArrayEquals(new String[]{"apple", "banana", "cherry", "date"}, array);
    }

    @Test
    void shouldThrowExceptionForNullArray() {
        assertThrows(IllegalArgumentException.class,
                () -> SelectionSort.sort(null));
    }
}
