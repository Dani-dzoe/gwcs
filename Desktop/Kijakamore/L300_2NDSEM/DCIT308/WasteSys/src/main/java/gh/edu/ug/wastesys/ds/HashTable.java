package gh.edu.ug.wastesys.ds;

import java.util.ArrayList;
import java.util.List;

public final class HashTable<K, V> {
    private final List<Entry<K, V>>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        buckets = new List[capacity];
    }

    public void put(K key, V value) {
        List<Entry<K, V>> bucket = bucket(key, true);
        for (Entry<K, V> entry : bucket) {
            if (equalsKey(entry.key, key)) {
                entry.value = value;
                return;
            }
        }
        bucket.add(new Entry<>(key, value));
        size++;
    }

    public V get(K key) {
        List<Entry<K, V>> bucket = bucket(key, false);
        if (bucket == null) {
            return null;
        }
        for (Entry<K, V> entry : bucket) {
            if (equalsKey(entry.key, key)) {
                return entry.value;
            }
        }
        return null;
    }

    public V remove(K key) {
        List<Entry<K, V>> bucket = bucket(key, false);
        if (bucket == null) {
            return null;
        }
        for (int i = 0; i < bucket.size(); i++) {
            Entry<K, V> entry = bucket.get(i);
            if (equalsKey(entry.key, key)) {
                bucket.remove(i);
                size--;
                return entry.value;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    private List<Entry<K, V>> bucket(K key, boolean create) {
        int index = Math.floorMod(key == null ? 0 : key.hashCode(), buckets.length);
        if (buckets[index] == null && create) {
            buckets[index] = new ArrayList<>();
        }
        return buckets[index];
    }

    private boolean equalsKey(K left, K right) {
        return left == null ? right == null : left.equals(right);
    }

    private static final class Entry<K, V> {
        private final K key;
        private V value;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
