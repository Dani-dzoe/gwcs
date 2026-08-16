package algorithms;

import model.Truck;
import model.WasteRequest;

/**
 * Formats GreedyTruckAssignment and KnapsackDP executions as printable trace
 * tables for the report / oral defense. Kept separate from the algorithm
 * classes themselves (which must not contain console output) — this is a
 * reporting/demo utility, not part of the assessed algorithm logic.
 */
public class TraceTablePrinter {

    /**
     * Builds a step-by-step trace of a GreedyTruckAssignment run: the
     * priority-sorted processing order, and for each request, which truck
     * was chosen and why (or why none was available).
     */
    public static String traceGreedy(WasteRequest[] originalRequests, Truck[] trucks,
                                      GreedyTruckAssignment.Assignment[] results) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-6s %-10s %-10s %-8s %-10s %-12s%n",
                "Step", "ReqID", "Priority", "Weight", "Truck", "Distance"));
        sb.append("-".repeat(60)).append(System.lineSeparator());

        for (int i = 0; i < results.length; i++) {
            GreedyTruckAssignment.Assignment a = results[i];
            WasteRequest r = a.getRequest();
            String truckLabel = a.isAssigned() ? ("T" + a.getTruck().getTruckId()) : "NONE";
            String distanceLabel = a.isAssigned() ? String.valueOf(a.getDistance()) : "-";
            sb.append(String.format("%-6d %-10d %-10d %-8.1f %-10s %-12s%n",
                    i + 1, r.getRequestId(), r.getPriority(), r.getEstimatedVolumeKg(),
                    truckLabel, distanceLabel));
        }
        return sb.toString();
    }

    /**
     * Formats the raw KnapsackDP dp[i][w] table, and lists which requests
     * were selected by the backtrack step.
     */
    public static String traceKnapsack(WasteRequest[] requests, int capacityKg, KnapsackDP.Result result) {
        int[][] dp = result.getDpTable();
        StringBuilder sb = new StringBuilder();

        sb.append("DP Table (rows = requests considered 0..n, columns = capacity 0..W)").append(System.lineSeparator());
        sb.append(String.format("%-6s", "i\\w"));
        for (int w = 0; w <= capacityKg; w++) {
            sb.append(String.format("%4d", w));
        }
        sb.append(System.lineSeparator());

        for (int i = 0; i < dp.length; i++) {
            String rowLabel = (i == 0) ? "0 (-)" : i + " (R" + requests[i - 1].getRequestId() + ")";
            sb.append(String.format("%-6s", rowLabel));
            for (int w = 0; w <= capacityKg; w++) {
                sb.append(String.format("%4d", dp[i][w]));
            }
            sb.append(System.lineSeparator());
        }

        sb.append(System.lineSeparator()).append("Selected requests: ");
        for (WasteRequest r : result.getSelectedRequests()) {
            sb.append("R").append(r.getRequestId()).append(" ");
        }
        sb.append(System.lineSeparator()).append("Total value: ").append(result.getTotalValue());
        return sb.toString();
    }
}
