package datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Heap - Min-heap implementation using array
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity:
 * - Insert: O(log n)
 * - Extract Min: O(log n)
 * - Get Min: O(1)
 */
public class Heap<T extends Comparable<T>> {
    private List<T> heap;

    public Heap() {
        this.heap = new ArrayList<>();
    }

    /**
     * Insert element
     */
    public void insert(T value) {
        heap.add(value);
        heapifyUp(heap.size() - 1);
    }

    /**
     * Extract minimum element
     */
    public T extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }

        T min = heap.get(0);
        T last = heap.remove(heap.size() - 1);

        if (!isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }

        return min;
    }

    /**
     * Get minimum element
     */
    public T getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return heap.get(0);
    }

    /**
     * Heapify up (after insert)
     */
    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            if (heap.get(index).compareTo(heap.get(parent)) < 0) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    /**
     * Heapify down (after extract)
     */
    private void heapifyDown(int index) {
        int smallest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < heap.size() && heap.get(left).compareTo(heap.get(smallest)) < 0) {
            smallest = left;
        }

        if (right < heap.size() && heap.get(right).compareTo(heap.get(smallest)) < 0) {
            smallest = right;
        }

        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    /**
     * Swap elements
     */
    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    /**
     * Check if empty
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Get size
     */
    public int size() {
        return heap.size();
    }

    /**
     * Clear heap
     */
    public void clear() {
        heap.clear();
    }

    /**
     * Get heap as list
     */
    public List<T> toList() {
        return new ArrayList<>(heap);
    }

    /**
     * Heap sort (returns sorted list)
     */
    public List<T> heapSort() {
        List<T> result = new ArrayList<>();
        Heap<T> tempHeap = new Heap<>();

        for (T item : heap) {
            tempHeap.insert(item);
        }

        while (!tempHeap.isEmpty()) {
            result.add(tempHeap.extractMin());
        }

        return result;
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
