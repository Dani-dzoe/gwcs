package performance;

import algorithms.sorting.InsertionSort;
import algorithms.sorting.MergeSort;
import algorithms.sorting.QuickSort;
import algorithms.sorting.SelectionSort;
import util.Timer;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SortingExperiment {

    public void runExperiment() {
        Timer timer = new Timer();
        System.out.println("\n=== Sorting Algorithm Performance Experiment ===\n");

        List<String[]> results = new ArrayList<>();
        results.add(new String[]{"Input Size", "Selection Sort (ms)", "Insertion Sort (ms)", 
                                "Merge Sort (ms)", "Quick Sort (ms)"});

        int[] sizes = {100, 500, 1000, 2000, 5000};

        for (int size : sizes) {
            // 1. Generate generic Integer[] arrays for Selection, Merge, and Quick Sort
            Integer[] arrGeneric1 = generateRandomIntegerArray(size);
            Integer[] arrGeneric3 = convertToIntegerArray(arrGeneric1);
            Integer[] arrGeneric4 = convertToIntegerArray(arrGeneric1);

            // 2. Generate a separate primitive int[] array for Insertion Sort
            int[] arrPrimitive2 = convertToPrimitiveArray(arrGeneric1);

            timer.start();
            SelectionSort.sort(arrGeneric1);
            timer.stop();
            double selectionTime = timer.elapsedMillis();

            timer.start();
            // FIXED: InsertionSort now gets the primitive int[] it expects
            InsertionSort.sort(arrPrimitive2);
            timer.stop();
            double insertionTime = timer.elapsedMillis();

            timer.start();
            MergeSort.sort(arrGeneric3);
            timer.stop();
            double mergeTime = timer.elapsedMillis();

            timer.start();
            QuickSort.sort(arrGeneric4);
            timer.stop();
            double quickTime = timer.elapsedMillis();

            results.add(new String[]{
                String.valueOf(size),
                String.format("%.3f", selectionTime),
                String.format("%.3f", insertionTime),
                String.format("%.3f", mergeTime),
                String.format("%.3f", quickTime)
            });

            System.out.printf("Size %5d: Selection %8.3f, Insertion %8.3f, Merge %8.3f, Quick %8.3f\n",
                            size, selectionTime, insertionTime, mergeTime, quickTime);
        }

        saveResults("experiments/raw/sorting_results.csv", results);
    }

    private Integer[] generateRandomIntegerArray(int size) {
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) arr[i] = (int)(Math.random() * size * 10);
        return arr;
    }

    // Helper to safely clone an Integer array
    private Integer[] convertToIntegerArray(Integer[] source) {
        Integer[] arr = new Integer[source.length];
        System.arraycopy(source, 0, arr, 0, source.length);
        return arr;
    }

    // Helper to turn an Integer[] array into a primitive int[] array
    private int[] convertToPrimitiveArray(Integer[] source) {
        int[] arr = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            arr[i] = source[i];
        }
        return arr;
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

