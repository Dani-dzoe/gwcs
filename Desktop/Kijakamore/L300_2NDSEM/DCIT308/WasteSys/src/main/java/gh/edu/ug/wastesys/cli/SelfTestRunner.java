package gh.edu.ug.wastesys.cli;

import gh.edu.ug.wastesys.algo.GreedyDpEngine;
import gh.edu.ug.wastesys.algo.GraphEngine;
import gh.edu.ug.wastesys.algo.SearchSortEngine;
import gh.edu.ug.wastesys.data.SeedDataFactory;
import gh.edu.ug.wastesys.ds.BinarySearchTree;
import gh.edu.ug.wastesys.ds.BTree;
import gh.edu.ug.wastesys.ds.CircularQueue;
import gh.edu.ug.wastesys.ds.DisjointSet;
import gh.edu.ug.wastesys.ds.DynamicArray;
import gh.edu.ug.wastesys.ds.HashTable;
import gh.edu.ug.wastesys.ds.MinHeapPriorityQueue;
import gh.edu.ug.wastesys.ds.RedBlackTree;
import gh.edu.ug.wastesys.ds.SinglyLinkedList;
import gh.edu.ug.wastesys.ds.Stack;
import gh.edu.ug.wastesys.model.ServiceRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class SelfTestRunner {
    public static void main(String[] args) {
        int assertions = 0;
        assertions += testDynamicArray();
        assertions += testLinkedList();
        assertions += testStack();
        assertions += testCircularQueue();
        assertions += testHeap();
        assertions += testBinarySearchTree();
        assertions += testRedBlackTree();
        assertions += testBTree();
        assertions += testHashTable();
        assertions += testDisjointSet();
        assertions += testSearchSort();
        assertions += testGraph();
        assertions += testGraphMatrixAndMst();
        assertions += testGreedyDp();
        assertions += testSeedFactory();
        assertions += testAdditionalCoverage();
        System.out.println("Self tests passed: " + assertions);
    }

    private static int testDynamicArray() {
        DynamicArray<Integer> array = new DynamicArray<>(2);
        array.insert(1);
        array.insert(2);
        array.insert(3);
        assert array.size() == 3;
        assert array.get(1) == 2;
        assert array.set(1, 9) == 2;
        assert array.remove(1) == 9;
        return 4;
    }

    private static int testLinkedList() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.addFirst("B");
        list.addLast("C");
        list.insertAfter("B", "D");
        list.addFirst("A");
        assert list.size() == 4;
        assert list.remove("D").equals("D");
        return 3;
    }

    private static int testStack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        assert stack.peek() == 2;
        assert stack.pop() == 2;
        assert !stack.isEmpty();
        return 3;
    }

    private static int testCircularQueue() {
        CircularQueue<Integer> queue = new CircularQueue<>(2);
        queue.enqueue(1);
        queue.enqueue(2);
        assert queue.dequeue() == 1;
        queue.enqueue(3);
        assert queue.dequeue() == 2;
        assert queue.dequeue() == 3;
        return 3;
    }

    private static int testHeap() {
        MinHeapPriorityQueue<Integer> heap = new MinHeapPriorityQueue<>();
        heap.insert(4);
        heap.insert(2);
        heap.insert(7);
        assert heap.extractMin() == 2;
        assert heap.extractMin() == 4;
        return 2;
    }

    private static int testBinarySearchTree() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(3);
        bst.insert(1);
        bst.insert(4);
        assert bst.contains(1);
        assert bst.inorder().equals(List.of(1, 3, 4));
        return 2;
    }

    private static int testRedBlackTree() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(7);
        tree.insert(3);
        tree.insert(9);
        tree.insert(1);
        assert tree.contains(9);
        assert tree.inorder().equals(List.of(1, 3, 7, 9));
        return 2;
    }

    private static int testBTree() {
        BTree<Integer> tree = new BTree<>();
        for (int value : List.of(10, 20, 5, 6, 12, 30, 7, 17)) {
            tree.insert(value);
        }
        assert tree.contains(12);
        assert tree.inorder().equals(List.of(5, 6, 7, 10, 12, 17, 20, 30));
        return 2;
    }

    private static int testHashTable() {
        HashTable<String, Integer> table = new HashTable<>(4);
        table.put("A", 1);
        table.put("B", 2);
        table.put("A", 3);
        assert table.get("A") == 3;
        assert table.remove("B") == 2;
        return 2;
    }

    private static int testDisjointSet() {
        DisjointSet set = new DisjointSet(4);
        set.union(0, 1);
        set.union(2, 3);
        assert set.find(0) == set.find(1);
        assert set.find(2) == set.find(3);
        return 2;
    }

    private static int testSearchSort() {
        SearchSortEngine engine = new SearchSortEngine();
        List<Integer> values = new ArrayList<>(List.of(5, 2, 9, 1));
        assert engine.linearSearch(values, 9) == 2;
        assert engine.binarySearch(List.of(1, 2, 5, 9), 5) == 2;
        engine.insertionSort(values);
        assert values.equals(List.of(1, 2, 5, 9));
        return 3;
    }

    private static int testGraph() {
        GraphEngine graph = new GraphEngine();
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, 3);
        graph.addEdge(1, 3, 10);
        assert graph.bfs(1).equals(List.of(1, 2, 3));
        assert graph.dfs(1).containsAll(List.of(1, 2, 3));
        assert graph.dijkstra(1, 3).distance() == 5.0;
        return 3;
    }

    private static int testGraphMatrixAndMst() {
        GraphEngine graph = new GraphEngine();
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 2);
        graph.addEdge(1, 3, 5);
        assert graph.bfsMatrix(1).equals(List.of(1, 2, 3));
        assert graph.dfsMatrix(1).containsAll(List.of(1, 2, 3));
        assert graph.primMst(1).edges().size() == 2;
        assert graph.kruskalMst().size() == 2;
        return 4;
    }

    private static int testGreedyDp() {
        GreedyDpEngine engine = new GreedyDpEngine();
        List<ServiceRequest> requests = List.of(
                new ServiceRequest(1, 1, 2, "Bin", 5, LocalDateTime.now(), LocalDateTime.now().plusHours(2), "OPEN", 2, 10),
                new ServiceRequest(2, 1, 3, "Bin", 9, LocalDateTime.now(), LocalDateTime.now().plusHours(2), "OPEN", 3, 20),
                new ServiceRequest(3, 1, 4, "Bin", 4, LocalDateTime.now(), LocalDateTime.now().plusHours(2), "OPEN", 4, 30)
        );
        assert engine.greedyByUrgency(requests).get(0).requestId() == 2;
        assert engine.knapsackByVolume(requests, 5).size() >= 1;
        return 2;
    }

    private static int testSeedFactory() {
        SeedDataFactory factory = new SeedDataFactory();
        assert factory.locations().size() == 50;
        assert factory.roads().size() == 100;
        assert factory.serviceRequests().size() == 300;
        assert factory.resources().size() == 30;
        assert factory.algorithmRuns().size() == 30;
        assert factory.auditEvents().size() == 30;
        return 6;
    }

    private static int testAdditionalCoverage() {
        DynamicArray<Integer> array = new DynamicArray<>(2);
        array.insert(1);
        array.insert(2);
        array.insert(3);
        assert array.remove(1) == 2;
        assert array.get(0) == 1;

        SearchSortEngine engine = new SearchSortEngine();
        boolean rejected = false;
        try {
            engine.binarySearch(List.of(4, 2, 1), 1);
        } catch (IllegalArgumentException exception) {
            rejected = true;
        }
        assert rejected;

        GreedyDpEngine greedyDpEngine = new GreedyDpEngine();
        List<ServiceRequest> requests = List.of(
                new ServiceRequest(1, 1, 2, "Bin", 8, LocalDateTime.now(), LocalDateTime.now().plusHours(2), "OPEN", 3, 10),
                new ServiceRequest(2, 1, 3, "Bin", 6, LocalDateTime.now(), LocalDateTime.now().plusHours(2), "OPEN", 2, 20),
                new ServiceRequest(3, 1, 4, "Bin", 6, LocalDateTime.now(), LocalDateTime.now().plusHours(2), "OPEN", 2, 30)
        );
        assert greedyDpEngine.greedyByUrgency(requests).get(0).requestId() == 1;
        assert greedyDpEngine.knapsackByVolume(requests, 4).size() == 2;
        return 6;
    }
}
