package algorithms.graph;

import datastructures.Graph;
import datastructures.PriorityQueueDS;
import java.util.*;

/**
 * Prim - Prim's minimum spanning tree algorithm
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity: O((V + E) log V)
 * Space Complexity: O(V)
 */
public class Prim {

    private static class Edge implements Comparable<Edge> {
        int from;
        int to;
        double weight;

        Edge(int from, int to, double weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Double.compare(this.weight, other.weight);
        }
    }

    /**
     * Find MST edges
     * @return list of MST edges
     */
    public static List<Edge> findMST(Graph graph, int start) {
        List<Edge> mst = new ArrayList<>();
        boolean[] inMST = new boolean[graph.vertices()];
        PriorityQueueDS<Edge> pq = new PriorityQueueDS<>();

        inMST[start] = true;

        for (Graph.Edge edge : graph.getAdjList().get(start)) {
            pq.enqueue(new Edge(start, edge.to, edge.weight));
        }

        while (!pq.isEmpty() && mst.size() < graph.vertices() - 1) {
            Edge minEdge = pq.dequeue();

            if (inMST[minEdge.to]) {
                continue;
            }

            mst.add(minEdge);
            inMST[minEdge.to] = true;

            for (Graph.Edge edge : graph.getAdjList().get(minEdge.to)) {
                if (!inMST[edge.to]) {
                    pq.enqueue(new Edge(minEdge.to, edge.to, edge.weight));
                }
            }
        }

        return mst;
    }

    /**
     * Calculate MST weight
     */
    public static double mstWeight(Graph graph, int start) {
        List<Edge> mst = findMST(graph, start);
        double totalWeight = 0.0;

        for (Edge edge : mst) {
            totalWeight += edge.weight;
        }

        return totalWeight;
    }
}
