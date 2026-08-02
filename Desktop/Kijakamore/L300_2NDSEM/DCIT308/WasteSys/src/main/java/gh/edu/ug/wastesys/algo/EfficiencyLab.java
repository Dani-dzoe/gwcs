package gh.edu.ug.wastesys.algo;

import gh.edu.ug.wastesys.model.AlgorithmRun;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class EfficiencyLab {
    private final SearchSortEngine searchSortEngine = new SearchSortEngine();

    public LabResult runSearchAndSortLab(List<Integer> inputSizes, long seed) {
        Random random = new Random(seed);
        List<String> csvLines = new ArrayList<>();
        csvLines.add("algorithm,inputSize,averageTimeNs");
        List<AlgorithmRun> runs = new ArrayList<>();

        int runId = 1;
        for (int inputSize : inputSizes) {
            List<Integer> data = buildSortedData(inputSize);
            long linear = averageTimeNs(3, () -> searchSortEngine.linearSearch(data, inputSize - 1));
            long binary = averageTimeNs(3, () -> searchSortEngine.binarySearch(data, inputSize - 1));
            csvLines.add("linear search," + inputSize + "," + linear);
            csvLines.add("binary search," + inputSize + "," + binary);
            runs.add(new AlgorithmRun(runId++, "Linear Search", inputSize, linear, 0L, LocalDateTime.now()));
            runs.add(new AlgorithmRun(runId++, "Binary Search", inputSize, binary, 0L, LocalDateTime.now()));

            List<Integer> shuffled = buildShuffledData(inputSize, random);
            long selection = averageTimeNs(3, () -> {
                List<Integer> copy = new ArrayList<>(shuffled);
                searchSortEngine.selectionSort(copy);
            });
            long insertion = averageTimeNs(3, () -> {
                List<Integer> copy = new ArrayList<>(shuffled);
                searchSortEngine.insertionSort(copy);
            });
            long merge = averageTimeNs(3, () -> searchSortEngine.mergeSort(shuffled));
            long quick = averageTimeNs(3, () -> searchSortEngine.quickSort(shuffled));
            csvLines.add("selection sort," + inputSize + "," + selection);
            csvLines.add("insertion sort," + inputSize + "," + insertion);
            csvLines.add("merge sort," + inputSize + "," + merge);
            csvLines.add("quicksort," + inputSize + "," + quick);
            runs.add(new AlgorithmRun(runId++, "Selection Sort", inputSize, selection, 0L, LocalDateTime.now()));
            runs.add(new AlgorithmRun(runId++, "Insertion Sort", inputSize, insertion, 0L, LocalDateTime.now()));
            runs.add(new AlgorithmRun(runId++, "Merge Sort", inputSize, merge, 0L, LocalDateTime.now()));
            runs.add(new AlgorithmRun(runId++, "Quicksort", inputSize, quick, 0L, LocalDateTime.now()));
        }

        return new LabResult(String.join(System.lineSeparator(), csvLines), runs);
    }

    public Path writeCsv(Path outputFile, String csvContent) {
        try {
            Files.createDirectories(outputFile.getParent());
            Files.writeString(outputFile, csvContent, StandardCharsets.UTF_8);
            return outputFile;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to write efficiency CSV", exception);
        }
    }

    private List<Integer> buildSortedData(int size) {
        List<Integer> values = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            values.add(i);
        }
        return values;
    }

    private List<Integer> buildShuffledData(int size, Random random) {
        List<Integer> values = buildSortedData(size);
        Collections.shuffle(values, random);
        return values;
    }

    private long averageTimeNs(int repetitions, Runnable action) {
        long total = 0L;
        for (int i = 0; i < repetitions; i++) {
            long start = System.nanoTime();
            action.run();
            total += System.nanoTime() - start;
        }
        return total / repetitions;
    }

    public record LabResult(String csvContent, List<AlgorithmRun> runs) {
    }
}