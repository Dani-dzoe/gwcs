package algorithms.optimization;

import java.util.ArrayList;
import java.util.List;

/**
 * KnapsackDP - 0/1 Knapsack using dynamic programming
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Time Complexity: O(n * W)
 * Space Complexity: O(n * W)
 */
public class KnapsackDP {

    /**
     * Item class
     */
    public static class Item {
        public int id;
        public String name;
        public int weight;
        public int value;

        public Item(int id, String name, int weight, int value) {
            this.id = id;
            this.name = name;
            this.weight = weight;
            this.value = value;
        }
    }

    /**
     * Solution class
     */
    public static class Solution {
        public int maxValue;
        public List<Item> selectedItems;
        public int[][] dp;

        public Solution(int maxValue, List<Item> selectedItems, int[][] dp) {
            this.maxValue = maxValue;
            this.selectedItems = selectedItems;
            this.dp = dp;
        }
    }

    /**
     * Solve 0/1 knapsack problem
     */
    public static Solution solve(List<Item> items, int capacity) {
        int n = items.size();
        int[][] dp = new int[n + 1][capacity + 1];

        // Build DP table
        for (int i = 1; i <= n; i++) {
            Item item = items.get(i - 1);

            for (int w = 0; w <= capacity; w++) {
                if (item.weight <= w) {
                    dp[i][w] = Math.max(
                        dp[i - 1][w],
                        dp[i - 1][w - item.weight] + item.value
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Backtrack to find selected items
        List<Item> selected = new ArrayList<>();
        int w = capacity;

        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                Item item = items.get(i - 1);
                selected.add(item);
                w -= item.weight;
            }
        }

        return new Solution(dp[n][capacity], selected, dp);
    }

    /**
     * Solve with only value (no backtracking)
     */
    public static int solveValueOnly(List<Item> items, int capacity) {
        int n = items.size();
        int[] dp = new int[capacity + 1];

        for (Item item : items) {
            for (int w = capacity; w >= item.weight; w--) {
                dp[w] = Math.max(dp[w], dp[w - item.weight] + item.value);
            }
        }

        return dp[capacity];
    }

    /**
     * Print DP table
     */
    public static void printDPTable(int[][] dp) {
        for (int[] row : dp) {
            for (int val : row) {
                System.out.printf("%4d", val);
            }
            System.out.println();
        }
    }
}
