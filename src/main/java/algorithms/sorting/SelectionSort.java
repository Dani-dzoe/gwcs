package algorithms.sorting;

/**
 * Selection Sort
 *
 * On each pass, finds the minimum element in the unsorted portion of the
 * array (from index i to the end) and swaps it into position i. After
 * pass i, the first i+1 elements are sorted and in their final place.
 *
 * Time Complexity:
 * Best:    O(n^2)  - comparisons always run, regardless of input order
 * Average: O(n^2)
 * Worst:   O(n^2)
 *
 * Space Complexity: O(1) - sorts in place, no extra array needed
 */
public class SelectionSort {

    // Utility class - no instances needed
    private SelectionSort() {
    }

    /**
     * Sorts the given array in ascending order using selection sort.
     * The array is modified in place.
     *
     * @param array the array to sort
     * @param <T>   type of elements, must implement Comparable
     */
    public static <T extends Comparable<T>> void sort(T[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array must not be null");
        }

        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j].compareTo(array[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                swap(array, i, minIndex);
            }
        }
    }

    private static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
