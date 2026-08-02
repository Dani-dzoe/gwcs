package gh.edu.ug.wastesys.ds;

import java.util.ArrayList;
import java.util.List;

public final class RedBlackTree<T extends Comparable<T>> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node<T> root;

    public void insert(T value) {
        root = insert(root, value);
        root.color = BLACK;
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
            return new Node<>(value, RED);
        }
        int comparison = value.compareTo(node.value);
        if (comparison < 0) {
            node.left = insert(node.left, value);
        } else if (comparison > 0) {
            node.right = insert(node.right, value);
        } else {
            node.value = value;
        }

        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }

    private boolean isRed(Node<T> node) {
        return node != null && node.color == RED;
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> pivot = node.right;
        node.right = pivot.left;
        pivot.left = node;
        pivot.color = node.color;
        node.color = RED;
        return pivot;
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> pivot = node.left;
        node.left = pivot.right;
        pivot.right = node;
        pivot.color = node.color;
        node.color = RED;
        return pivot;
    }

    private void flipColors(Node<T> node) {
        node.color = RED;
        if (node.left != null) {
            node.left.color = BLACK;
        }
        if (node.right != null) {
            node.right.color = BLACK;
        }
    }

    private void inorder(Node<T> node, List<T> values) {
        if (node == null) {
            return;
        }
        inorder(node.left, values);
        values.add(node.value);
        inorder(node.right, values);
    }

    private static final class Node<T> {
        private T value;
        private Node<T> left;
        private Node<T> right;
        private boolean color;

        private Node(T value, boolean color) {
            this.value = value;
            this.color = color;
        }
    }
}
