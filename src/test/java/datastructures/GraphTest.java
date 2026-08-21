package datastructures;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * GraphTest - Unit tests for Graph
 */
public class GraphTest {

    @Test
    public void testAddEdge() {
        Graph graph = new Graph(5);

        graph.addEdge(0, 1, 10.0);
        graph.addEdge(0, 2, 5.0);

        List<Integer> neighbors = graph.getNeighbors(0);

        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(1));
        assertTrue(neighbors.contains(2));
    }

    @Test
    public void testGetWeight() {
        Graph graph = new Graph(5);

        graph.addEdge(0, 1, 10.0);
        graph.addEdge(0, 2, 5.0);

        assertEquals(10.0, graph.getWeight(0, 1));
        assertEquals(5.0, graph.getWeight(0, 2));
        assertEquals(Double.POSITIVE_INFINITY, graph.getWeight(0, 3));
    }

    @Test
    public void testUndirected() {
        Graph graph = new Graph(5, false);

        graph.addEdge(0, 1, 10.0);

        assertTrue(graph.getNeighbors(1).contains(0));  // Reverse edge exists
    }

    @Test
    public void testDirected() {
        Graph graph = new Graph(5, true);

        graph.addEdge(0, 1, 10.0);

        assertFalse(graph.getNeighbors(1).contains(0));  // No reverse edge
    }

    @Test
    public void testVertices() {
        Graph graph = new Graph(5);

        assertEquals(5, graph.vertices());
    }

    @Test
    public void testEdges() {
        Graph graph = new Graph(5);

        assertEquals(0, graph.edges());

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);

        assertEquals(2, graph.edges());
    }

    @Test
    public void testInvalidVertex() {
        Graph graph = new Graph(5);

        assertThrows(IllegalArgumentException.class, () -> {
            graph.addEdge(0, 10, 5.0);  // Vertex 10 doesn't exist
        });
    }

    @Test
    public void testGetAdjList() {
        Graph graph = new Graph(3);

        graph.addEdge(0, 1, 10.0);
        graph.addEdge(0, 2, 5.0);

        var adjList = graph.getAdjList();

        assertEquals(3, adjList.size());
        assertEquals(2, adjList.get(0).size());
    }
}
