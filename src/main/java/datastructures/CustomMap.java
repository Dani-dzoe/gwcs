package datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * CustomMap - Custom map implementation using hash table
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class CustomMap<K, V> {
    private HashTable<K, V> map;
    private int size;

    public CustomMap() {
        this.map = new HashTable<>();
        this.size = 0;
    }

    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (!map.containsKey(key)) size++;
        map.put(key, value);
    }

    public V get(K key) {
        if (key == null) return null;
        return map.get(key);
    }

    public V remove(K key) {
        if (key == null || !map.containsKey(key)) return null;
        V value = map.get(key);
        map.remove(key);
        size--;
        return value;
    }

    public boolean containsKey(K key) {
        if (key == null) return false;
        return map.containsKey(key);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public List<K> keySet() {
        return map.keySet();
    }

    public List<V> values() {
        List<V> values = new ArrayList<>();
        for (K key : keySet()) values.add(map.get(key));
        return values;
    }

    public void clear() {
        map = new HashTable<>();
        size = 0;
    }
}
