package gh.edu.ug.wastesys.ds;

import java.util.ArrayList;
import java.util.List;

public final class BinarySearchTree<T extends Comparable<T>> {
    private Node<T> root;

    public void insert(T value) {
        root = insert(root, value);
    }

    public boolean contains(T value) {
        Node<T> current = root;
        while (current != null) {
            int comparison = value.compareTo(current.value);
            if (comparison == 0) {
                return true;
            }
            current = comparison < 0 ? current.left : current.right;
        }
        return false;
    }

    public List<T> inorder() {
        List<T> values = new ArrayList<>();
        inorder(root, values);
        return values;
    }

    private Node<T> insert(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        }
        if (value.compareTo(node.value) < 0) {
            node.left = insert(node.left, value);
        } else {
            node.right = insert(node.right, value);
        }
        node.height = 1 + Math.max(height(node.left), height(node.right));
        return balance(node);
    }

    private Node<T> balance(Node<T> node) {
        int balanceFactor = height(node.left) - height(node.right);
        if (balanceFactor > 1) {
            if (height(node.left.left) < height(node.left.right)) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (balanceFactor < -1) {
            if (height(node.right.right) < height(node.right.left)) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node;
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> pivot = node.left;
        node.left = pivot.right;
        pivot.right = node;
        node.height = 1 + Math.max(height(node.left), height(node.right));
        pivot.height = 1 + Math.max(height(pivot.left), height(pivot.right));
        return pivot;
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> pivot = node.right;
        node.right = pivot.left;
        pivot.left = node;
        node.height = 1 + Math.max(height(node.left), height(node.right));
        pivot.height = 1 + Math.max(height(pivot.left), height(pivot.right));
        return pivot;
    }

    private void inorder(Node<T> node, List<T> values) {
        if (node == null) {
            return;
        }
        inorder(node.left, values);
        values.add(node.value);
        inorder(node.right, values);
    }

    private int height(Node<T> node) {
        return node == null ? 0 : node.height;
    }

    private static final class Node<T> {
        private final T value;
        private Node<T> left;
        private Node<T> right;
        private int height = 1;

        private Node(T value) {
            this.value = value;
        }
    }
}
