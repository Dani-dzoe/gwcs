package app;

import database.*;
import model.*;
import datastructures.*;
import algorithms.searching.*;
import algorithms.sorting.*;
import algorithms.graph.*;
import scheduling.*;
import util.*;

import java.util.List;
import java.util.Scanner;

/**
 * Menu - Console menu system
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class Menu {
    private Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Show main menu
     */
    public void showMainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n========================================");
            System.out.println("MAIN MENU");
            System.out.println("========================================");
            System.out.println("1. Data Structures Demo");
            System.out.println("2. Algorithms Demo");
            System.out.println("3. Database Operations");
            System.out.println("4. Waste Request Management");
            System.out.println("5. Graph Algorithms");
            System.out.println("6. Performance Experiments");
            System.out.println("7. Exit");
            System.out.println("========================================");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showDataStructuresMenu();
                    break;
                case "2":
                    showAlgorithmsMenu();
                    break;
                case "3":
                    showDatabaseMenu();
                    break;
                case "4":
                    showWasteRequestMenu();
                    break;
                case "5":
                    showGraphMenu();
                    break;
                case "6":
                    showExperimentsMenu();
                    break;
                case "7":
                    running = false;
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Data structures menu
     */
    private void showDataStructuresMenu() {
        System.out.println("\n--- Data Structures Demo ---");

        DynamicArray<Integer> array = new DynamicArray<>();
        array.add(10);
        array.add(20);
        array.add(30);
        System.out.println("DynamicArray: " + array);

        LinkedListDS<String> list = new LinkedListDS<>();
        list.addLast("Accra");
        list.addLast("Tema");
        list.addLast("Kasoa");
        System.out.println("LinkedList: " + list);

        StackDS<Integer> stack = new StackDS<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("Stack: " + stack);
        System.out.println("Pop: " + stack.pop());

        BST<Integer> bst = new BST<>();
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        System.out.print("BST Inorder: ");
        bst.inorder();

        HashTable<String, Integer> ht = new HashTable<>();
        ht.put("A", 1);
        ht.put("B", 2);
        System.out.println("HashTable: " + ht);
    }

    /**
     * Algorithms menu
     */
    private void showAlgorithmsMenu() {
        System.out.println("\n--- Algorithms Demo ---");

        Integer[] arr = {64, 34, 25, 12, 22};
        System.out.print("Before: ");
        for (int i : arr) System.out.print(i + " ");
        System.out.println();

        MergeSort.sort(arr);
        System.out.print("After MergeSort: ");
        for (int i : arr) System.out.print(i + " ");
        System.out.println();

        int[] sorted = {1, 2, 3, 5, 7, 8, 9};
        int idx = BinarySearch.search(sorted, 7);
        System.out.println("BinarySearch 7 at index: " + idx);
    }

    /**
     * Database menu
     */
    private void showDatabaseMenu() {
        System.out.println("\n--- Database Operations ---");

        DatabaseLoader loader = new DatabaseLoader();
        loader.printStatistics();

        LocationDAO locDao = new LocationDAO();
        List<Location> locs = locDao.findActive();
        System.out.println("\nSample Locations:");
        int count = 0;
        for (Location loc : locs) {
            if (count++ < 5) {
                System.out.println("  - " + loc.getName() + " (" + loc.getArea() + ")");
            }
        }

        TruckDAO truckDao = new TruckDAO();
        List<Truck> trucks = truckDao.findAvailable();
        System.out.println("\nAvailable Trucks: " + trucks.size());
        for (Truck t : trucks) {
            if (count++ < 10) {
                System.out.println("  - " + t.getTruckName() + " (" + t.getTruckType() + ")");
            }
        }
    }

    /**
     * Waste request menu
     */
    private void showWasteRequestMenu() {
        System.out.println("\n--- Waste Request Management ---");

        WasteRequestDAO dao = new WasteRequestDAO();
        List<WasteRequest> requests = dao.findPending();

        System.out.println("Pending Requests: " + requests.size());

        for (WasteRequest req : requests) {
            System.out.println("  Request #" + req.getRequestId() + 
                             " - Urgency: " + req.getUrgencyLevel() +
                             " - Category: " + req.getCategory());
        }

        // Demo scheduling
        System.out.println("\n--- Scheduling Demo ---");
        PriorityScheduler scheduler = new PriorityScheduler();

        for (WasteRequest req : requests.subList(0, Math.min(3, requests.size()))) {
            scheduler.addRequest(req);
        }

        System.out.println("Scheduled by priority:");
        while (!scheduler.isEmpty()) {
            WasteRequest req = scheduler.getNext();
            System.out.println("  - " + req);
        }
    }

    /**
     * Graph menu
     */
    private void showGraphMenu() {
        System.out.println("\n--- Graph Algorithms ---");

        // Create sample graph
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 8);
        graph.addEdge(2, 4, 10);
        graph.addEdge(3, 4, 2);
        graph.addEdge(3, 5, 6);
        graph.addEdge(4, 5, 3);

        System.out.println("Graph Adjacency List:");
        System.out.println(graph);

        System.out.println("BFS from 0: " + BFS.traverse(graph, 0));
        System.out.println("DFS from 0: " + DFS.traverse(graph, 0));

        System.out.println("\nShortest paths from 0 (Dijkstra):");
        var distances = Dijkstra.shortestPaths(graph, 0);
        for (var entry : distances.entrySet()) {
            System.out.println("  Vertex " + entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nMST (Prim): weight = " + Prim.mstWeight(graph, 0));
        System.out.println("MST (Kruskal): weight = " + Kruskal.mstWeight(graph));
    }

    /**
     * Experiments menu
     */
    private void showExperimentsMenu() {
        System.out.println("\n--- Performance Experiments ---");

        Timer timer = new Timer();

        // Binary search experiment
        System.out.println("\nBinary Search Performance:");
        for (int size = 1000; size <= 10000; size += 2000) {
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) arr[i] = i;

            timer.start();
            for (int i = 0; i < 100; i++) {
                BinarySearch.search(arr, size / 2);
            }
            timer.stop();

            System.out.println("  Size " + size + ": " + timer.elapsedMillis() + " ms (100 runs)");
        }

        // Sorting experiment
        System.out.println("\nSorting Performance (size=1000):");
        Integer[] arr1 = new Integer[1000];
        Integer[] arr2 = new Integer[1000];
        for (int i = 0; i < 1000; i++) {
            arr1[i] = (int)(Math.random() * 1000);
            arr2[i] = arr1[i];
        }

        timer.start();
        MergeSort.sort(arr1);
        timer.stop();
        System.out.println("  MergeSort: " + timer.elapsedMillis() + " ms");

        timer.start();
        QuickSort.sort(arr2);
        timer.stop();
        System.out.println("  QuickSort: " + timer.elapsedMillis() + " ms");
    }
}
