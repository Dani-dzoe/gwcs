package datastructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomSetTest {

    @Test
    public void testAddAndContains() {
        CustomSet<Integer> set = new CustomSet<>();
        set.add(10);
        set.add(20);
        set.add(30);

        assertTrue(set.contains(10));
        assertTrue(set.contains(20));
        assertFalse(set.contains(40));
    }

    @Test
    public void testRemove() {
        CustomSet<Integer> set = new CustomSet<>();
        set.add(10);
        set.add(20);

        assertTrue(set.remove(10));
        assertFalse(set.contains(10));
        assertTrue(set.contains(20));
    }

    @Test
    public void testSize() {
        CustomSet<Integer> set = new CustomSet<>();
        assertEquals(0, set.size());
        set.add(10);
        assertEquals(1, set.size());
    }

    @Test
    public void testUnion() {
        CustomSet<Integer> set1 = new CustomSet<>();
        set1.add(1);
        set1.add(2);

        CustomSet<Integer> set2 = new CustomSet<>();
        set2.add(2);
        set2.add(3);

        CustomSet<Integer> union = set1.union(set2);
        assertTrue(union.contains(1));
        assertTrue(union.contains(2));
        assertTrue(union.contains(3));
    }

    @Test
    public void testIntersection() {
        CustomSet<Integer> set1 = new CustomSet<>();
        set1.add(1);
        set1.add(2);

        CustomSet<Integer> set2 = new CustomSet<>();
        set2.add(2);
        set2.add(3);

        CustomSet<Integer> intersection = set1.intersection(set2);
        assertTrue(intersection.contains(2));
        assertFalse(intersection.contains(1));
    }
}
