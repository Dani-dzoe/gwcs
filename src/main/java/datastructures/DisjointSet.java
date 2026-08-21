package datastructures;

import java.util.HashMap;
import java.util.Map;

/**
 * DisjointSet - Union-Find data structure
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity (with path compression and union by rank):
 * - Find: O(α(n)) ≈ O(1)
 * - Union: O(α(n)) ≈ O(1)
 */
public class DisjointSet<T> {
    private class Node {
        T data;
        Node parent;
        int rank;

        Node(T data) {
            this.data = data;
            this.parent = this;
            this.rank = 0;
        }
    }

    private Map<T, Node> map;

    public DisjointSet() {
        this.map = new HashMap<>();
    }

    /**
     * Make set
     */
    public void makeSet(T value) {
        if (!map.containsKey(value)) {
            map.put(value, new Node(value));
        }
    }

    /**
     * Find with path compression
     */
    public T find(T value) {
        if (!map.containsKey(value)) {
            throw new IllegalArgumentException("Value not in set");
        }

        return findSet(map.get(value)).data;
    }

    private Node findSet(Node node) {
        if (node != node.parent) {
            node.parent = findSet(node.parent);
        }
        return node.parent;
    }

    /**
     * Union by rank
     */
    public boolean union(T value1, T value2) {
        if (!map.containsKey(value1) || !map.containsKey(value2)) {
            throw new IllegalArgumentException("Value not in set");
        }

        Node root1 = findSet(map.get(value1));
        Node root2 = findSet(map.get(value2));

        if (root1 == root2) {
            return false;
        }

        if (root1.rank > root2.rank) {
            root2.parent = root1;
        } else if (root1.rank < root2.rank) {
            root1.parent = root2;
        } else {
            root2.parent = root1;
            root1.rank++;
        }

        return true;
    }

    /**
     * Check if same set
     */
    public boolean isSameSet(T value1, T value2) {
        return find(value1).equals(find(value2));
    }

    /**
     * Get set size
     */
    public int size() {
        return map.size();
    }

    /**
     * Get number of disjoint sets
     */
    public int numSets() {
        int count = 0;

        for (Node node : map.values()) {
            if (node == node.parent) {
                count++;
            }
        }

        return count;
    }

    /**
     * Clear
     */
    public void clear() {
        map.clear();
    }
}
