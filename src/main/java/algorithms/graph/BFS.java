package algorithms.graph;

import datastructures.Graph;
import datastructures.QueueDS;
import java.util.*;

/**
 * BFS - Breadth-First Search algorithm
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity: O(V + E)
 * Space Complexity: O(V)
 */
public class BFS {

    /**
     * BFS traversal
     * @return list of vertices in BFS order
     */
    public static List<Integer> traverse(Graph graph, int start) {
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[graph.vertices()];
        QueueDS<Integer> queue = new QueueDS<>();

        queue.enqueue(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int current = queue.dequeue();
            result.add(current);

            for (int neighbor : graph.getNeighbors(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.enqueue(neighbor);
                }
            }
        }

        return result;
    }

    /**
     * BFS to find shortest path (unweighted graph)
     * @return map of distances from start
     */
    public static Map<Integer, Integer> shortestPath(Graph graph, int start) {
        Map<Integer, Integer> distances = new HashMap<>();
        boolean[] visited = new boolean[graph.vertices()];
        QueueDS<Integer> queue = new QueueDS<>();

        for (int i = 0; i < graph.vertices(); i++) {
            distances.put(i, Integer.MAX_VALUE);
        }

        queue.enqueue(start);
        visited[start] = true;
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            int current = queue.dequeue();

            for (int neighbor : graph.getNeighbors(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.enqueue(neighbor);
                    distances.put(neighbor, distances.get(current) + 1);
                }
            }
        }

        return distances;
    }

    /**
     * Check if path exists
     */
    public static boolean hasPath(Graph graph, int start, int end) {
        if (start == end) {
            return true;
        }

        boolean[] visited = new boolean[graph.vertices()];
        QueueDS<Integer> queue = new QueueDS<>();

        queue.enqueue(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int current = queue.dequeue();

            for (int neighbor : graph.getNeighbors(current)) {
                if (neighbor == end) {
                    return true;
                }
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.enqueue(neighbor);
                }
            }
        }

        return false;
    }
}
