package algorithms.sorting;

/**
 * SelectionSort - Selection sort algorithm
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class SelectionSort {

    public static <T extends Comparable<T>> void sort(T[] array) {
        if (array == null || array.length <= 1) return;

        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j].compareTo(array[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                T temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }
    }

    public static <T extends Comparable<T>> int sortWithCount(T[] array) {
        int comparisons = 0;
        if (array == null || array.length <= 1) return comparisons;

        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                comparisons++;
                if (array[j].compareTo(array[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                T temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }
        return comparisons;
    }

    public static <T extends Comparable<T>> void sortDescending(T[] array) {
        if (array == null || array.length <= 1) return;

        for (int i = 0; i < array.length - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j].compareTo(array[maxIndex]) > 0) {
                    maxIndex = j;
                }
            }
            if (maxIndex != i) {
                T temp = array[i];
                array[i] = array[maxIndex];
                array[maxIndex] = temp;
            }
        }
    }
}
