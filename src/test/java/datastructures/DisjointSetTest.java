package datastructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * DisjointSetTest - Unit tests for Union-Find
 */
public class DisjointSetTest {

    @Test
    public void testMakeSetAndFind() {
        DisjointSet<Integer> ds = new DisjointSet<>();

        ds.makeSet(1);
        ds.makeSet(2);
        ds.makeSet(3);

        assertEquals(1, ds.find(1));
        assertEquals(2, ds.find(2));
        assertEquals(3, ds.find(3));
    }

    @Test
    public void testUnion() {
        DisjointSet<Integer> ds = new DisjointSet<>();

        ds.makeSet(1);
        ds.makeSet(2);
        ds.makeSet(3);

        assertTrue(ds.union(1, 2));
        assertEquals(1, ds.find(1));
        assertEquals(1, ds.find(2));  // Now in same set
        assertEquals(3, ds.find(3));  // Still separate
    }

    @Test
    public void testUnionSameSet() {
        DisjointSet<Integer> ds = new DisjointSet<>();

        ds.makeSet(1);
        ds.makeSet(2);

        ds.union(1, 2);

        assertFalse(ds.union(1, 2));  // Already in same set
    }

    @Test
    public void testIsSameSet() {
        DisjointSet<Integer> ds = new DisjointSet<>();

        ds.makeSet(1);
        ds.makeSet(2);
        ds.makeSet(3);

        assertFalse(ds.isSameSet(1, 2));

        ds.union(1, 2);

        assertTrue(ds.isSameSet(1, 2));
        assertFalse(ds.isSameSet(1, 3));
    }

    @Test
    public void testNumSets() {
        DisjointSet<Integer> ds = new DisjointSet<>();

        ds.makeSet(1);
        ds.makeSet(2);
        ds.makeSet(3);

        assertEquals(3, ds.numSets());

        ds.union(1, 2);

        assertEquals(2, ds.numSets());  // {1,2} and {3}
    }

    @Test
    public void testSize() {
        DisjointSet<Integer> ds = new DisjointSet<>();

        assertEquals(0, ds.size());

        ds.makeSet(1);
        ds.makeSet(2);
        ds.makeSet(3);

        assertEquals(3, ds.size());
    }

    @Test
    public void testClear() {
        DisjointSet<Integer> ds = new DisjointSet<>();

        ds.makeSet(1);
        ds.makeSet(2);
        ds.makeSet(3);

        ds.clear();

        assertEquals(0, ds.size());
    }

    @Test
    public void testFindNonExistent() {
        DisjointSet<Integer> ds = new DisjointSet<>();

        ds.makeSet(1);

        assertThrows(IllegalArgumentException.class, () -> {
            ds.find(999);
        });
    }
}
