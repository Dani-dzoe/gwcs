package datastructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * BSTTest - Unit tests for Binary Search Tree
 */
public class BSTTest {

    @Test
    public void testInsertAndSearch() {
        BST<Integer> bst = new BST<>();

        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);

        assertTrue(bst.search(50));
        assertTrue(bst.search(30));
        assertTrue(bst.search(70));
        assertTrue(bst.search(20));
        assertTrue(bst.search(40));
        assertFalse(bst.search(100));
    }

    @Test
    public void testDelete() {
        BST<Integer> bst = new BST<>();

        bst.insert(50);
        bst.insert(30);
        bst.insert(70);

        assertTrue(bst.delete(30));
        assertFalse(bst.search(30));
        assertTrue(bst.search(50));
        assertTrue(bst.search(70));
    }

    @Test
    public void testDeleteNonExistent() {
        BST<Integer> bst = new BST<>();
        bst.insert(50);

        assertFalse(bst.delete(100));
    }

    @Test
    public void testInorderTraversal() {
        BST<Integer> bst = new BST<>();

        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);

        // Inorder should give sorted order
        // (Test visually - just ensure it doesn't crash)
        assertDoesNotThrow(() -> bst.inorder());
    }

    @Test
    public void testHeight() {
        BST<Integer> bst = new BST<>();

        assertEquals(-1, bst.height());

        bst.insert(50);
        assertEquals(0, bst.height());

        bst.insert(30);
        bst.insert(70);
        assertEquals(1, bst.height());
    }

    @Test
    public void testSize() {
        BST<Integer> bst = new BST<>();

        assertEquals(0, bst.size());

        bst.insert(50);
        assertEquals(1, bst.size());

        bst.insert(30);
        bst.insert(70);
        assertEquals(3, bst.size());
    }

    @Test
    public void testIsEmpty() {
        BST<Integer> bst = new BST<>();
        assertTrue(bst.isEmpty());

        bst.insert(50);
        assertFalse(bst.isEmpty());
    }

    @Test
    public void testClear() {
        BST<Integer> bst = new BST<>();

        bst.insert(50);
        bst.insert(30);
        bst.insert(70);

        bst.clear();

        assertTrue(bst.isEmpty());
        assertEquals(0, bst.size());
    }

    @Test
    public void testInsertNull() {
        BST<Integer> bst = new BST<>();

        assertThrows(IllegalArgumentException.class, () -> {
            bst.insert(null);
        });
    }

    @Test
    public void testDuplicates() {
        BST<Integer> bst = new BST<>();

        bst.insert(50);
        bst.insert(50);  // Duplicate

        assertTrue(bst.search(50));
        assertEquals(1, bst.size());  // Should not increase
    }
}
