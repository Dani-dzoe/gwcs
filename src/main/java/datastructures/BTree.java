package datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * BTree - B-Tree implementation (order 3)
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class BTree<T extends Comparable<T>> {
    private static final int ORDER = 3;

    private class Node {
        List<T> keys = new ArrayList<>();
        List<Node> children = new ArrayList<>();
        boolean isLeaf;

        Node(boolean isLeaf) {
            this.isLeaf = isLeaf;
        }
    }

    private Node root;
    private int size;

    public BTree() {
        this.root = new Node(true);
        this.size = 0;
    }

    public void insert(T key) {
        if (key == null) throw new IllegalArgumentException("Cannot insert null");
        if (search(key)) return;

        Node result = insertRec(root, key);
        if (result != null) {
            Node newRoot = new Node(false);
            newRoot.keys.add(result.keys.get(0));
            newRoot.children.add(root);
            newRoot.children.add(result.children.get(1));
            root = newRoot;
        }
        size++;
    }

    private Node insertRec(Node node, T key) {
        int i = 0;
        while (i < node.keys.size() && key.compareTo(node.keys.get(i)) > 0) i++;

        if (node.isLeaf) {
            node.keys.add(i, key);
            if (node.keys.size() >= ORDER) return splitNode(node);
            return null;
        } else {
            Node childResult = insertRec(node.children.get(i), key);
            if (childResult != null) {
                node.keys.add(i, childResult.keys.get(0));
                node.children.set(i, childResult.children.get(0));
                node.children.add(i + 1, childResult.children.get(1));
                if (node.keys.size() >= ORDER) return splitNode(node);
            }
            return null;
        }
    }

    private Node splitNode(Node node) {
        int mid = node.keys.size() / 2;
        T midKey = node.keys.get(mid);

        Node rightNode = new Node(node.isLeaf);
        while (node.keys.size() > mid) rightNode.keys.add(0, node.keys.remove(node.keys.size() - 1));
        node.keys.remove(node.keys.size() - 1);

        if (!node.isLeaf) {
            while (node.children.size() > mid + 1) rightNode.children.add(0, node.children.remove(node.children.size() - 1));
        }

        Node result = new Node(false);
        result.keys.add(midKey);
        result.children.add(node);
        result.children.add(rightNode);
        return result;
    }

    public boolean search(T key) {
        return searchRec(root, key);
    }

    private boolean searchRec(Node node, T key) {
        int i = 0;
        while (i < node.keys.size() && key.compareTo(node.keys.get(i)) > 0) i++;
        if (i < node.keys.size() && key.compareTo(node.keys.get(i)) == 0) return true;
        if (node.isLeaf) return false;
        return searchRec(node.children.get(i), key);
    }

    public void inorder() {
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(Node node) {
        if (node == null) return;
        for (int i = 0; i < node.keys.size(); i++) {
            if (!node.isLeaf) inorderRec(node.children.get(i));
            System.out.print(node.keys.get(i) + " ");
        }
        if (!node.isLeaf && node.children.size() > node.keys.size()) {
            inorderRec(node.children.get(node.children.size() - 1));
        }
    }

    public int height() {
        return heightRec(root);
    }

    private int heightRec(Node node) {
        if (node == null || node.isLeaf) return 0;
        return 1 + heightRec(node.children.get(0));
    }

    public int size() {
        return size;
    }
}
