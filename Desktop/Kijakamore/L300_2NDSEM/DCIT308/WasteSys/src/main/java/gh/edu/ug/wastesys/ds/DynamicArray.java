package gh.edu.ug.wastesys.ds;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class DynamicArray<T> implements Iterable<T> {
    private Object[] elements;
    private int size;

    public DynamicArray() {
        this(8);
    }

    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("initialCapacity must be positive");
        }
        this.elements = new Object[initialCapacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(T value) {
        ensureCapacity(size + 1);
        elements[size++] = value;
    }

    public T get(int index) {
        rangeCheck(index);
        return elementAt(index);
    }

    public T set(int index, T value) {
        rangeCheck(index);
        T previous = elementAt(index);
        elements[index] = value;
        return previous;
    }

    public T remove(int index) {
        rangeCheck(index);
        T removed = elementAt(index);
        int moveCount = size - index - 1;
        if (moveCount > 0) {
            System.arraycopy(elements, index + 1, elements, index, moveCount);
        }
        elements[--size] = null;
        return removed;
    }

    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    private void ensureCapacity(int required) {
        if (required <= elements.length) {
            return;
        }
        int newCapacity = Math.max(required, elements.length * 2);
        elements = Arrays.copyOf(elements, newCapacity);
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) elements[index];
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int cursor;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return elementAt(cursor++);
            }
        };
    }
}
