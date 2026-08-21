package datastructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomMapTest {

    @Test
    public void testPutAndGet() {
        CustomMap<String, Integer> map = new CustomMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);

        assertEquals(1, map.get("A"));
        assertEquals(2, map.get("B"));
        assertEquals(3, map.get("C"));
    }

    @Test
    public void testUpdate() {
        CustomMap<String, Integer> map = new CustomMap<>();
        map.put("A", 1);
        map.put("A", 10);

        assertEquals(10, map.get("A"));
    }

    @Test
    public void testRemove() {
        CustomMap<String, Integer> map = new CustomMap<>();
        map.put("A", 1);
        map.put("B", 2);

        assertEquals(1, map.remove("A"));
        assertNull(map.get("A"));
    }

    @Test
    public void testContainsKey() {
        CustomMap<String, Integer> map = new CustomMap<>();
        map.put("A", 1);

        assertTrue(map.containsKey("A"));
        assertFalse(map.containsKey("B"));
    }

    @Test
    public void testSize() {
        CustomMap<String, Integer> map = new CustomMap<>();
        assertEquals(0, map.size());
        map.put("A", 1);
        assertEquals(1, map.size());
    }
}
