package performance;

import model.AlgorithmRun;
import model.WasteRequest;
import util.Timer;
import util.MemoryMonitor;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Entry point for running Ghana Waste Collection System performance
 * experiments. This harness and the data now represents actual waste pickup
 * requests from campus locations instead of raw integers.
 *
 * The sortByUrgency() below is a stand-in for Part 2's real
 * sort implementations (selection/insertion/merge/quicksort). I am to swap it out
 * once their code lands -- nothing in ExperimentRunner needs to change,
 * since it only depends on the TimedAlgorithm/InputGenerator interfaces.
 *
 * Run with:
 *   javac model/*.java performance/*.java
 *   java performance.PerformanceRunner
 */
public class PerformanceRunner {

    // Campus locations used to generate realistic synthetic requests.
    private static final String[] CAMPUS_LOCATIONS = {
            "COMMONWEALTH_HALL", "LEGON_HALL", "VOLTA_HALL", "AKUAFO_HALL",
            "BALME_LIBRARY", "JQB_DEPT", "PENTAGON_HOSTEL", "TF_HOSTEL",
            "CENTRAL_CAFETERIA", "SPORTS_COMPLEX"
    };
    private static final String[] WASTE_TYPE = {"GENERAL", "ORGANIC", "RECYCLABLE"};
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final List<AlgorithmRun> store = new ArrayList<>();

    public static void main(String[] args) {
        PerformanceRunner runner = new PerformanceRunner();

        // Shrunk sizes for a fast local smoke test. Bump toward the brief's
        // full range (100..50,000) once running against real Part 2/3 code.
        int[] demoSizes = {100, 500, 1000, 2000, 25000, 50000};

        System.out.println("sorting pending pickup requests by urgency");
        runner.runExperiment(
                "SortByUrgency_PLACEHOLDER",
                demoSizes,
                PerformanceRunner::generatePendingRequests,
                PerformanceRunner::sortByUrgency,
                3 // brief requires >= 3 repeats, averaged
        );

        runner.exportTo("gwcs_performance_results_demo.csv");

        System.out.println("=== Stored " + runner.getStore().size() + " averaged run records ===");
        for (int i = 0; i < runner.getStore().size(); i++) {
            System.out.println(runner.getStore().get(i));
        }
    }

    /**
     * Generates a batch of synthetic pending waste pickup requests spread
     * across campus locations, with random fill levels (urgency).
     * Fixed seed per call for reproducibility of individual runs.
     */
    private static WasteRequest[] generatePendingRequests(int size) {
        Random rnd = new Random(42);
        WasteRequest[] requests = new WasteRequest[size];
        long now = System.currentTimeMillis() / 1000L;

        for (int i = 0; i < size; i++) {
            String locationId = CAMPUS_LOCATIONS[rnd.nextInt(CAMPUS_LOCATIONS.length)];
            String wasteType = WASTE_TYPE[rnd.nextInt(WASTE_TYPE.length)];
            int fillLevel = rnd.nextInt(101); // 0-100%
            long submitted = now - rnd.nextInt(3600); // submitted sometime in the last hour
            long deadline = submitted + 7200; // must be collected within 2 hours of submission

            requests[i] = new WasteRequest(
                    "REQ" + String.format("%06d", i),
                    locationId,
                    wasteType,
                    fillLevel,
                    submitted,
                    deadline
            );
        }
        return requests;
    }

    /**
     * PLACEHOLDER sort -- NOT the graded implementation. Part 2 owns the
     * real custom sorts (selection/insertion/merge/quicksort). This exists
     * only to prove the harness times and stores results correctly against
     * real domain objects.
     */
    private static void sortByUrgency(WasteRequest[] requests) {
        int n = requests.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                // descending: most urgent (highest fill level) first
                if (requests[j].urgencyScore() < requests[j + 1].urgencyScore()) {
                    WasteRequest tmp = requests[j];
                    requests[j] = requests[j + 1];
                    requests[j + 1] = tmp;
                }
            }
        }
    }

    /**
     * Runs a generic performance experiment.
     *
     * @param <T>              The type of data structure being tested
     * @param experimentName   The name of the experiment
     * @param sizes            The array of input sizes to test
     * @param generator        The function to generate data structure of given size
     * @param operation        The consumer to execute operations on the data structure
     * @param iterations       the number of iterations per size
     */
    public <T> void runExperiment(String experimentName,
                                   int[] sizes,
                                   Function<Integer, T> generator,
                                   Consumer<T> operation,
                                   int iterations) {
        System.out.println("Running experiment: " + experimentName);

        for (int size : sizes) {
            long totalTimeNs = 0;
            long totalMemoryKb = 0;

            for (int i = 0; i < iterations; i++) {
                Timer timer = new Timer();
                MemoryMonitor memMonitor = new MemoryMonitor();

                // Generate data structure
                memMonitor.snapshotBefore();
                timer.start();
                T dataStructure = generator.apply(size);
                timer.stop();
                long genTimeNs = timer.elapsedNs();

                // Perform operations
                timer = new Timer();
                timer.start();
                operation.accept(dataStructure);
                timer.stop();
                long opTimeNs = timer.elapsedNs();

                memMonitor.snapshotAfter();
                long memoryKb = memMonitor.usedDeltaKb();

                totalTimeNs += genTimeNs + opTimeNs;
                totalMemoryKb += memoryKb;
            }

            // Average over iterations
            long avgTimeNs = totalTimeNs / iterations;
            long avgMemoryKb = totalMemoryKb / iterations;

            // Store result
            String runId = UUID.randomUUID().toString();
            String dateRun = LocalDateTime.now().format(DATE_FORMATTER);
            AlgorithmRun run = new AlgorithmRun(runId, experimentName, size, avgTimeNs, avgMemoryKb, dateRun);
            store.add(run);

            System.out.printf("  Size: %7d | Time: %10d ns (%6.2f ms) | Memory: %6d KB%n",
                    size, avgTimeNs, avgTimeNs / 1_000_000.0, avgMemoryKb);
        }
    }

    /**
     * Gets the list of all recorded algorithm runs.
     */
    public List<AlgorithmRun> getStore() {
        return new ArrayList<>(store);
    }

    /**
     * Exports all results to a CSV file.
     *
     * @param filename Output CSV filename
     */
    public void exportTo(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Write header
            writer.write("runId,algorithmName,inputSize,timeNs,memoryKb,dateRun\n");

            // Write data rows
            for (AlgorithmRun run : store) {
                writer.write(run.toCsvRow() + "\n");
            }

            System.out.println("Results exported to " + filename);
        } catch (IOException e) {
            System.err.println("Error exporting results to " + filename + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}