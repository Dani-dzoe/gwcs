package algorithms.graph;

import org.junit.jupiter.api.Test;
import datastructures.Graph;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class KruskalTest {

    @Test
    public void testFindMST() {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 10.0);
        graph.addEdge(0, 2, 5.0);
        graph.addEdge(1, 2, 2.0);
        graph.addEdge(1, 3, 1.0);
        graph.addEdge(2, 3, 9.0);

        List<Kruskal.Edge> mst = Kruskal.findMST(graph);

        assertEquals(3, mst.size());  // V-1 edges
    }

    @Test
    public void testMstWeight() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 1.0);
        graph.addEdge(0, 2, 4.0);
        graph.addEdge(1, 2, 2.0);
        graph.addEdge(1, 3, 6.0);
        graph.addEdge(2, 3, 3.0);

        double weight = Kruskal.mstWeight(graph);

        assertTrue(weight > 0);
    }
}
