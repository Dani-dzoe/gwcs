package algorithms.searching;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinarySearchTest {

    @Test
    void shouldFindElementInMiddleOfArray() {
        int[] array = {1, 3, 5, 7, 9, 11};
        assertEquals(3, BinarySearch.search(array, 7));
    }

    @Test
    void shouldFindElementAtStartOfArray() {
        int[] array = {2, 4, 6, 8};
        assertEquals(0, BinarySearch.search(array, 2));
    }

    @Test
    void shouldFindElementAtEndOfArray() {
        int[] array = {2, 4, 6, 8};
        assertEquals(3, BinarySearch.search(array, 8));
    }

    @Test
    void shouldReturnNegativeOneWhenElementNotFound() {
        int[] array = {1, 3, 5, 7};
        assertEquals(-1, BinarySearch.search(array, 4));
    }

    @Test
    void shouldReturnNegativeOneForEmptyArray() {
        int[] array = {};
        assertEquals(-1, BinarySearch.search(array, 5));
    }

    @Test
    void shouldFindElementInSingleElementArray() {
        int[] array = {42};
        assertEquals(0, BinarySearch.search(array, 42));
    }

    @Test
    void shouldReturnNegativeOneForSingleElementArrayMiss() {
        int[] array = {42};
        assertEquals(-1, BinarySearch.search(array, 7));
    }

    @Test
    void shouldFindSmallestElementInLargerArray() {
        int[] array = {1, 3, 5, 7, 9, 11, 13, 15};
        assertEquals(0, BinarySearch.search(array, 1));
    }

    @Test
    void shouldFindLargestElementInLargerArray() {
        int[] array = {1, 3, 5, 7, 9, 11, 13, 15};
        assertEquals(7, BinarySearch.search(array, 15));
    }
}
