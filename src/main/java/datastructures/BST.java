package datastructures;

/**
 * BST - Binary Search Tree implementation
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity (average):
 * - Insert: O(log n)
 * - Search: O(log n)
 * - Delete: O(log n)
 * 
 * Time Complexity (worst):
 * - Insert/Search/Delete: O(n)
 */
public class BST<T extends Comparable<T>> {
    private class Node {
        T data;
        Node left;
        Node right;

        Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    public BST() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Insert value
     */
    private boolean isNewNodeInserted;
    
    public void insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot insert null");
        }
        isNewNodeInserted = false;
        root = insertRec(root, value);
        if (isNewNodeInserted) {
            size++;
        }
    }

    private Node insertRec(Node node, T value) {
        if (node == null) {
            isNewNodeInserted = true;
            return new Node(value);
        }

        if (value.compareTo(node.data) < 0) {
            node.left = insertRec(node.left, value);
        } else if (value.compareTo(node.data) > 0) {
            node.right = insertRec(node.right, value);
        }

        return node;
    }

    /**
     * Search for value
     */
    public boolean search(T value) {
        return searchRec(root, value) != null;
    }

    private Node searchRec(Node node, T value) {
        if (node == null || value.equals(node.data)) {
            return node;
        }

        if (value.compareTo(node.data) < 0) {
            return searchRec(node.left, value);
        } else {
            return searchRec(node.right, value);
        }
    }

    /**
     * Delete value
     */
    public boolean delete(T value) {
        if (!search(value)) {
            return false;
        }
        root = deleteRec(root, value);
        size--;
        return true;
    }

    private Node deleteRec(Node node, T value) {
        if (node == null) {
            return null;
        }

        if (value.compareTo(node.data) < 0) {
            node.left = deleteRec(node.left, value);
        } else if (value.compareTo(node.data) > 0) {
            node.right = deleteRec(node.right, value);
        } else {
            // Node with one or no child
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            // Node with two children
            node.data = minValue(node.right);
            node.right = deleteRec(node.right, node.data);
        }

        return node;
    }

    private T minValue(Node node) {
        T min = node.data;
        while (node.left != null) {
            min = node.left.data;
            node = node.left;
        }
        return min;
    }

    /**
     * Inorder traversal
     */
    public void inorder() {
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(Node node) {
        if (node != null) {
            inorderRec(node.left);
            System.out.print(node.data + " ");
            inorderRec(node.right);
        }
    }

    /**
     * Preorder traversal
     */
    public void preorder() {
        preorderRec(root);
        System.out.println();
    }

    private void preorderRec(Node node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preorderRec(node.left);
            preorderRec(node.right);
        }
    }

    /**
     * Postorder traversal
     */
    public void postorder() {
        postorderRec(root);
        System.out.println();
    }

    private void postorderRec(Node node) {
        if (node != null) {
            postorderRec(node.left);
            postorderRec(node.right);
            System.out.print(node.data + " ");
        }
    }

    /**
     * Get height
     */
    public int height() {
        return heightRec(root);
    }

    private int heightRec(Node node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(heightRec(node.left), heightRec(node.right));
    }

    /**
     * Get size
     */
    public int size() {
        return size;
    }

    /**
     * Check if empty
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Clear tree
     */
    public void clear() {
        root = null;
        size = 0;
    }
}
