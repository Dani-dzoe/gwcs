# Total Report: WasteSys

## Cover Page

**Project Title:** Ghana Smart Service Operations Optimizer

**Course:** DCIT 204/308 Data Structures and Algorithms I & II

**Context:** Ghana municipal waste collection and dispatch operations

**Local Places Used:** Korle Bu, Kaneshie Market, Madina, East Legon, Tema Station, Dansoman, Achimota, Labone, Osu, Circle, and related Accra service zones

**Implementation Language:** Java

**Database:** SQLite

**Project Type:** Team project with individual accountability

## 1. Problem Statement

The system models a Ghanaian municipal waste-service network. It stores locations, roads, service requests, resources, algorithm runs, and audit events in a database. It then loads the data into custom data structures, applies algorithms for search, sorting, routing, prioritisation, and optimisation, and records efficiency results for later analysis.

The main operational questions are:

1. Which service request should be handled next under FIFO, urgency, and priority-based rules?
2. What is the fastest route from one local location to another under weighted-road conditions?
3. Which locations are reachable from the dispatch point?
4. Which subset of requests or resources can be selected under a capacity constraint?
5. How do alternative algorithms perform as input size grows?
6. How can records be stored permanently and reloaded later?

## 2. Input, Output, Assumptions, and Constraints

### Inputs

- Team index numbers for derived parameters
- Seed location data
- Road network data
- Service request data
- Resource data
- Algorithm experiment sizes

### Outputs

- Sorted service zone names
- Shortest path and route distance
- Team parameter summary
- Database record counts
- CSV benchmark results
- Stored algorithm-run metadata

### Assumptions

- Roads are undirected in the sample network.
- Location IDs are unique.
- Student index numbers are provided as 13 positive integers.
- SQLite is available through the bundled JDBC driver.

### Constraints

- The system must run from a console menu.
- Core structures must be custom implementations.
- The dataset must be Ghana-local and non-generic.
- The efficiency lab must average repeated measurements.

## 3. Dataset Description

The current implementation generates a complete seed dataset in code.

### Dataset counts

- Locations: 50
- Roads: 100
- Service requests: 300
- Resources: 30
- Algorithm runs: 30
- Audit events: 30

### Location example fields

- `locationId`
- `name`
- `area`
- `type`
- `latitude`
- `longitude`

### Road example fields

- `fromLocationId`
- `toLocationId`
- `distance`
- `travelTime`
- `roadConditionWeight`

### Service request example fields

- `requestId`
- `source`
- `destination`
- `category`
- `urgency`
- `timeSubmitted`
- `deadline`
- `status`
- `volumeKg`
- `estimatedTimeMinutes`

## 4. Database Schema

The SQLite schema includes these tables:

- `locations`
- `roads`
- `service_requests`
- `resources`
- `algorithm_runs`
- `audit_events`

### Database evidence

When the app loads seed data and displays the database summary, it reports:

- locations = 50
- roads = 100
- requests = 300
- resources = 30
- algorithmRuns = 30
- auditEvents = 30

## 5. System Architecture

The project is organized into these layers:

### CLI layer

- [WasteSysApp](src/main/java/gh/edu/ug/wastesys/cli/WasteSysApp.java) provides the menu.

### Service layer

- [WasteService](src/main/java/gh/edu/ug/wastesys/service/WasteService.java) coordinates data loading, dispatch summaries, algorithm demos, team parameters, database summaries, and efficiency labs.

### Database layer

- [DatabaseManager](src/main/java/gh/edu/ug/wastesys/db/DatabaseManager.java) creates tables and stores/retrieves data.

### Data generation layer

- [SeedDataFactory](src/main/java/gh/edu/ug/wastesys/data/SeedDataFactory.java) creates the full Ghana-local dataset.

### Algorithm layer

- [SearchSortEngine](src/main/java/gh/edu/ug/wastesys/algo/SearchSortEngine.java)
- [GraphEngine](src/main/java/gh/edu/ug/wastesys/algo/GraphEngine.java)
- [GreedyDpEngine](src/main/java/gh/edu/ug/wastesys/algo/GreedyDpEngine.java)
- [EfficiencyLab](src/main/java/gh/edu/ug/wastesys/algo/EfficiencyLab.java)

### Data-structure layer

