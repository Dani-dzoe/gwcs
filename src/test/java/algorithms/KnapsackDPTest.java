package algorithms.optimization;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class KnapsackDPTest {

    @Test
    public void testSolve() {
        List<KnapsackDP.Item> items = new ArrayList<>();
        items.add(new KnapsackDP.Item(1, "Item1", 10, 60));
        items.add(new KnapsackDP.Item(2, "Item2", 20, 100));
        items.add(new KnapsackDP.Item(3, "Item3", 30, 120));

        KnapsackDP.Solution solution = KnapsackDP.solve(items, 50);

        assertTrue(solution.maxValue > 0);
        assertFalse(solution.selectedItems.isEmpty());
    }

    @Test
    public void testSolveValueOnly() {
        List<KnapsackDP.Item> items = new ArrayList<>();
        items.add(new KnapsackDP.Item(1, "Item1", 10, 60));
        items.add(new KnapsackDP.Item(2, "Item2", 20, 100));

        int value = KnapsackDP.solveValueOnly(items, 30);

        assertTrue(value > 0);
    }
}
