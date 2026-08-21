package app;

import database.*;
import model.*;
import datastructures.*;
import algorithms.searching.*;
import algorithms.sorting.*;
import algorithms.graph.*;
import algorithms.optimization.*;
import scheduling.*;
import util.*;

import java.util.List;
import java.util.Scanner;

/**
 * Main - Application entry point
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * Ghana Smart Waste Collection System
 */
public class Main {
    private static Scanner scanner;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Ghana Smart Waste Collection System");
        System.out.println("University of Ghana - DCIT 204308");
        System.out.println("========================================\n");

        scanner = new Scanner(System.in);

        // Initialize database
        initializeDatabase();

        // Show menu
        Menu menu = new Menu();
        menu.showMainMenu();

        scanner.close();
    }

    /**
     * Initialize database
     */
    private static void initializeDatabase() {
        System.out.println("Initializing database...");

        DatabaseInitializer initializer = new DatabaseInitializer("database/schema.sql");

        if (!initializer.isInitialized()) {
            System.out.println("Database not initialized. Initializing...");
            initializer.initialize();
        } else {
            System.out.println("Database already initialized.");
        }

        DatabaseLoader loader = new DatabaseLoader();
        loader.printStatistics();

        System.out.println("Database ready.\n");
    }

    /**
     * Demo data structures
     */
    public static void demoDataStructures() {
        System.out.println("\n--- Data Structures Demo ---\n");

        // DynamicArray
        DynamicArray<Integer> array = new DynamicArray<>();
        array.add(10);
        array.add(20);
        array.add(30);
        System.out.println("DynamicArray: " + array);

        // LinkedListDS
        LinkedListDS<String> list = new LinkedListDS<>();
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");
        System.out.println("LinkedList: " + list);

        // StackDS
        StackDS<Integer> stack = new StackDS<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("Stack: " + stack);
        System.out.println("Stack pop: " + stack.pop());

        // QueueDS
        QueueDS<String> queue = new QueueDS<>();
        queue.enqueue("First");
        queue.enqueue("Second");
        queue.enqueue("Third");
        System.out.println("Queue: " + queue);
        System.out.println("Queue dequeue: " + queue.dequeue());

        // BST
        BST<Integer> bst = new BST<>();
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        System.out.print("BST inorder: ");
        bst.inorder();

        // HashTable
        HashTable<String, Integer> hashTable = new HashTable<>();
        hashTable.put("A", 1);
        hashTable.put("B", 2);
        hashTable.put("C", 3);
        System.out.println("HashTable: " + hashTable);
        System.out.println("HashTable get(B): " + hashTable.get("B"));

        System.out.println("\n--- Demo Complete ---\n");
    }

    /**
     * Demo algorithms
     */
    public static void demoAlgorithms() {
        System.out.println("\n--- Algorithms Demo ---\n");

        // LinearSearch
        Integer[] numbers = {5, 3, 8, 1, 9, 2, 7};
        int index = LinearSearch.search(numbers, 9);
        System.out.println("LinearSearch: 9 found at index " + index);

        // BinarySearch
        int[] sorted = {1, 2, 3, 5, 7, 8, 9};
        index = BinarySearch.search(sorted, 7);
        System.out.println("BinarySearch: 7 found at index " + index);

        // Sorting
        Integer[] toSort = {64, 34, 25, 12, 22, 11, 90};
        System.out.print("Before sort: ");
        printArray(toSort);
        MergeSort.sort(toSort);
        System.out.print("After MergeSort: ");
        printArray(toSort);

        // Graph
        Graph graph = new Graph(5);
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 5);
        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 3, 9);
        graph.addEdge(2, 4, 2);
        graph.addEdge(3, 4, 4);

        System.out.println("\nGraph:");
        System.out.println(graph);

        List<Integer> bfs = BFS.traverse(graph, 0);
        System.out.println("BFS from 0: " + bfs);

        System.out.println("\n--- Demo Complete ---\n");
    }

    /**
     * Print array
     */
    private static void printArray(Integer[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
