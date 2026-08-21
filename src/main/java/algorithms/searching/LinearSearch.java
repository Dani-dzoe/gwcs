package algorithms.searching;

/**
 * LinearSearch - Linear search algorithm
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class LinearSearch {

    /**
     * Search for target in array using linear search
     * @param array Array to search
     * @param target Value to find
     * @return Index of target, or -1 if not found
     */
    public static <T> int search(T[] array, T target) {
        if (array == null || target == null) return -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Search and return index and comparison count
     * @param array Array to search
     * @param target Value to find
     * @return int[2] where [0] = index, [1] = comparisons
     */
    public static <T> int[] searchWithCount(T[] array, T target) {
        int comparisons = 0;
        if (array == null || target == null) {
            return new int[]{-1, comparisons};
        }

        for (int i = 0; i < array.length; i++) {
            comparisons++;
            if (array[i].equals(target)) {
                return new int[]{i, comparisons};
            }
        }
        return new int[]{-1, comparisons};
    }

    /**
     * Find all occurrences of target
     * @param array Array to search
     * @param target Value to find
     * @return List of indices where target appears
     */
    public static <T> java.util.List<Integer> searchAll(T[] array, T target) {
        java.util.List<Integer> indices = new java.util.ArrayList<>();
        if (array == null || target == null) return indices;

        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                indices.add(i);
            }
        }
        return indices;
    }

    /**
     * Search with early termination if array is sorted
     * @param array Sorted array to search
     * @param target Value to find
     * @return Index of target, or -1 if not found
     */
    public static <T extends Comparable<T>> int searchSorted(T[] array, T target) {
        if (array == null || target == null) return -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
            if (array[i].compareTo(target) > 0) {
                break;  // Early termination
            }
        }
        return -1;
    }
}
