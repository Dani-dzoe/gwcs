import algorithms.DistanceProvider;
import algorithms.GreedyTruckAssignment;
import algorithms.KnapsackDP;
import algorithms.MapDistanceProvider;
import algorithms.TraceTablePrinter;
import model.Truck;
import model.WasteRequest;

/**
 * Generates real trace-table output for Part 4's report section, using the
 * greedy-vs-knapsack counterexample scenario as the worked example. Not part
 * of the assessed algorithm code -- this is a report/demo driver.
 */
public class TraceTableDemo {
    public static void main(String[] args) {
        String[] locations = {"TRUCK_LOC", "LOC_A", "LOC_B", "LOC_C"};
        double[][] dist = {
                {0, 1, 1, 1},
                {1, 0, 1, 1},
                {1, 1, 0, 1},
                {1, 1, 1, 0}
        };
        DistanceProvider provider = new MapDistanceProvider(locations, dist);

        WasteRequest reqA = new WasteRequest(1, "LOC_A", "GENERAL", 0, 5, 25, 1000);  // value 500, weight 25
        WasteRequest reqB = new WasteRequest(2, "LOC_B", "GENERAL", 50, 3, 20, 1000); // value 350, weight 20
        WasteRequest reqC = new WasteRequest(3, "LOC_C", "GENERAL", 50, 3, 10, 1000); // value 350, weight 10
        WasteRequest[] requests = {reqA, reqB, reqC};

        Truck truck = new Truck(1, "Kofi", 30, "TRUCK_LOC", 90, true);

        System.out.println("=== GREEDY TRUCK ASSIGNMENT TRACE ===");
        System.out.println("Requests (id, priority, weight, urgencyScore): "
                + "R1(p5,w25,v" + reqA.getUrgencyScore() + ") "
                + "R2(p3,w20,v" + reqB.getUrgencyScore() + ") "
                + "R3(p3,w10,v" + reqC.getUrgencyScore() + ")");
        System.out.println("Truck capacity: 30kg" + System.lineSeparator());

        GreedyTruckAssignment greedy = new GreedyTruckAssignment(provider);
        GreedyTruckAssignment.Assignment[] greedyResults = greedy.assign(requests, new Truck[]{truck});
        System.out.println(TraceTablePrinter.traceGreedy(requests, new Truck[]{truck}, greedyResults));

        int greedyTotal = 0;
        for (GreedyTruckAssignment.Assignment a : greedyResults) {
            if (a.isAssigned()) greedyTotal += a.getRequest().getUrgencyScore();
        }
        System.out.println("Greedy total value served: " + greedyTotal);
        System.out.println("(R1 consumes 25/30 kg, leaving only 5kg -- neither R2 (20kg) nor R3 (10kg) fit)");

        System.out.println();
        System.out.println("=== KNAPSACK DP TRACE (same requests, same capacity) ===");
        KnapsackDP knapsack = new KnapsackDP();
        KnapsackDP.Result result = knapsack.solve(requests, 30);
        System.out.println(TraceTablePrinter.traceKnapsack(requests, 30, result));

        System.out.println();
        System.out.println("=== COMPARISON ===");
        System.out.println("Greedy (priority-first) total value: " + greedyTotal);
        System.out.println("Knapsack (optimal) total value:      " + result.getTotalValue());
        System.out.println("Knapsack improvement: " + (result.getTotalValue() - greedyTotal)
                + " (" + String.format("%.1f", 100.0 * (result.getTotalValue() - greedyTotal) / greedyTotal) + "% more value served)");
    }
}
