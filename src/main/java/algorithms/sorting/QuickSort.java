package algorithms.sorting;

/**
 * QuickSort - Quick sort algorithm (divide and conquer)
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity (average): O(n log n)
 * Time Complexity (worst): O(n^2)
 * Space Complexity: O(log n)
 * Stable: No
 */
public class QuickSort {

    /**
     * Sort array
     */
    public static <T extends Comparable<T>> void sort(T[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        quickSort(array, 0, array.length - 1);
    }

    private static <T extends Comparable<T>> void quickSort(T[] array, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivotIndex = partition(array, left, right);
        quickSort(array, left, pivotIndex - 1);
        quickSort(array, pivotIndex + 1, right);
    }

    private static <T extends Comparable<T>> int partition(T[] array, int left, int right) {
        T pivot = array[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, right);
        return i + 1;
    }

    /**
     * Sort with comparison count
     */
    public static <T extends Comparable<T>> int sortWithCount(T[] array) {
        if (array == null || array.length <= 1) {
            return 0;
        }

        int[] count = {0};
        quickSortWithCount(array, 0, array.length - 1, count);
        return count[0];
    }

    private static <T extends Comparable<T>> void quickSortWithCount(T[] array, int left, int right, int[] count) {
        if (left >= right) {
            return;
        }

        int pivotIndex = partitionWithCount(array, left, right, count);
        quickSortWithCount(array, left, pivotIndex - 1, count);
        quickSortWithCount(array, pivotIndex + 1, right, count);
    }

    private static <T extends Comparable<T>> int partitionWithCount(T[] array, int left, int right, int[] count) {
        T pivot = array[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            count[0]++;
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, right);
        return i + 1;
    }

    /**
     * Swap elements
     */
    private static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