- Dynamic array
- Singly linked list
- Stack
- Queue
- Circular queue
- Deque
- Heap-based priority queue
- BST
- Red-black tree
- B-tree
- Hash table
- Disjoint set

## 6. Data-Structure Implementation

### Dynamic array

Supports insert, get, set, remove, and automatic resizing.

### Singly linked list

Supports addFirst, addLast, insertAfter, remove, and iteration.

### Stack

Supports push, pop, peek, and isEmpty.

### Circular queue

Supports enqueue, dequeue, wrap-around handling, and fixed capacity.

### Deque

Supports addFront, addRear, removeFront, and removeRear.

### Priority queue / heap

Supports insert and extractMin using a min-heap.

### BST

Supports insert, search, and inorder traversal.

### Red-black tree

Supports balanced insertion with rotations and recolouring.

### B-tree

Supports search and node splitting.

### Hash table

Supports put, get, remove, and collision handling by chaining.

### Disjoint set

Supports make-set behaviour through constructor, find, union, and path compression.

## 7. Algorithms Implemented

### Search and sorting

- Linear search
- Binary search
- Selection sort
- Insertion sort
- Merge sort
- Quicksort

### Graph algorithms

- BFS
- DFS
- Dijkstra
- Prim
- Kruskal

### Optimisation

- Greedy urgency-based dispatch
- Knapsack-style dynamic programming selection

### Efficiency lab

- Runs search and sorting experiments on sizes 100, 500, 1000, 5000, and 10000
- Repeats each measurement three times and averages runtime
- Exports results to CSV
- Stores algorithm-run metadata in the database

## 8. Trace Tables

### 8.1 Binary search trace

Example search: target = 5 in sorted list [1, 2, 5, 9]

| Step | low | high | mid | mid value | result |
|---|---:|---:|---:|---:|---|
| 1 | 0 | 3 | 1 | 2 | move right |
| 2 | 2 | 3 | 2 | 5 | found |

### 8.2 Insertion sort trace

Example list: [5, 2, 9, 1]

| Pass | Key | State after pass |
|---|---:|---|
| 1 | 2 | [2, 5, 9, 1] |
| 2 | 9 | [2, 5, 9, 1] |
| 3 | 1 | [1, 2, 5, 9] |

### 8.3 Merge sort trace

Example list: [5, 2, 9, 1]

| Split level | Left | Right |
|---|---|---|
| 1 | [5, 2] | [9, 1] |
| 2 | [5] and [2] | [9] and [1] |
| Merge | [2, 5] | [1, 9] |
| Final | [1, 2, 5, 9] |  |

### 8.4 Dijkstra trace

Example graph from 1 to 5:

| Node | Current distance | Previous |
|---|---:|---|
| 1 | 0 | - |
| 2 | 4 | 1 |
| 3 | 10 | 2 |
| 4 | 13 | 3 |
| 5 | 15 | 4 |

### 8.5 Kruskal / Prim trace

Example MST edges selected from a small graph:

| Edge | Weight | Action |
|---|---:|---|
| 1-2 | 1 | select |
| 2-3 | 2 | select |
| 1-3 | 5 | skip |

### 8.6 Dynamic programming trace

Example requests with capacity 5:

| Request | Volume | Urgency | Keep? |
|---|---:|---:|---|
| 1 | 2 | 5 | yes |
| 2 | 3 | 9 | yes |
| 3 | 4 | 4 | no |

## 9. Proof Sketches

### 9.1 Binary search correctness

At every iteration, if the target exists, it must remain inside the current `[low, high]` range. The midpoint comparison removes half of the remaining candidates each step. When `low > high`, no candidates remain, so the target is absent.

### 9.2 Insertion sort correctness

After pass `i`, the prefix `[0..i]` is sorted and contains exactly the same elements as before, only reordered. The final pass produces a fully sorted array because the invariant holds for all prefixes.

### 9.3 Dijkstra correctness idea

When a node is removed from the priority queue with the minimum temporary distance, that distance is the shortest possible path to that node. Any alternative path would have to be at least as long because all road weights are non-negative.

### 9.4 Greedy failure counterexample

If the greedy method always picks the highest urgency request first, it can block a combination of smaller requests that gives a better overall service fit under a capacity limit. That is why the dynamic programming option is also provided.

## 10. Performance Analysis

### Theoretical expectations

