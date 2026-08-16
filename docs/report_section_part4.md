# Part 4 Report Section — Greedy Truck Assignment & 0/1 Knapsack DP
**Author:** Chris Asumadu Domfeh (Assistant Lead, Part 4)

## 1. Problem Context

The waste collection system needs to decide, for a given set of pending waste
collection requests and a fleet of trucks, which requests get served and by
which truck. Two optimization strategies were implemented and compared:

- **Greedy Truck Assignment** — a fast, practical heuristic used for normal
  day-to-day dispatch: assign the nearest available truck to the most urgent
  request, repeat.
- **0/1 Knapsack DP** — used when a single truck's capacity is the binding
  constraint and we want the value-maximizing set of requests to load onto
  it, rather than committing greedily.

These two algorithms represent the classic greedy-vs-dynamic-programming
trade-off: greedy is faster and simpler but not always optimal; DP is slower
but guarantees the best possible outcome for the problem as defined.

## 2. Greedy Truck Assignment — Implementation

**File:** `algorithms/GreedyTruckAssignment.java`

**Approach:**
1. Sort all pending requests by priority, highest first (insertion sort,
   local to this class — can be swapped for the team's shared MergeSort once
   Part 2 is merged).
2. For each request in that order, scan all trucks and pick the nearest
   available truck that still has enough remaining capacity for the
   request's load.
3. Deduct the assigned load from that truck's remaining capacity so
   subsequent requests see an accurate picture.
4. If no truck qualifies, the request is returned as unassigned rather than
   silently dropped, so the caller (e.g. the console menu / scheduler) can
   decide what to do with it (queue it for the next run, escalate it, etc.).

**Design note:** the algorithm depends on a `DistanceProvider` interface
rather than directly on the Graph/Dijkstra module. This was necessary
because Parts 3 (Graph) wasn't built yet when this was written; it also
means Greedy is independently testable without a real road network, and the
production `DistanceProvider` (backed by Dijkstra) can be swapped in later
with no change to `GreedyTruckAssignment` itself.

**Complexity:**
- Sorting `n` requests by priority: `O(n log n)` (insertion sort as
  implemented here is `O(n²)` worst case, but is intended to be replaced by
  the team's `O(n log n)` MergeSort/QuickSort — this is a placeholder)
- For each of `n` requests, scanning `m` trucks: `O(n · m)`
- **Overall: `O(n log n + n · m)`**
- Space: `O(n + m)` for the sorted copy, capacity-tracking array, and results

**Limitation (by design, not a bug):** Greedy commits to each choice
irreversibly and never reconsiders earlier assignments. It optimizes locally
(nearest truck, most urgent first) but does not guarantee the assignment
that serves the greatest total value across all requests. See Section 4.

## 3. 0/1 Knapsack DP — Implementation

**File:** `algorithms/KnapsackDP.java`
(implemented jointly with Ebenezer N. K. Asamoah-Addo)

**Approach:** Standard bottom-up 0/1 knapsack. For a single truck with
integer capacity `W` kg and `n` candidate requests, each with a weight
(estimated load) and a value (urgency score), build a table `dp[i][w]` =
best achievable value using the first `i` requests within capacity `w`:

```
dp[i][w] = max( dp[i-1][w], dp[i-1][w - weight[i]] + value[i] )   if weight[i] <= w
dp[i][w] = dp[i-1][w]                                              otherwise
```

`dp[n][W]` gives the optimal total value. The selected subset is recovered
by backtracking through the table from `dp[n][W]`, checking at each row
whether including that request changed the value from the row above.

**Complexity:**
- Time: `O(n · W)` — one DP table cell per request per unit of capacity
- Space: `O(n · W)` for the full table (kept intentionally, rather than
  compressed to a 1D rolling array, so it can be printed for trace-table
  and correctness evidence — see `TraceTablePrinter.java`)

**Why DP guarantees optimality here (informal correctness argument):**
Each cell `dp[i][w]` is defined as the best value achievable using only the
first `i` items and capacity `w`. By induction: `dp[0][w] = 0` for all `w`
(base case, no items to choose from). Assuming `dp[i-1][*]` is correct for
all capacities, `dp[i][w]` is correct because it explicitly considers both
possibilities for item `i` — include it (only valid if it fits, in which
case the remaining budget `w - weight[i]` must itself hold the optimal
solution to the reduced problem) or exclude it — and takes the better of the
two. Since both sub-cases rely only on already-verified smaller subproblems,
`dp[n][W]` is correct by induction.

## 4. Counterexample: Greedy vs. Optimal

To demonstrate that greedy is not always optimal, we constructed a scenario
with one truck (capacity 30kg) and three requests:

| Request | Priority | Weight | Value |
|---|---|---|---|
| R1 | 5 (highest) | 25kg | 500 |
| R2 | 3 | 20kg | 350 |
| R3 | 3 | 10kg | 350 |

Greedy processes R1 first (highest priority), consuming 25 of the 30kg and
leaving only 5kg spare — not enough for either R2 or R3. **Greedy total
value: 500.**

Knapsack considers all combinations and finds that R2+R3 together weigh
exactly 30kg and are worth 700 combined — more than R1 alone. **Knapsack
total value: 700**, a 40% improvement.

This is a textbook demonstration of why greedy algorithms require a proof
of the exchange/matroid property to guarantee optimality, and why none
exists here: priority order is not the same as value-per-unit-capacity
order, so greedy's locally best choice at step 1 is not part of any globally
optimal solution. Full trace tables for both runs are in
`trace_tables_part4.md`.

## 5. Testing

- `GreedyTruckAssignmentTest.java` — 6 tests covering normal assignment,
  priority ordering, unavailable trucks, insufficient capacity, and null
  input handling.
- `KnapsackDPTest.java` — 7 tests covering normal selection, the
  counterexample scenario itself (encoded as a regression test), full
  capacity, zero capacity, empty input, and invalid input handling.

## 6. Division of Work with Ebenezer

- Chris: algorithm implementation (`GreedyTruckAssignment`, `KnapsackDP`),
  trace table generation, this write-up, unit tests for own code.
- Ebenezer: counterexample analysis and proof write-up (built on the
  scenario and trace tables above), DP trace table walkthrough, leading the
  40+ unit test suite across the team, test result documentation.
