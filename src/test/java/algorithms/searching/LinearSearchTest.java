package algorithms.searching;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LinearSearchTest {

    @Test
    void shouldFindElementInMiddleOfArray() {
        Integer[] array = {5, 3, 8, 1, 9};
        assertEquals(2, LinearSearch.search(array, 8));
    }

    @Test
    void shouldFindElementAtStartOfArray() {
        Integer[] array = {5, 3, 8, 1, 9};
        assertEquals(0, LinearSearch.search(array, 5));
    }

    @Test
    void shouldFindElementAtEndOfArray() {
        Integer[] array = {5, 3, 8, 1, 9};
        assertEquals(4, LinearSearch.search(array, 9));
    }

    @Test
    void shouldReturnMinusOneWhenElementNotFound() {
        Integer[] array = {5, 3, 8, 1, 9};
        assertEquals(-1, LinearSearch.search(array, 100));
    }

    @Test
    void shouldReturnMinusOneForEmptyArray() {
        Integer[] array = {};
        assertEquals(-1, LinearSearch.search(array, 5));
    }

    @Test
    void shouldFindOnlyElementInSingleElementArray() {
        Integer[] array = {42};
        assertEquals(0, LinearSearch.search(array, 42));
    }

    @Test
    void shouldReturnFirstOccurrenceWhenDuplicatesExist() {
        Integer[] array = {4, 2, 4, 7, 4};
        assertEquals(0, LinearSearch.search(array, 4));
    }

    @Test
    void shouldThrowExceptionForNullArray() {
        assertThrows(IllegalArgumentException.class,
                () -> LinearSearch.search(null, 5));
    }

    @Test
    void shouldThrowExceptionForNullTarget() {
        Integer[] array = {1, 2, 3};
        assertThrows(IllegalArgumentException.class,
                () -> LinearSearch.search(array, null));
    }

    @Test
    void shouldWorkOnUnsortedArray() {
        String[] array = {"banana", "apple", "date", "cherry"};
        assertEquals(1, LinearSearch.search(array, "apple"));
        assertArrayEquals(new String[]{"banana", "apple", "date", "cherry"}, array);
    }
}
