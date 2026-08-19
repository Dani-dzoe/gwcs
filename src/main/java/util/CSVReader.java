package util;

import model.AlgorithmRun;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads algorithm run results from a CSV file.
 * Parses CSV format: runId,algorithmName,inputSize,timeNs,memoryKb,dateRun
 */
public class CSVReader {

    private static final String EXPECTED_HEADER = "runId,algorithmName,inputSize,timeNs,memoryKb,dateRun";

    /**
     * Reads a CSV file and returns a list of AlgorithmRun objects.
     *
     * @param filePath path to the CSV file
     * @return list of parsed AlgorithmRun objects
     * @throws RuntimeException if file cannot be read or format is invalid
     */
    public static List<AlgorithmRun> read(String filePath) {
        List<AlgorithmRun> runs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new RuntimeException("CSV file is empty: " + filePath);
            }

            // Validate header
            if (!headerLine.trim().equals(EXPECTED_HEADER)) {
                throw new RuntimeException(
                        "Invalid CSV header. Expected: " + EXPECTED_HEADER +
                        ", but got: " + headerLine
                );
            }

            // Read data rows
            String line;
            int lineNum = 2; // line 1 is header
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    lineNum++;
                    continue; // Skip empty lines
                }

                try {
                    AlgorithmRun run = parseCsvLine(line);
                    runs.add(run);
                } catch (Exception e) {
                    throw new RuntimeException(
                            "Error parsing CSV line " + lineNum + ": " + line,
                            e
                    );
                }
                lineNum++;
            }

            System.out.println("Loaded " + runs.size() + " algorithm runs from " + filePath);
            return runs;

        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file: " + filePath, e);
        }
    }

    /**
     * Parses a single CSV line into an AlgorithmRun object.
     * Format: runId,algorithmName,inputSize,timeNs,memoryKb,dateRun
     *
     * @param line the CSV line to parse
     * @return parsed AlgorithmRun object
     */
    private static AlgorithmRun parseCsvLine(String line) {
        String[] parts = line.split(",", -1); // -1 keeps empty trailing fields

        if (parts.length != 6) {
            throw new IllegalArgumentException(
                    "Expected 6 fields, got " + parts.length + ": " + line
            );
        }

        try {
            String runId = parts[0].trim();
            String algorithmName = parts[1].trim();
            int inputSize = Integer.parseInt(parts[2].trim());
            long timeNs = Long.parseLong(parts[3].trim());
            long memoryKb = Long.parseLong(parts[4].trim());
            String dateRun = parts[5].trim();

            return new AlgorithmRun(runId, algorithmName, inputSize, timeNs, memoryKb, dateRun);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric value in CSV line: " + line, e);
        }
    }
}
