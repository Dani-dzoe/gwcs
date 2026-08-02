package gh.edu.ug.wastesys.ds;

import java.util.ArrayList;
import java.util.List;

public final class BTree<T extends Comparable<T>> {
    private final int minimumDegree;
    private Node<T> root;

    public BTree() {
        this(2);
    }

    public BTree(int minimumDegree) {
        if (minimumDegree < 2) {
            throw new IllegalArgumentException("minimumDegree must be at least 2");
        }
        this.minimumDegree = minimumDegree;
    }

    public boolean contains(T value) {
        return search(root, value) != null;
    }

    public void insert(T value) {
        if (root == null) {
            root = new Node<>(true);
            root.keys.add(value);
            return;
        }
        if (root.keys.size() == 2 * minimumDegree - 1) {
            Node<T> newRoot = new Node<>(false);
            newRoot.children.add(root);
            splitChild(newRoot, 0);
            insertNonFull(newRoot, value);
            root = newRoot;
        } else {
            insertNonFull(root, value);
        }
    }

    public List<T> inorder() {
        List<T> values = new ArrayList<>();
        inorder(root, values);
        return values;
    }

    private Node<T> search(Node<T> node, T value) {
        if (node == null) {
            return null;
        }
        int index = 0;
        while (index < node.keys.size() && value.compareTo(node.keys.get(index)) > 0) {
            index++;
        }
        if (index < node.keys.size() && value.compareTo(node.keys.get(index)) == 0) {
            return node;
        }
        if (node.leaf) {
            return null;
        }
        return search(node.children.get(index), value);
    }

    private void insertNonFull(Node<T> node, T value) {
        int index = node.keys.size() - 1;
        if (node.leaf) {
            node.keys.add(null);
            while (index >= 0 && value.compareTo(node.keys.get(index)) < 0) {
                node.keys.set(index + 1, node.keys.get(index));
                index--;
            }
            node.keys.set(index + 1, value);
            return;
        }
        while (index >= 0 && value.compareTo(node.keys.get(index)) < 0) {
            index--;
        }
        index++;
        Node<T> child = node.children.get(index);
        if (child.keys.size() == 2 * minimumDegree - 1) {
            splitChild(node, index);
            if (value.compareTo(node.keys.get(index)) > 0) {
                index++;
            }
        }
        insertNonFull(node.children.get(index), value);
    }

    private void splitChild(Node<T> parent, int childIndex) {
        Node<T> fullChild = parent.children.get(childIndex);
        Node<T> sibling = new Node<>(fullChild.leaf);
        for (int j = 0; j < minimumDegree - 1; j++) {
            sibling.keys.add(fullChild.keys.remove(minimumDegree));
        }
        if (!fullChild.leaf) {
            for (int j = 0; j < minimumDegree; j++) {
                sibling.children.add(fullChild.children.remove(minimumDegree));
            }
        }
        parent.children.add(childIndex + 1, sibling);
        parent.keys.add(childIndex, fullChild.keys.remove(minimumDegree - 1));
    }

    private void inorder(Node<T> node, List<T> values) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < node.keys.size(); i++) {
            if (!node.leaf) {
                inorder(node.children.get(i), values);
            }
            values.add(node.keys.get(i));
        }
        if (!node.leaf) {
            inorder(node.children.get(node.keys.size()), values);
        }
    }

    private static final class Node<T> {
        private final List<T> keys = new ArrayList<>();
        private final List<Node<T>> children = new ArrayList<>();
        private final boolean leaf;

        private Node(boolean leaf) {
            this.leaf = leaf;
        }
    }
}