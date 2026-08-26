package algorithms.graph;

import org.junit.jupiter.api.Test;
import datastructures.Graph;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

/**
 * DijkstraTest - Unit tests for Dijkstra
 */
public class DijkstraTest {

    @Test
    public void testShortestPaths() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 10.0);
        graph.addEdge(0, 2, 5.0);
        graph.addEdge(1, 2, 2.0);
        graph.addEdge(1, 3, 1.0);
        graph.addEdge(2, 3, 9.0);

        Map<Integer, Double> distances = Dijkstra.shortestPaths(graph, 0);

        assertEquals(0.0, distances.get(0));
        assertEquals(7.0, distances.get(1));
        assertEquals(5.0, distances.get(2));
        assertEquals(8.0, distances.get(3));  // 0->1->3 = 10+1
    }

    @Test
    public void testShortestPath() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 5.0);
        graph.addEdge(1, 2, 3.0);
        graph.addEdge(0, 2, 10.0);

        double distance = Dijkstra.shortestPath(graph, 0, 2);

        assertEquals(8.0, distance);  // 0->1->2 = 5+3
    }

    @Test
    public void testUnreachable() {
        Graph graph = new Graph(3);
        graph.addEdge(0, 1, 5.0);
        // Vertex 2 is unreachable from 0

        double distance = Dijkstra.shortestPath(graph, 0, 2);

        assertEquals(Double.POSITIVE_INFINITY, distance);
    }
}
