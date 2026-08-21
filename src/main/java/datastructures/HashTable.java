package datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * HashTable - Hash table with chaining
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity (average):
 * - Put: O(1)
 * - Get: O(1)
 * - Remove: O(1)
 * 
 * Time Complexity (worst):
 * - Put/Get/Remove: O(n)
 */
public class HashTable<K, V> {
    
    // Fix 1: Added generic markers <K, V> to the inner Entry class declaration
    private class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    // Fix 2: Updated the table array to use the generic version of Entry
    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    @SuppressWarnings("unchecked")
    public HashTable() {
        this.capacity = 16;
        // Fix 3: Creates a raw Entry array and casts cleanly to a generic array
        this.table = (Entry<K, V>[]) new Entry[capacity];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        // Fix 4: Creates a raw Entry array and casts cleanly to a generic array
        this.table = (Entry<K, V>[]) new Entry[capacity];
        this.size = 0;
    }

    /**
     * Put key-value pair
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = hash(key);

        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        // Fix 5: Instantiates the new Entry using diamond operator syntax
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = table[index];
        table[index] = newEntry;
        size++;

        if ((double) size / capacity > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    /**
     * Get value by key
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = hash(key);
        Entry<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    /**
     * Remove by key
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = hash(key);
        Entry<K, V> current = table[index];
        Entry<K, V> prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }

        return null;
    }

    /**
     * Check if contains key
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Hash function
     */
    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    /**
     * Resize table
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldTable = table;
        capacity = capacity * 2;
        // Fix 6: Instantiates fresh raw array infrastructure during resize rehash
        table = (Entry<K, V>[]) new Entry[capacity];
        size = 0;

        for (Entry<K, V> entry : oldTable) {
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    /**
     * Get size
     */
    public int size() {
        return size;
    }

    /**
     * Get capacity
     */
    public int capacity() {
        return capacity;
    }

    /**
     * Get load factor
     */
    public double loadFactor() {
        return (double) size / capacity;
    }

    /**
     * Get number of collisions
     */
    public int countCollisions() {
        int collisions = 0;

        for (Entry<K, V> entry : table) {
            int chainLength = 0;
            while (entry != null) {
                chainLength++;
                entry = entry.next;
            }
            if (chainLength > 1) {
                collisions += chainLength - 1;
            }
        }

        return collisions;
    }

    /**
     * Get all keys
     */
    public List<K> keySet() {
        List<K> keys = new ArrayList<>();

        for (Entry<K, V> entry : table) {
            while (entry != null) {
                keys.add(entry.key);
                entry = entry.next;
            }
        }

        return keys;
    }

    /**
     * Clear table
     */
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;

        for (Entry<K, V> entry : table) {
            while (entry != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(entry.key).append("=").append(entry.value);
                first = false;
                entry = entry.next;
            }
        }

        sb.append("}");
        return sb.toString();
    }
}

