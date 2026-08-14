package algorithms.searching;

/**
 * Linear Search
 *
 * Sequentially scans each element of the array, from index 0 onward,
 * comparing it to the target value. Works on sorted and unsorted data,
 * which is its main advantage over Binary Search.
 *
 * Time Complexity:
 * Best:    O(1)   - target is the first element
 * Average: O(n)
 * Worst:   O(n)   - target is the last element or absent
 *
 * Space Complexity: O(1)
 */
public class LinearSearch {

    // Utility class - no instances needed
    private LinearSearch() {
    }

    /**
     * Searches the array for the first occurrence of target.
     *
     * @param array  the array to search (may be unsorted)
     * @param target the value to search for
     * @param <T>    type of elements, must implement Comparable
     * @return the index of the first occurrence of target, or -1 if the
     *         array does not contain target
     */
    public static <T extends Comparable<T>> int search(T[] array, T target) {
        if (array == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
        if (target == null) {
            throw new IllegalArgumentException("Target must not be null");
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i].compareTo(target) == 0) {
                return i;
            }
        }
        return -1;
    }
}
