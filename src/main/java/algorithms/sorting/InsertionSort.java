package algorithms.sorting;

/**
 * Insertion Sort
 *
 * Sorts an array in ascending order by growing a sorted
 * portion one element at a time.
 *
 * Time Complexity:
 * Best: O(n)    - already sorted input
 * Average: O(n^2)
 * Worst: O(n^2)  - reverse sorted input
 *
 * Space Complexity: O(1) - in-place sort
 */
public class InsertionSort {

    /**
     * Sorts the given array in ascending order using insertion sort.
     *
     * @param array array to sort, modified in place
     */
    public static void sort(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array must not be null");
        }

        for (int i = 1; i < array.length; i++) {

            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {

                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = key;
        }
    }
}