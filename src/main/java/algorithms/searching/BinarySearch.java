package algorithms.searching;

/**
 * Binary Search
 *
 * Requires sorted input array (ascending order).
 *
 * Time Complexity:
 * Best: O(1)
 * Average: O(log n)
 * Worst: O(log n)
 *
 * Space Complexity: O(1)
 */
public class BinarySearch {

    /**
     * Searches for target in a sorted array using iterative binary search.
     *
     * @param array  sorted array to search (ascending order)
     * @param target value to search for
     * @return index of target if found, -1 otherwise
     */
    public static int search(int[] array, int target) {
        if (array == null) {
            throw new IllegalArgumentException("Array must not be null");
        }

        int low = 0;
        int high = array.length - 1;

        while (low <= high) {

            int mid = low + (high - low) / 2;

            if (array[mid] == target) {
                return mid;
            }

            if (array[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }
}
