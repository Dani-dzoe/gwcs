package datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * CustomSet - Custom set implementation using hash table
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class CustomSet<T> {
    private HashTable<T, Boolean> map;
    private int size;

    public CustomSet() {
        this.map = new HashTable<>();
        this.size = 0;
    }

    public boolean add(T value) {
        if (value == null) throw new IllegalArgumentException("Cannot add null");
        if (!contains(value)) {
            map.put(value, true);
            size++;
            return true;
        }
        return false;
    }

    public boolean contains(T value) {
        if (value == null) return false;
        return map.containsKey(value);
    }

    public boolean remove(T value) {
        if (value == null || !contains(value)) return false;
        map.remove(value);
        size--;
        return true;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        map = new HashTable<>();
        size = 0;
    }

    public List<T> toList() {
        return new ArrayList<>(map.keySet());
    }

    public CustomSet<T> union(CustomSet<T> other) {
        CustomSet<T> result = new CustomSet<>();
        for (T item : this.toList()) result.add(item);
        for (T item : other.toList()) result.add(item);
        return result;
    }

    public CustomSet<T> intersection(CustomSet<T> other) {
        CustomSet<T> result = new CustomSet<>();
        for (T item : this.toList()) {
            if (other.contains(item)) result.add(item);
        }
        return result;
    }

    public CustomSet<T> difference(CustomSet<T> other) {
        CustomSet<T> result = new CustomSet<>();
        for (T item : this.toList()) {
            if (!other.contains(item)) result.add(item);
        }
        return result;
    }

    public boolean isSubsetOf(CustomSet<T> other) {
        for (T item : this.toList()) {
            if (!other.contains(item)) return false;
        }
        return true;
    }
}
