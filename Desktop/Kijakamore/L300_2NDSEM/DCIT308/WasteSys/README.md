# WasteSys

Ghana Smart Service Operations Optimizer for municipal waste collection.

## Context

This implementation uses a Ghanaian local waste-routing scenario centered on Accra, Tema, Madina, East Legon, Kaneshie, Korle Bu, and nearby service zones.

## Features

- Custom data structures: dynamic array, linked list, stack, queue, circular queue, deque, heap-based priority queue, BST, red-black tree, B-tree, hash table, disjoint set, graph
- Algorithms: linear search, binary search, selection sort, insertion sort, merge sort, quicksort, BFS, DFS, Dijkstra, Prim, Kruskal, greedy dispatch, knapsack DP
- SQLite persistence and CSV seeding
- Console menu for demonstrations
- JUnit tests

## Build

```bash
mvn test
mvn package
```

If Maven is unavailable on the machine, use the local scripts instead:

```powershell
.\scripts\build.ps1
.\scripts\run-tests.ps1
```

## Run

```bash
mvn exec:java -Dexec.mainClass=gh.edu.ug.wastesys.WasteSysApp
```

If the exec plugin is not available in your IDE, run the main class directly from `src/main/java/gh/edu/ug/wastesys/WasteSysApp.java`.

For this workspace, use the bundled runtime script so the SQLite driver is on the classpath:

```powershell
.\scripts\run-app.ps1
```
