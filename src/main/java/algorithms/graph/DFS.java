package algorithms.graph;

import datastructures.Graph;
import datastructures.StackDS;
import java.util.*;

/**
 * DFS - Depth-First Search algorithm
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity: O(V + E)
 * Space Complexity: O(V)
 */
public class DFS {

    /**
     * DFS traversal (iterative)
     */
    public static List<Integer> traverse(Graph graph, int start) {
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[graph.vertices()];
        StackDS<Integer> stack = new StackDS<>();

        stack.push(start);

        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (!visited[current]) {
                visited[current] = true;
                result.add(current);

                List<Integer> neighbors = graph.getNeighbors(current);
                Collections.reverse(neighbors);

                for (int neighbor : neighbors) {
                    if (!visited[neighbor]) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        return result;
    }

    /**
     * DFS traversal (recursive)
     */
    public static List<Integer> traverseRecursive(Graph graph, int start) {
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[graph.vertices()];

        dfsRecursive(graph, start, visited, result);

        return result;
    }

    private static void dfsRecursive(Graph graph, int vertex, boolean[] visited, List<Integer> result) {
        visited[vertex] = true;
        result.add(vertex);

        for (int neighbor : graph.getNeighbors(vertex)) {
            if (!visited[neighbor]) {
                dfsRecursive(graph, neighbor, visited, result);
            }
        }
    }

    /**
     * Check if path exists
     */
    public static boolean hasPath(Graph graph, int start, int end) {
        if (start == end) {
            return true;
        }

        boolean[] visited = new boolean[graph.vertices()];

        return hasPathRecursive(graph, start, end, visited);
    }

    private static boolean hasPathRecursive(Graph graph, int current, int end, boolean[] visited) {
        visited[current] = true;

        if (current == end) {
            return true;
        }

        for (int neighbor : graph.getNeighbors(current)) {
            if (!visited[neighbor]) {
                if (hasPathRecursive(graph, neighbor, end, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Find connected components
     * @return number of connected components
     */
    public static int countComponents(Graph graph) {
        boolean[] visited = new boolean[graph.vertices()];
        int components = 0;

        for (int i = 0; i < graph.vertices(); i++) {
            if (!visited[i]) {
                components++;
                dfsRecursive(graph, i, visited, new ArrayList<>());
            }
        }

        return components;
    }
}