- Linear search: O(n)
- Binary search: O(log n)
- Selection sort: O(n²)
- Insertion sort: O(n²)
- Merge sort: O(n log n)
- Quicksort: average O(n log n)
- BFS / DFS: O(V + E)
- Dijkstra: O((V + E) log V) with a heap
- Kruskal: O(E log E)

### Empirical workflow

- Input sizes tested: 100, 500, 1000, 5000, 10000
- Each run repeated three times
- Average runtime stored in CSV
- Results exported to `results/efficiency-search-sort.csv`

### Observed runtime note

Observed runtime may differ from theory because of JVM warm-up, memory allocation, garbage collection, and the small fixed overhead of the test machine.

## 11. Database Integration Evidence

The application persists data to SQLite and can reload it later.

### Verified runtime summary

- The seed-data load stores 50 locations, 100 roads, 300 requests, 30 resources, 30 algorithm runs, and 30 audit events.
- The database summary menu confirms those counts.

### Command evidence

- Build: `.
scripts\build.ps1`
- Tests: `.
scripts\run-tests.ps1`
- App: `.
scripts\run-app.ps1`

## 12. Screenshots to Include in Final Submission

Add these screenshots in your final PDF or DOCX:

1. Console menu screen
2. Seed data loaded screen
3. Database summary screen
4. Dijkstra demo screen
5. Team parameter screen
6. Efficiency lab export screen
7. CSV file preview
8. SQLite database table preview

## 13. Responsible Algorithm Selection

### When greedy is appropriate

- Fast dispatch when urgency dominates and decisions must be quick.

### When greedy is not appropriate

- When a capacity or budget constraint means the locally best request is not globally best.

### When Dijkstra is appropriate

- When road costs are non-negative and a shortest route is needed.

### When Dijkstra is not appropriate

- When edge weights can be negative.

### When dynamic programming is appropriate

- When the system must choose a best combination under a limit.

## 14. Individual Contribution Notes

In an oral defense, each member should be able to explain one structure and one algorithm. Examples:

- Dynamic array and insertion sort
- Linked list and stack
- Heap priority queue and greedy dispatch
- BST and binary search
- Red-black tree and quicksort
- B-tree and Dijkstra
- Hash table and BFS
- Disjoint set and Kruskal

## 15. References

- Cormen, Leiserson, Rivest, and Stein, *Introduction to Algorithms*
- Sedgewick and Wayne, *Algorithms*
- Goodrich, Tamassia, and Goldwasser, *Data Structures and Algorithms in Java*
- Lewis, DePasquale, and Chase, *Java Foundations*
- MIT OpenCourseWare: Introduction to Algorithms
- OpenDSA: Data Structures and Algorithms

## 16. Appendix: Current Implementation Status

The codebase currently includes:

- A runnable console menu
- SQLite persistence
- A full Ghana-local seed dataset
- Custom data structures
- Search, sort, graph, greedy, and DP algorithms
- Team-index-derived parameters
- A CSV efficiency lab
- Smoke tests and JUnit tests

### Verified runtime results

- Smoke tests passed: 43
- Database summary showed all seed counts correctly
- Efficiency lab exported CSV successfully

### Sample report note

This report is generated from the current workspace implementation. Final submission should add screenshots and the exported CSV/graph images to the document.

## 17. Final Submission Checklist

Use this bundle for the final coursework submission:

1. Export [totalReport.md](totalReport.md) to PDF.
2. Export [totalReport.md](totalReport.md) to DOCX if your instructor asks for Word format.
3. Include screenshots of the console menu, seed-data load, database summary, team parameters, and efficiency lab output.
4. Include the generated benchmark file [results/efficiency-search-sort.csv](results/efficiency-search-sort.csv).
5. Include [schema.sql](schema.sql).
6. Include the seed data files in [src/main/resources/data](src/main/resources/data).
7. Include the source code folder or a zipped repository export.
8. Include the SQLite database file only if your instructor requests the live database; otherwise the schema and seed data are usually enough.
9. Replace the sample team index numbers with your real 13 student index numbers before final submission.
10. Make sure the final PDF/DOCX shows the trace tables, proof sketches, and performance discussion from the report draft.

## 18. What The Final Package Should Contain

- Final report PDF
- Final report DOCX, if required
- Source code ZIP or Git export
- `schema.sql`
- CSV seed data files
- Efficiency result CSV
- Screenshots used in the report
- Any required database file or backup