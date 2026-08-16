import algorithms.DistanceProvider;
import algorithms.GreedyTruckAssignment;
import algorithms.KnapsackDP;
import model.Truck;
import model.WasteRequest;

public class SmokeTest {
    public static void main(String[] args) {
        testGreedyBasicAssignment();
        testGreedyNoSuitableTruck();
        testKnapsackBasic();
        testGreedyVsKnapsackCounterexample();
        System.out.println("ALL SMOKE TESTS PASSED");
    }

    static void testGreedyBasicAssignment() {
        String[] locations = {"BUSH_CANTEEN", "PENTAGON", "JQB"};
        double[][] dist = {
                {0, 2, 5},
                {2, 0, 4},
                {5, 4, 0}
        };
        DistanceProvider provider = new algorithms.MapDistanceProvider(locations, dist);

        WasteRequest r1 = new WasteRequest(1, "BUSH_CANTEEN", "GENERAL", 80, 3, 50, 1000);
        Truck t1 = new Truck(1, "Kofi", 200, "PENTAGON", 90, true);
        Truck t2 = new Truck(2, "Ama", 200, "JQB", 90, true);

        GreedyTruckAssignment greedy = new GreedyTruckAssignment(provider);
        GreedyTruckAssignment.Assignment[] results = greedy.assign(
                new WasteRequest[]{r1}, new Truck[]{t1, t2});

        assertTrue(results.length == 1, "should produce one assignment");
        assertTrue(results[0].isAssigned(), "should be assigned");
        assertTrue(results[0].getTruck().getTruckId() == 1, "nearest truck (Pentagon, dist=2) should win, got truck " + results[0].getTruck().getTruckId());
        System.out.println("testGreedyBasicAssignment OK: " + results[0]);
    }

    static void testGreedyNoSuitableTruck() {
        String[] locations = {"A", "B"};
        double[][] dist = {{0, 1}, {1, 0}};
        DistanceProvider provider = new algorithms.MapDistanceProvider(locations, dist);

        WasteRequest heavy = new WasteRequest(1, "A", "GENERAL", 90, 5, 500, 1000);
        Truck small = new Truck(1, "Kofi", 100, "B", 90, true);

        GreedyTruckAssignment greedy = new GreedyTruckAssignment(provider);
        GreedyTruckAssignment.Assignment[] results = greedy.assign(
                new WasteRequest[]{heavy}, new Truck[]{small});

        assertTrue(!results[0].isAssigned(), "request heavier than any truck capacity should be unassigned");
        System.out.println("testGreedyNoSuitableTruck OK: " + results[0]);
    }

    static void testKnapsackBasic() {
        WasteRequest r1 = new WasteRequest(1, "A", "GENERAL", 50, 2, 10, 1000); // value 250, weight 10
        WasteRequest r2 = new WasteRequest(2, "B", "GENERAL", 90, 4, 20, 1000); // value 490, weight 20
        WasteRequest r3 = new WasteRequest(3, "C", "GENERAL", 30, 1, 15, 1000); // value 130, weight 15

        KnapsackDP knapsack = new KnapsackDP();
        KnapsackDP.Result result = knapsack.solve(new WasteRequest[]{r1, r2, r3}, 25);

        // capacity 25: best combo is r1+r3 (weight 25, value 380) vs r2 alone (weight 20, value 490)
        // optimal should be r2 alone since 490 > 380 and nothing else fits alongside r2 (25-20=5 < 10)
        assertTrue(result.getTotalValue() == 490, "expected total value 490, got " + result.getTotalValue());
        assertTrue(result.getSelectedRequests().length == 1, "expected 1 selected request");
        assertTrue(result.getSelectedRequests()[0].getRequestId() == 2, "expected request 2 selected");
        System.out.println("testKnapsackBasic OK: totalValue=" + result.getTotalValue());
    }

    static void testGreedyVsKnapsackCounterexample() {
        // One truck, capacity 30kg.
        // Request A: highest priority (5), weight 25 -> value 500. Greedy grabs
        //   it first because it processes strictly by priority, leaving only
        //   5kg of capacity remaining.
        // Requests B and C: priority 3 each, weight 20 and 10 (sum 30, fits
        //   exactly), combined value 350 + 350 = 700. Neither fits in the 5kg
        //   greedy leaves behind, so greedy locks them both out.
        // Optimal (knapsack) sees that skipping A and taking B+C together
        // yields 700 > 500 -- strictly better -- and picks that instead.
        // This is the classic greedy failure: locally-best-first (by priority)
        // is not the same as globally-best (by total value under capacity).
        String[] locations = {"TRUCK_LOC", "LOC_A", "LOC_B", "LOC_C"};
        double[][] dist = {
                {0, 1, 1, 1},
                {1, 0, 1, 1},
                {1, 1, 0, 1},
                {1, 1, 1, 0}
        };
        DistanceProvider provider = new algorithms.MapDistanceProvider(locations, dist);

        WasteRequest reqA = new WasteRequest(1, "LOC_A", "GENERAL", 0, 5, 25, 1000);  // value 500, weight 25
        WasteRequest reqB = new WasteRequest(2, "LOC_B", "GENERAL", 50, 3, 20, 1000); // value 350, weight 20
        WasteRequest reqC = new WasteRequest(3, "LOC_C", "GENERAL", 50, 3, 10, 1000); // value 350, weight 10

        Truck truck = new Truck(1, "Kofi", 30, "TRUCK_LOC", 90, true);

        GreedyTruckAssignment greedy = new GreedyTruckAssignment(provider);
        GreedyTruckAssignment.Assignment[] greedyResults = greedy.assign(
                new WasteRequest[]{reqA, reqB, reqC}, new Truck[]{truck});

        int greedyTotalValue = 0;
        for (GreedyTruckAssignment.Assignment a : greedyResults) {
            if (a.isAssigned()) {
                greedyTotalValue += a.getRequest().getUrgencyScore();
            }
        }

        KnapsackDP knapsack = new KnapsackDP();
        KnapsackDP.Result knapsackResult = knapsack.solve(
                new WasteRequest[]{reqA, reqB, reqC}, 30);

        System.out.println("Counterexample -> greedy total value: " + greedyTotalValue
                + " (picks A only), knapsack total value: " + knapsackResult.getTotalValue()
                + " (picks B+C)");
        assertTrue(knapsackResult.getTotalValue() > greedyTotalValue,
                "knapsack should beat greedy in this constructed scenario");
        assertTrue(greedyTotalValue == 500, "greedy should only fit request A, got " + greedyTotalValue);
        assertTrue(knapsackResult.getTotalValue() == 700, "knapsack should find B+C=700, got " + knapsackResult.getTotalValue());
    }

    static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
