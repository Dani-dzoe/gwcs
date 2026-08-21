package performance;

import datastructures.Heap;
import util.Timer;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HeapExperiment {

    public void runExperiment() {
        Timer timer = new Timer();
        System.out.println("\n=== Heap Performance Experiment ===\n");

        List<String[]> results = new ArrayList<>();
        results.add(new String[]{"Input Size", "Insert Time (ms)", "Extract Min Time (ms)"});

        int[] sizes = {100, 500, 1000, 5000, 10000};

        for (int size : sizes) {
            Heap<Integer> heap = new Heap<>();
            timer.start();
            for (int i = 0; i < size; i++) heap.insert((int)(Math.random() * size * 10));
            timer.stop();
            double insertTime = timer.elapsedMillis();

            timer.start();
            while (!heap.isEmpty()) heap.extractMin();
            timer.stop();
            double extractTime = timer.elapsedMillis();

            results.add(new String[]{
                String.valueOf(size),
                String.format("%.3f", insertTime),
                String.format("%.3f", extractTime)
            });

            System.out.printf("Size %5d: Insert %8.3f ms, Extract %8.3f ms\n",
                            size, insertTime, extractTime);
        }

        saveResults("experiments/raw/heap_results.csv", results);
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
