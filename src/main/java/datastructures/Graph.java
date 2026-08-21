package datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Graph - Graph data structure using adjacency list
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class Graph {

    /**
     * Edge - Represents a weighted edge
     */
    public static class Edge {
        public int to;
        public double weight;

        public Edge(int to, double weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    private int vertices;
    private boolean directed;
    private Map<Integer, List<Edge>> adjList;
    private int edgeCount;

    public Graph(int vertices) {
        this(vertices, false);
    }

    public Graph(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;
        this.adjList = new HashMap<>();
        this.edgeCount = 0;

        for (int i = 0; i < vertices; i++) {
            adjList.put(i, new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, double weight) {
        if (from < 0 || from >= vertices || to < 0 || to >= vertices) {
            throw new IllegalArgumentException("Invalid vertex");
        }
        adjList.get(from).add(new Edge(to, weight));
        edgeCount++;
        if (!directed) {
            adjList.get(to).add(new Edge(from, weight));
            edgeCount++;
        }
    }

    public void addEdge(int from, int to) {
        addEdge(from, to, 1.0);
    }

    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (Edge edge : adjList.get(vertex)) {
            neighbors.add(edge.to);
        }
        return neighbors;
    }

    public double getWeight(int from, int to) {
        for (Edge edge : adjList.get(from)) {
            if (edge.to == to) {
                return edge.weight;
            }
        }
        return Double.POSITIVE_INFINITY;
    }

    public int vertices() {
        return vertices;
    }

    public int edges() {
        return directed ? edgeCount : edgeCount / 2;
    }

    public Map<Integer, List<Edge>> getAdjList() {
        return adjList;
    }

    public boolean isDirected() {
        return directed;
    }
}
