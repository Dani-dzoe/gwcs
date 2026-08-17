package algorithms.sorting; 

import java.util.Comparator;

public class MergeSort {

    public static <T> void sort(T[] array, Comparator<T> comparator) {
        if (array == null || array.length <= 1) {
            return;
        }
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Object[array.length];
        mergeSort(array, temp, 0, array.length - 1, comparator);
    }

    private static <T> void mergeSort(T[] arr, T[] temp, int left, int right, Comparator<T> comp) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, temp, left, mid, comp);
            mergeSort(arr, temp, mid + 1, right, comp);
            merge(arr, temp, left, mid, right, comp);
        }
    }

    private static <T> void merge(T[] arr, T[] temp, int left, int mid, int right, Comparator<T> comp) {
        for (int i = left; i <= right; i++) {
            temp[i] = arr[i];
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (comp.compare(temp[i], temp[j]) <= 0) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = temp[j++];
            }
        }

        while (i <= mid) {
            arr[k++] = temp[i++];
        }
    }
}