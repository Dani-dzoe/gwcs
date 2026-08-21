package algorithms.graph;

import datastructures.Graph;
import datastructures.PriorityQueueDS;
import java.util.*;

/**
 * Dijkstra - Dijkstra's shortest path algorithm
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity: O((V + E) log V)
 * Space Complexity: O(V)
 */
public class Dijkstra {

    private static class Node implements Comparable<Node> {
        int id;
        double distance;

        Node(int id, double distance) {
            this.id = id;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.distance, other.distance);
        }
    }

    /**
     * Find shortest paths from start
     * @return map of distances
     */
    public static Map<Integer, Double> shortestPaths(Graph graph, int start) {
        Map<Integer, Double> distances = new HashMap<>();
        PriorityQueueDS<Node> pq = new PriorityQueueDS<>();

        for (int i = 0; i < graph.vertices(); i++) {
            distances.put(i, Double.POSITIVE_INFINITY);
        }

        distances.put(start, 0.0);
        pq.enqueue(new Node(start, 0.0));

        while (!pq.isEmpty()) {
            Node current = pq.dequeue();

            if (current.distance > distances.get(current.id)) {
                continue;
            }

            for (Graph.Edge edge : graph.getAdjList().get(current.id)) {
                double newDist = distances.get(current.id) + edge.weight;

                if (newDist < distances.get(edge.to)) {
                    distances.put(edge.to, newDist);
                    pq.enqueue(new Node(edge.to, newDist));
                }
            }
        }

        return distances;
    }

    /**
     * Find shortest path from start to end
     * @return distance or Double.POSITIVE_INFINITY if unreachable
     */
    public static double shortestPath(Graph graph, int start, int end) {
        Map<Integer, Double> distances = shortestPaths(graph, start);
        return distances.get(end);
    }

    /**
     * Find shortest path with predecessors
     * @return map of [distance, predecessor]
     */
    public static Map<Integer, int[]> shortestPathWithPredecessors(Graph graph, int start) {
        Map<Integer, Double> distances = new HashMap<>();
        Map<Integer, Integer> predecessors = new HashMap<>();
        PriorityQueueDS<Node> pq = new PriorityQueueDS<>();

        for (int i = 0; i < graph.vertices(); i++) {
            distances.put(i, Double.POSITIVE_INFINITY);
            predecessors.put(i, -1);
        }

        distances.put(start, 0.0);
        pq.enqueue(new Node(start, 0.0));

        while (!pq.isEmpty()) {
            Node current = pq.dequeue();

            if (current.distance > distances.get(current.id)) {
                continue;
            }

            for (Graph.Edge edge : graph.getAdjList().get(current.id)) {
                double newDist = distances.get(current.id) + edge.weight;

                if (newDist < distances.get(edge.to)) {
                    distances.put(edge.to, newDist);
                    predecessors.put(edge.to, current.id);
                    pq.enqueue(new Node(edge.to, newDist));
                }
            }
        }

        Map<Integer, int[]> result = new HashMap<>();
        for (int i = 0; i < graph.vertices(); i++) {
            result.put(i, new int[]{distances.get(i).intValue(), predecessors.get(i)});
        }

        return result;
    }
}
