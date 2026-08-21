package performance;

import algorithms.graph.BFS;
import algorithms.graph.DFS;
import algorithms.graph.Dijkstra;
import datastructures.Graph;
import util.Timer;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphExperiment {

    public void runExperiment() {
        Timer timer = new Timer();
        System.out.println("\n=== Graph Algorithm Performance Experiment ===\n");

        List<String[]> results = new ArrayList<>();
        results.add(new String[]{"Vertices", "BFS (ms)", "DFS (ms)", "Dijkstra (ms)"});

        int[] sizes = {50, 100, 200, 500};

        for (int v : sizes) {
            Graph graph = generateRandomGraph(v, v * 3);

            timer.start();
            BFS.traverse(graph, 0);
            timer.stop();
            double bfsTime = timer.elapsedMillis();

            timer.start();
            DFS.traverse(graph, 0);
            timer.stop();
            double dfsTime = timer.elapsedMillis();

            timer.start();
            Dijkstra.shortestPaths(graph, 0);
            timer.stop();
            double dijkstraTime = timer.elapsedMillis();

            results.add(new String[]{
                String.valueOf(v),
                String.format("%.3f", bfsTime),
                String.format("%.3f", dfsTime),
                String.format("%.3f", dijkstraTime)
            });

            System.out.printf("Vertices %4d: BFS %8.3f ms, DFS %8.3f ms, Dijkstra %8.3f ms\n",
                            v, bfsTime, dfsTime, dijkstraTime);
        }

        saveResults("experiments/raw/graph_results.csv", results);
    }

    private Graph generateRandomGraph(int vertices, int edges) {
        Graph graph = new Graph(vertices);
        int added = 0;
        while (added < edges) {
            int from = (int)(Math.random() * vertices);
            int to = (int)(Math.random() * vertices);
            if (from != to) {
                graph.addEdge(from, to, Math.random() * 10 + 1);
                added++;
            }
        }
        return graph;
    }

    private void saveResults(String filename, List<String[]> results) {
        try (FileWriter fw = new FileWriter(filename)) {
            for (String[] row : results) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    sb.append(row[i]);
                    if (i < row.length - 1) sb.append(",");
                }
                fw.write(sb.toString() + "\n");
            }
            System.out.println("Results saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving results: " + e.getMessage());
        }
    }
}
