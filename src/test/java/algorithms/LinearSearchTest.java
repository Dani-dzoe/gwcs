package algorithms.searching;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LinearSearchTest - Unit tests for LinearSearch
 */
public class LinearSearchTest {

    @Test
    public void testSearchFound() {
        Integer[] array = {5, 3, 8, 1, 9, 2};

        assertEquals(0, LinearSearch.search(array, 5));
        assertEquals(1, LinearSearch.search(array, 3));
        assertEquals(4, LinearSearch.search(array, 9));
    }

    @Test
    public void testSearchNotFound() {
        Integer[] array = {5, 3, 8, 1, 9};

        assertEquals(-1, LinearSearch.search(array, 10));
        assertEquals(-1, LinearSearch.search(array, 0));
    }

    @Test
    public void testSearchEmpty() {
        Integer[] array = {};

        assertEquals(-1, LinearSearch.search(array, 1));
    }

    @Test
    public void testSearchNull() {
        Integer[] array = {5, 3, 8};

        assertEquals(-1, LinearSearch.search(array, null));
        assertEquals(-1, LinearSearch.search(null, 5));
    }

    @Test
    public void testSearchFirstElement() {
        Integer[] array = {10, 20, 30};

        assertEquals(0, LinearSearch.search(array, 10));
    }

    @Test
    public void testSearchLastElement() {
        Integer[] array = {10, 20, 30};

        assertEquals(2, LinearSearch.search(array, 30));
    }

    @Test
    public void testSearchAll() {
        Integer[] array = {1, 2, 3, 2, 4, 2};

        java.util.List<Integer> indices = LinearSearch.searchAll(array, 2);

        assertEquals(3, indices.size());
        assertEquals(1, indices.get(0));
        assertEquals(3, indices.get(1));
        assertEquals(5, indices.get(2));
    }

    @Test
    public void testSearchWithCount() {
        Integer[] array = {5, 3, 8, 1, 9};

        int[] result = LinearSearch.searchWithCount(array, 8);

        assertEquals(2, result[0]);  // Index
        assertEquals(3, result[1]);  // Comparisons
    }
}
