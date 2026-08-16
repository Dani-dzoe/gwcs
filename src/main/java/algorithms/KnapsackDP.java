package algorithms;

import model.WasteRequest;

/**
 * 0/1 Knapsack Dynamic Programming — Waste Request Selection.
 *
 * Given one truck with a fixed integer capacity (kg) and a set of candidate
 * waste requests — each with a weight (estimated load) and a value (urgency
 * score) — selects the subset of requests that maximizes total value served
 * without exceeding the truck's capacity.
 *
 * Unlike GreedyTruckAssignment, which commits to the nearest job first, this
 * considers all candidate requests together and is guaranteed to find the
 * value-maximizing subset for a single truck. See the counterexample
 * analysis for a scenario where greedy's "closest job first" choice results
 * in strictly less total value served than this optimal selection.
 *
 * Time Complexity:  O(n * W), n = number of requests, W = truck capacity (kg, integer)
 * Space Complexity: O(n * W) for the DP table, kept on the result for trace-table evidence
 */
public class KnapsackDP {

    /**
     * Result of a knapsack solve: which requests were selected, the total
     * value achieved, and the DP table (kept for the trace table / correctness
     * writeup, not needed for normal use).
     */
    public static class Result {
        private final int totalValue;
        private final WasteRequest[] selectedRequests;
        private final int[][] dpTable;

        public Result(int totalValue, WasteRequest[] selectedRequests, int[][] dpTable) {
            this.totalValue = totalValue;
            this.selectedRequests = selectedRequests;
            this.dpTable = dpTable;
        }

        public int getTotalValue() {
            return totalValue;
        }

        public WasteRequest[] getSelectedRequests() {
            return selectedRequests;
        }

        public int[][] getDpTable() {
            return dpTable;
        }
    }

    /**
     * Solves 0/1 knapsack for a single truck.
     *
     * @param requests    candidate waste requests, must not be null
     * @param capacityKg  truck capacity in whole kg, must be non-negative
     * @return the Result containing the optimal subset and total value
     */
    public Result solve(WasteRequest[] requests, int capacityKg) {
        if (requests == null) {
            throw new IllegalArgumentException("requests cannot be null");
        }
        if (capacityKg < 0) {
            throw new IllegalArgumentException("capacityKg cannot be negative, got: " + capacityKg);
        }

        int n = requests.length;
        int[] weights = new int[n];
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            weights[i] = (int) Math.round(requests[i].getEstimatedVolumeKg());
            values[i] = requests[i].getUrgencyScore();
        }

        int[][] dp = new int[n + 1][capacityKg + 1];

        for (int i = 1; i <= n; i++) {
            int weight = weights[i - 1];
            int value = values[i - 1];
            for (int w = 0; w <= capacityKg; w++) {
                if (weight <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - weight] + value);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        boolean[] keep = new boolean[n];
        int w = capacityKg;
        int selectedCount = 0;
        for (int i = n; i >= 1; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                keep[i - 1] = true;
                selectedCount++;
                w -= weights[i - 1];
            }
        }

        WasteRequest[] selected = new WasteRequest[selectedCount];
        int index = 0;
        for (int i = 0; i < n; i++) {
            if (keep[i]) {
                selected[index] = requests[i];
                index++;
            }
        }

        return new Result(dp[n][capacityKg], selected, dp);
    }
}
