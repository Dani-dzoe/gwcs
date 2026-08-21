package algorithms.graph;

import datastructures.Graph;
import datastructures.DisjointSet;
import datastructures.PriorityQueueDS;
import java.util.*;

/**
 * Kruskal - Kruskal's minimum spanning tree algorithm
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity: O(E log E)
 * Space Complexity: O(V)
 */
public class Kruskal {

    public static class Edge implements Comparable<Edge> {
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
    public static List<Edge> findMST(Graph graph) {
        List<Edge> mst = new ArrayList<>();
        PriorityQueueDS<Edge> pq = new PriorityQueueDS<>();
        DisjointSet<Integer> ds = new DisjointSet<>();

        for (int i = 0; i < graph.vertices(); i++) {
            ds.makeSet(i);
        }

        for (Map.Entry<Integer, List<Graph.Edge>> entry : graph.getAdjList().entrySet()) {
            for (Graph.Edge edge : entry.getValue()) {
                pq.enqueue(new Edge(entry.getKey(), edge.to, edge.weight));
            }
        }

        while (!pq.isEmpty() && mst.size() < graph.vertices() - 1) {
            Edge minEdge = pq.dequeue();

            if (ds.find(minEdge.from) != ds.find(minEdge.to)) {
                ds.union(minEdge.from, minEdge.to);
                mst.add(minEdge);
            }
        }

        return mst;
    }

    /**
     * Calculate MST weight
     */
    public static double mstWeight(Graph graph) {
        List<Edge> mst = findMST(graph);
        double totalWeight = 0.0;

        for (Edge edge : mst) {
            totalWeight += edge.weight;
        }

        return totalWeight;
    }
}
