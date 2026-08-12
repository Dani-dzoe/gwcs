package datastructures;

/**
 * My custom dynamic array class.
 * Works like ArrayList, expanding automatically when full.
 *
 * @param <T> what we're storing
 */
public class DynamicArray<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] data;
    private int size;

    // Start with default capacity
    public DynamicArray() {
        this.data = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    // Tack an item onto the end
    public void add(T value) {
        if (size == data.length) {
            resize(); // array is full, double it
        }
        data[size++] = value;
    }

    // Squeeze an item into a specific spot
    public void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        if (size == data.length) {
            resize();
        }
        
        // Shift everything right to make room
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = value;
        size++;
    }

    // Grab the item at index
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    // Swap out the item at index
    public void set(int index, T value) {
        checkIndex(index);
        data[index] = value;
    }

    // Drop the item at index and shift everything left
    public void remove(int index) {
        checkIndex(index);
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[size - 1] = null; // free up memory
        size--;
    }

    // Quick check if we have this item
    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    // Find where the item lives. Returns -1 if not found.
    public int indexOf(T value) {
        for (int i = 0; i < size; i++) {
            if (value == null) {
                if (data[i] == null) {
                    return i;
                }
            } else if (value.equals(data[i])) {
                return i;
            }
        }
        return -1; // didn't find it
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Wipe the whole array clean
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    // Helper to double the array size when we hit the limit
    private void resize() {
        int newCapacity = data.length * 2;
        Object[] newData = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData; // swap in the new bigger array
    }

    // Quick bounds check helper to keep things DRY
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
