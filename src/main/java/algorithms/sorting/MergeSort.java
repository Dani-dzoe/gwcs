package algorithms.sorting;

/**
 * MergeSort - Merge sort algorithm (divide and conquer)
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity: O(n log n)
 * Space Complexity: O(n)
 * Stable: Yes
 */
public class MergeSort {

    /**
     * Sort array
     */
    public static <T extends Comparable<T>> void sort(T[] array) {
        if (array == null || array.length <= 1) {
            return;
        }

        T[] aux = array.clone();
        mergeSort(array, aux, 0, array.length - 1);
    }

    private static <T extends Comparable<T>> void mergeSort(T[] array, T[] aux, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(aux, array, left, mid);
        mergeSort(aux, array, mid + 1, right);

        merge(array, aux, left, mid, right);
    }

    private static <T extends Comparable<T>> void merge(T[] array, T[] aux, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;

        for (int k = left; k <= right; k++) {
            if (i > mid) {
                array[k] = aux[j++];
            } else if (j > right) {
                array[k] = aux[i++];
            } else if (aux[j].compareTo(aux[i]) < 0) {
                array[k] = aux[j++];
            } else {
                array[k] = aux[i++];
            }
        }
    }

    /**
     * Sort with comparison count
     */
    public static <T extends Comparable<T>> int sortWithCount(T[] array) {
        if (array == null || array.length <= 1) {
            return 0;
        }

        int[] count = {0};
        T[] aux = array.clone();
        mergeSortWithCount(array, aux, 0, array.length - 1, count);
        return count[0];
    }

    private static <T extends Comparable<T>> void mergeSortWithCount(T[] array, T[] aux, int left, int right, int[] count) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSortWithCount(aux, array, left, mid, count);
        mergeSortWithCount(aux, array, mid + 1, right, count);

        mergeWithCount(array, aux, left, mid, right, count);
    }

    private static <T extends Comparable<T>> void mergeWithCount(T[] array, T[] aux, int left, int mid, int right, int[] count) {
        int i = left;
        int j = mid + 1;

        for (int k = left; k <= right; k++) {
            if (i > mid) {
                array[k] = aux[j++];
            } else if (j > right) {
                array[k] = aux[i++];
            } else {
                count[0]++;
                if (aux[j].compareTo(aux[i]) < 0) {
                    array[k] = aux[j++];
                } else {
                    array[k] = aux[i++];
                }
            }
        }
    }
}
