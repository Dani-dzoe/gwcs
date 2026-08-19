package util;

import model.AlgorithmRun;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Writes algorithm run results to a CSV file (Section 9-iii: "Export results
 * to CSV and plot line graphs"). This is raw data, not screenshots --
 * required per Section 8-vi / Section 12.
 */
public class CSVWriter {

    private static final String HEADER = "runId,algorithmName,inputSize,timeNs,memoryKb,dateRun";

    /**
     * Exports a list of algorithm runs to CSV format.
     * 
     * @param runs the list of AlgorithmRun results to export
     * @param filePath the output CSV file path
     */
    public static void export(List<AlgorithmRun> runs, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println(HEADER);
            for (AlgorithmRun run : runs) {
                writer.println(run.toCsvRow());
            }
            System.out.println("Exported " + runs.size() + " rows to " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to export CSV to " + filePath, e);
        }
    }
}
