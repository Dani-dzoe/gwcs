package gh.edu.ug.wastesys.algo;

import gh.edu.ug.wastesys.ds.MinHeapPriorityQueue;
import gh.edu.ug.wastesys.ds.DisjointSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class GraphEngine {
    private final Map<Integer, Map<Integer, Double>> adjacencyList = new LinkedHashMap<>();
    private double[][] adjacencyMatrix = new double[1][1];
    private int maxNodeId;

    public void addEdge(int from, int to, double weight) {
        adjacencyList.computeIfAbsent(from, key -> new LinkedHashMap<>()).put(to, weight);
        adjacencyList.computeIfAbsent(to, key -> new LinkedHashMap<>()).put(from, weight);
        ensureMatrixCapacity(Math.max(from, to));
        adjacencyMatrix[from][to] = weight;
        adjacencyMatrix[to][from] = weight;
        maxNodeId = Math.max(maxNodeId, Math.max(from, to));
    }

    public List<Integer> bfs(int start) {
        List<Integer> order = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            int current = queue.remove();
            order.add(current);
            for (int neighbor : adjacencyList.getOrDefault(current, Map.of()).keySet()) {
                if (visited.add(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }
        return order;
    }

    public List<Integer> dfs(int start) {
        List<Integer> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        dfs(start, visited, order);
        return order;
    }

    public List<Integer> bfsMatrix(int start) {
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[maxNodeId + 1];
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;
        while (!queue.isEmpty()) {
            int current = queue.removeFirst();
            order.add(current);
            for (int neighbor = 1; neighbor <= maxNodeId; neighbor++) {
                if (adjacencyMatrix[current][neighbor] > 0 && !visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        return order;
    }

    public List<Integer> dfsMatrix(int start) {
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[maxNodeId + 1];
        dfsMatrix(start, visited, order);
        return order;
    }

    public MstResult primMst(int start) {
        if (maxNodeId == 0) {
            return new MstResult(List.of(), 0.0);
        }
        boolean[] visited = new boolean[maxNodeId + 1];
        MinHeapPriorityQueue<Edge> queue = new MinHeapPriorityQueue<>();
        List<int[]> edges = new ArrayList<>();
        double totalWeight = 0.0;
        visited[start] = true;
        pushMatrixEdges(start, visited, queue);
        while (!queue.isEmpty() && edges.size() < adjacencyList.size() - 1) {
            Edge edge = queue.extractMin();
            if (visited[edge.to]) {
                continue;
            }
            visited[edge.to] = true;
            edges.add(new int[]{edge.from, edge.to, (int) Math.round(edge.weight)});
            totalWeight += edge.weight;
            pushMatrixEdges(edge.to, visited, queue);
        }
        return new MstResult(edges, totalWeight);
    }

    private void dfs(int current, Set<Integer> visited, List<Integer> order) {
        if (!visited.add(current)) {
            return;
        }
        order.add(current);
        for (int neighbor : adjacencyList.getOrDefault(current, Map.of()).keySet()) {
            dfs(neighbor, visited, order);
        }
    }

    public PathResult dijkstra(int start, int target) {
        Map<Integer, Double> distance = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        MinHeapPriorityQueue<NodeDistance> queue = new MinHeapPriorityQueue<>();
        for (int node : adjacencyList.keySet()) {
            distance.put(node, Double.POSITIVE_INFINITY);
        }
        distance.put(start, 0.0);
        queue.insert(new NodeDistance(start, 0.0));
        while (!queue.isEmpty()) {
            NodeDistance current = queue.extractMin();
            if (!visited.add(current.node)) {
                continue;
            }
            if (current.node == target) {
                break;
            }
            for (Map.Entry<Integer, Double> edge : adjacencyList.getOrDefault(current.node, Map.of()).entrySet()) {
                double newDistance = distance.get(current.node) + edge.getValue();
                if (newDistance < distance.getOrDefault(edge.getKey(), Double.POSITIVE_INFINITY)) {
                    distance.put(edge.getKey(), newDistance);
                    previous.put(edge.getKey(), current.node);
                    queue.insert(new NodeDistance(edge.getKey(), newDistance));
                }
            }
        }
        return new PathResult(distance.getOrDefault(target, Double.POSITIVE_INFINITY), reconstructPath(previous, start, target));
    }

    public List<int[]> kruskalMst() {
        List<int[]> edges = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (Map.Entry<Integer, Map<Integer, Double>> entry : adjacencyList.entrySet()) {
            for (Map.Entry<Integer, Double> neighbor : entry.getValue().entrySet()) {
                String key = entry.getKey() < neighbor.getKey()
                        ? entry.getKey() + ":" + neighbor.getKey()
                        : neighbor.getKey() + ":" + entry.getKey();
                if (seen.add(key)) {
                    edges.add(new int[]{entry.getKey(), neighbor.getKey(), (int) Math.round(neighbor.getValue())});
                }
            }
        }
        edges.sort((a, b) -> Integer.compare(a[2], b[2]));
        List<int[]> mst = new ArrayList<>();
        DisjointSet disjointSet = new DisjointSet(maxNodeId + 1);
        for (int[] edge : edges) {
            if (disjointSet.find(edge[0]) != disjointSet.find(edge[1])) {
                disjointSet.union(edge[0], edge[1]);
                mst.add(edge);
            }
        }
        return mst;
    }

    public double[][] adjacencyMatrix() {
        double[][] copy = new double[maxNodeId + 1][maxNodeId + 1];
        for (int i = 0; i <= maxNodeId; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, copy[i], 0, maxNodeId + 1);
        }
        return copy;
    }

    private List<Integer> reconstructPath(Map<Integer, Integer> previous, int start, int target) {
        LinkedList<Integer> path = new LinkedList<>();
        Integer current = target;
        while (current != null) {
            path.addFirst(current);
            if (current == start) {
                break;
            }
            current = previous.get(current);
        }
        if (path.isEmpty() || path.getFirst() != start) {
            return List.of();
        }
        return path;
    }

    private void ensureMatrixCapacity(int requiredNode) {
        if (requiredNode < adjacencyMatrix.length) {
            return;
        }
        int newSize = Math.max(requiredNode + 1, adjacencyMatrix.length * 2);
        double[][] expanded = new double[newSize][newSize];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, expanded[i], 0, adjacencyMatrix[i].length);
        }
        adjacencyMatrix = expanded;
    }

    private void dfsMatrix(int current, boolean[] visited, List<Integer> order) {
        if (current < 0 || current >= visited.length || visited[current]) {
            return;
        }
        visited[current] = true;
        order.add(current);
        for (int neighbor = 1; neighbor <= maxNodeId; neighbor++) {
            if (adjacencyMatrix[current][neighbor] > 0) {
                dfsMatrix(neighbor, visited, order);
            }
        }
    }

    private void pushMatrixEdges(int from, boolean[] visited, MinHeapPriorityQueue<Edge> queue) {
        for (int neighbor = 1; neighbor <= maxNodeId; neighbor++) {
            if (adjacencyMatrix[from][neighbor] > 0 && !visited[neighbor]) {
                queue.insert(new Edge(from, neighbor, adjacencyMatrix[from][neighbor]));
            }
        }
    }

    public record PathResult(double distance, List<Integer> path) {
    }

    public record MstResult(List<int[]> edges, double totalWeight) {
    }

    private record NodeDistance(int node, double distance) implements Comparable<NodeDistance> {
        @Override
        public int compareTo(NodeDistance other) {
            return Double.compare(distance, other.distance);
        }
    }

    private record Edge(int from, int to, double weight) implements Comparable<Edge> {
        @Override
        public int compareTo(Edge other) {
            return Double.compare(weight, other.weight);
        }
    }
}
