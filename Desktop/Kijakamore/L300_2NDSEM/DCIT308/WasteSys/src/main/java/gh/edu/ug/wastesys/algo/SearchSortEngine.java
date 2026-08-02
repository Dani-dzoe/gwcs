package gh.edu.ug.wastesys.algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SearchSortEngine {
    public <T extends Comparable<T>> int linearSearch(List<T> values, T target) {
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).compareTo(target) == 0) {
                return i;
            }
        }
        return -1;
    }

    public <T extends Comparable<T>> int binarySearch(List<T> values, T target) {
        ensureSorted(values);
        int low = 0;
        int high = values.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = values.get(mid).compareTo(target);
            if (comparison == 0) {
                return mid;
            }
            if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    public <T extends Comparable<T>> void selectionSort(List<T> values) {
        for (int i = 0; i < values.size(); i++) {
            int minIndex = i;
            for (int j = i + 1; j < values.size(); j++) {
                if (values.get(j).compareTo(values.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            Collections.swap(values, i, minIndex);
        }
    }

    public <T extends Comparable<T>> void insertionSort(List<T> values) {
        for (int i = 1; i < values.size(); i++) {
            T key = values.get(i);
            int j = i - 1;
            while (j >= 0 && values.get(j).compareTo(key) > 0) {
                values.set(j + 1, values.get(j));
                j--;
            }
            values.set(j + 1, key);
        }
    }

    public <T extends Comparable<T>> List<T> mergeSort(List<T> values) {
        if (values.size() <= 1) {
            return new ArrayList<>(values);
        }
        int mid = values.size() / 2;
        List<T> left = mergeSort(values.subList(0, mid));
        List<T> right = mergeSort(values.subList(mid, values.size()));
        return merge(left, right);
    }

    public <T extends Comparable<T>> List<T> quickSort(List<T> values) {
        List<T> copy = new ArrayList<>(values);
        quickSortInPlace(copy, 0, copy.size() - 1);
        return copy;
    }

    private <T extends Comparable<T>> void quickSortInPlace(List<T> values, int low, int high) {
        if (low >= high) {
            return;
        }
        int pivot = partition(values, low, high);
        quickSortInPlace(values, low, pivot - 1);
        quickSortInPlace(values, pivot + 1, high);
    }

    private <T extends Comparable<T>> int partition(List<T> values, int low, int high) {
        T pivot = values.get(high);
        int storeIndex = low;
        for (int i = low; i < high; i++) {
            if (values.get(i).compareTo(pivot) <= 0) {
                Collections.swap(values, i, storeIndex++);
            }
        }
        Collections.swap(values, storeIndex, high);
        return storeIndex;
    }

    private <T extends Comparable<T>> void ensureSorted(List<T> values) {
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i - 1).compareTo(values.get(i)) > 0) {
                throw new IllegalArgumentException("binary search requires sorted input");
            }
        }
    }

    private <T extends Comparable<T>> List<T> merge(List<T> left, List<T> right) {
        List<T> merged = new ArrayList<>(left.size() + right.size());
        int leftIndex = 0;
        int rightIndex = 0;
        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex).compareTo(right.get(rightIndex)) <= 0) {
                merged.add(left.get(leftIndex++));
            } else {
                merged.add(right.get(rightIndex++));
            }
        }
        while (leftIndex < left.size()) {
            merged.add(left.get(leftIndex++));
        }
        while (rightIndex < right.size()) {
            merged.add(right.get(rightIndex++));
        }
        return merged;
    }
}
