package datastructures;

/**
 * RedBlackTree - Red-Black Tree implementation
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity:
 * - Insert: O(log n)
 * - Search: O(log n)
 * - Delete: O(log n)
 */
public class RedBlackTree<T extends Comparable<T>> {
    private enum Color { RED, BLACK }

    private class Node {
        T data;
        Color color;
        Node left;
        Node right;
        Node parent;

        Node(T data, Color color) {
            this.data = data;
            this.color = color;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    private Node root;
    private int size;

    public RedBlackTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Insert value
     */
    private boolean isNewRBTNodeInserted;

    public void insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot insert null");
        }

        isNewRBTNodeInserted = false;
        Node newNode = new Node(value, Color.RED);

        if (root == null) {
            root = newNode;
            root.color = Color.BLACK;
            isNewRBTNodeInserted = true;
        } else {
            insertRec(root, newNode);
            if (isNewRBTNodeInserted) {
                fixViolations(newNode);
            }
        }
        if (isNewRBTNodeInserted) {
            size++;
        }
    }

    private void insertRec(Node current, Node newNode) {
        if (newNode.data.compareTo(current.data) < 0) {
            if (current.left == null) {
                current.left = newNode;
                newNode.parent = current;
                isNewRBTNodeInserted = true;
            } else {
                insertRec(current.left, newNode);
            }
        } else if (newNode.data.compareTo(current.data) > 0) {
            if (current.right == null) {
                current.right = newNode;
                newNode.parent = current;
                isNewRBTNodeInserted = true;
            } else {
                insertRec(current.right, newNode);
            }
        }
    }

    private void fixViolations(Node node) {
        Node parent = node.parent;

        while (parent != null && parent.color == Color.RED) {
            Node grandparent = parent.parent;

            if (parent == grandparent.left) {
                Node uncle = grandparent.right;

                if (uncle != null && uncle.color == Color.RED) {
                    parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    grandparent.color = Color.RED;
                    node = grandparent;
                } else {
                    if (node == parent.right) {
                        rotateLeft(parent);
                        node = parent;
                        parent = node.parent;
                    }
                    rotateRight(grandparent);
                    Color temp = parent.color;
                    parent.color = grandparent.color;
                    grandparent.color = temp;
                    node = parent;
                }
            } else {
                Node uncle = grandparent.left;

                if (uncle != null && uncle.color == Color.RED) {
                    parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    grandparent.color = Color.RED;
                    node = grandparent;
                } else {
                    if (node == parent.left) {
                        rotateRight(parent);
                        node = parent;
                        parent = node.parent;
                    }
                    rotateLeft(grandparent);
                    Color temp = parent.color;
                    parent.color = grandparent.color;
                    grandparent.color = temp;
                    node = parent;
                }
            }

            parent = node.parent;
        }

        root.color = Color.BLACK;
    }

    private void rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;

        if (y.left != null) {
            y.left.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;

        if (y.right != null) {
            y.right.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }

        y.right = x;
        x.parent = y;
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
     * Inorder traversal
     */
    public void inorder() {
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(Node node) {
        if (node != null) {
            inorderRec(node.left);
            System.out.print(node.data + "(" + node.color + ") ");
            inorderRec(node.right);
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
     * Get black height
     */
    public int blackHeight() {
        return blackHeightRec(root);
    }

    private int blackHeightRec(Node node) {
        if (node == null) {
            return 0;
        }
        int leftBH = blackHeightRec(node.left);
        int rightBH = blackHeightRec(node.right);

        if (leftBH == -1 || rightBH == -1 || leftBH != rightBH) {
            return -1;
        }

        return (node.color == Color.BLACK ? 1 : 0) + leftBH;
    }
}
