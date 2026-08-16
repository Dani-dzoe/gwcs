package algorithms;

import model.WasteRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnapsackDPTest {

    @Test
    void shouldSelectSingleHighValueRequestOverTwoLowerValueOnes() {
        WasteRequest r1 = new WasteRequest(1, "A", "GENERAL", 50, 2, 10, 1000); // value 250, weight 10
        WasteRequest r2 = new WasteRequest(2, "B", "GENERAL", 90, 4, 20, 1000); // value 490, weight 20
        WasteRequest r3 = new WasteRequest(3, "C", "GENERAL", 30, 1, 15, 1000); // value 130, weight 15

        KnapsackDP knapsack = new KnapsackDP();
        KnapsackDP.Result result = knapsack.solve(new WasteRequest[]{r1, r2, r3}, 25);

        assertEquals(490, result.getTotalValue());
        assertEquals(1, result.getSelectedRequests().length);
        assertEquals(2, result.getSelectedRequests()[0].getRequestId());
    }

    @Test
    void shouldBeatGreedyPriorityFirstInConstructedScenario() {
        // See GreedyTruckAssignmentTest / SmokeTest for the matching greedy run.
        // Priority-first greedy takes request A (value 500) and has no room left.
        // Knapsack correctly takes B+C together (value 700).
        WasteRequest reqA = new WasteRequest(1, "LOC_A", "GENERAL", 0, 5, 25, 1000);
        WasteRequest reqB = new WasteRequest(2, "LOC_B", "GENERAL", 50, 3, 20, 1000);
        WasteRequest reqC = new WasteRequest(3, "LOC_C", "GENERAL", 50, 3, 10, 1000);

        KnapsackDP knapsack = new KnapsackDP();
        KnapsackDP.Result result = knapsack.solve(new WasteRequest[]{reqA, reqB, reqC}, 30);

        assertEquals(700, result.getTotalValue());
        assertEquals(2, result.getSelectedRequests().length);
    }

    @Test
    void shouldSelectAllRequestsWhenCapacityIsSufficient() {
        WasteRequest r1 = new WasteRequest(1, "A", "GENERAL", 20, 1, 5, 1000);
        WasteRequest r2 = new WasteRequest(2, "B", "GENERAL", 20, 1, 5, 1000);

        KnapsackDP knapsack = new KnapsackDP();
        KnapsackDP.Result result = knapsack.solve(new WasteRequest[]{r1, r2}, 100);

        assertEquals(2, result.getSelectedRequests().length);
        assertEquals(r1.getUrgencyScore() + r2.getUrgencyScore(), result.getTotalValue());
    }

    @Test
    void shouldSelectNothingWhenCapacityIsZero() {
        WasteRequest r1 = new WasteRequest(1, "A", "GENERAL", 20, 1, 5, 1000);

        KnapsackDP knapsack = new KnapsackDP();
        KnapsackDP.Result result = knapsack.solve(new WasteRequest[]{r1}, 0);

        assertEquals(0, result.getTotalValue());
        assertEquals(0, result.getSelectedRequests().length);
    }

    @Test
    void shouldHandleEmptyRequestsArray() {
        KnapsackDP knapsack = new KnapsackDP();
        KnapsackDP.Result result = knapsack.solve(new WasteRequest[]{}, 50);

        assertEquals(0, result.getTotalValue());
        assertEquals(0, result.getSelectedRequests().length);
    }

    @Test
    void shouldThrowExceptionWhenRequestsArrayIsNull() {
        KnapsackDP knapsack = new KnapsackDP();
        assertThrows(IllegalArgumentException.class, () -> knapsack.solve(null, 50));
    }

    @Test
    void shouldThrowExceptionWhenCapacityIsNegative() {
        KnapsackDP knapsack = new KnapsackDP();
        assertThrows(IllegalArgumentException.class,
                () -> knapsack.solve(new WasteRequest[]{}, -5));
    }
}
