package algorithms.graph;

import org.junit.jupiter.api.Test;
import datastructures.Graph;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

/**
 * BFSTest - Unit tests for BFS
 */
public class BFSTest {

    @Test
    public void testTraverse() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);

        List<Integer> result = BFS.traverse(graph, 0);

        assertEquals(5, result.size());
        assertEquals(0, result.get(0));  // Start with 0
    }

    @Test
    public void testShortestPath() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        Map<Integer, Integer> distances = BFS.shortestPath(graph, 0);

        assertEquals(0, distances.get(0));
        assertEquals(1, distances.get(1));
        assertEquals(2, distances.get(2));
        assertEquals(3, distances.get(3));
    }

    @Test
    public void testHasPath() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        assertTrue(BFS.hasPath(graph, 0, 2));
        assertFalse(BFS.hasPath(graph, 0, 4));
    }

    @Test
    public void testHasPathSameVertex() {
        Graph graph = new Graph(3);

        assertTrue(BFS.hasPath(graph, 1, 1));  // Same vertex
    }
}
