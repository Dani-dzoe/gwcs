package datastructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RedBlackTreeTest {

    @Test
    public void testInsertAndSearch() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.insert(50);
        rbt.insert(30);
        rbt.insert(70);
        rbt.insert(20);
        rbt.insert(40);

        assertTrue(rbt.search(50));
        assertTrue(rbt.search(30));
        assertTrue(rbt.search(70));
        assertFalse(rbt.search(100));
    }

    @Test
    public void testHeight() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.insert(50);
        rbt.insert(30);
        rbt.insert(70);

        assertTrue(rbt.height() >= 1);
    }

    @Test
    public void testSize() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        assertEquals(0, rbt.size());
        rbt.insert(50);
        assertEquals(1, rbt.size());
    }

    @Test
    public void testInsertNull() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        assertThrows(IllegalArgumentException.class, () -> rbt.insert(null));
    }

    @Test
    public void testDuplicates() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.insert(50);
        rbt.insert(50);
        assertTrue(rbt.search(50));
        assertEquals(1, rbt.size());
    }
}
