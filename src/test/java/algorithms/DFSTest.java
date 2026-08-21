package algorithms.graph;

import org.junit.jupiter.api.Test;
import datastructures.Graph;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * DFSTest - Unit tests for DFS
 */
public class DFSTest {

    @Test
    public void testTraverse() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);

        List<Integer> result = DFS.traverse(graph, 0);

        assertEquals(5, result.size());
        assertEquals(0, result.get(0));  // Start with 0
    }

    @Test
    public void testTraverseRecursive() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);

        List<Integer> result = DFS.traverseRecursive(graph, 0);

        assertEquals(4, result.size());
    }

    @Test
    public void testHasPath() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        assertTrue(DFS.hasPath(graph, 0, 2));
        assertFalse(DFS.hasPath(graph, 0, 4));
    }

    @Test
    public void testCountComponents() {
        Graph graph = new Graph(6);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);
        // Vertex 5 is isolated

        assertEquals(3, DFS.countComponents(graph));  // {0,1,2}, {3,4}, {5}
    }
}
