package performance;

import algorithms.searching.BinarySearch;
import algorithms.searching.LinearSearch;
import util.Timer;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchExperiment {

    public void runExperiment() {
        Timer timer = new Timer();
        System.out.println("\n=== Search Algorithm Performance Experiment ===\n");

        List<String[]> results = new ArrayList<>();
        results.add(new String[]{"Input Size", "Linear Search (ms)", "Binary Search (ms)", "Ratio"});

        int[] sizes = {100, 500, 1000, 5000, 10000};

        for (int size : sizes) {
            // 1. Create an Integer[] object array for LinearSearch (which uses Generics <T>)
            Integer[] objectArray = new Integer[size];
            for (int i = 0; i < size; i++) objectArray[i] = i;

            // 2. Create an int[] primitive array for BinarySearch (which uses int[])
            int[] primitiveArray = new int[size];
            for (int i = 0; i < size; i++) primitiveArray[i] = i;

            Integer targetObject = size / 2;
            int targetPrimitive = size / 2;
            int runs = 100;

            timer.start();
            // Pass the Integer[] array and Integer object target here
            for (int i = 0; i < runs; i++) LinearSearch.search(objectArray, targetObject);
            timer.stop();
            double linearTime = timer.elapsedMillis();

            timer.start();
            // Pass the primitive int[] array and int target here
            for (int i = 0; i < runs; i++) BinarySearch.search(primitiveArray, targetPrimitive);
            timer.stop();
            double binaryTime = timer.elapsedMillis();

            double ratio = linearTime / (binaryTime > 0 ? binaryTime : 0.001);

            results.add(new String[]{
                String.valueOf(size),
                String.format("%.3f", linearTime),
                String.format("%.3f", binaryTime),
                String.format("%.2f", ratio)
            });

            System.out.printf("Size %5d: Linear %8.3f ms, Binary %8.3f ms, Ratio %6.2fx\n",
                            size, linearTime, binaryTime, ratio);
        }

        saveResults("experiments/raw/search_results.csv", results);
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

