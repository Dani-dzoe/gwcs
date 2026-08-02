package gh.edu.ug.wastesys;

import gh.edu.ug.wastesys.algo.GraphEngine;
import gh.edu.ug.wastesys.algo.GreedyDpEngine;
import gh.edu.ug.wastesys.algo.SearchSortEngine;
import gh.edu.ug.wastesys.config.TeamParameters;
import gh.edu.ug.wastesys.data.SeedDataFactory;
import gh.edu.ug.wastesys.ds.BTree;
import gh.edu.ug.wastesys.ds.BinarySearchTree;
import gh.edu.ug.wastesys.ds.CircularQueue;
import gh.edu.ug.wastesys.ds.DisjointSet;
import gh.edu.ug.wastesys.ds.DynamicArray;
import gh.edu.ug.wastesys.ds.HashTable;
import gh.edu.ug.wastesys.ds.MinHeapPriorityQueue;
import gh.edu.ug.wastesys.ds.RedBlackTree;
import gh.edu.ug.wastesys.ds.SinglyLinkedList;
import gh.edu.ug.wastesys.ds.Stack;
import gh.edu.ug.wastesys.model.Location;
import gh.edu.ug.wastesys.model.ServiceRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjectSmokeTest {
    @Test
    void dynamicArrayRejectsNonPositiveCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new DynamicArray<>(0));
    }

    @Test
    void dynamicArrayInsertsAndGrows() {
        DynamicArray<Integer> array = new DynamicArray<>(2);
        array.insert(1);
        array.insert(2);
        array.insert(3);
        assertEquals(3, array.size());
        assertEquals(2, array.get(1));
    }

    @Test
    void dynamicArrayRemovesAndShiftsElements() {
        DynamicArray<Integer> array = new DynamicArray<>(2);
        array.insert(10);
        array.insert(20);
        array.insert(30);
        assertEquals(20, array.remove(1));
        assertEquals(2, array.size());
        assertEquals(30, array.get(1));
    }

    @Test
    void dynamicArrayReplacesExistingValue() {
        DynamicArray<String> array = new DynamicArray<>(2);
        array.insert("A");
        array.insert("B");
        assertEquals("A", array.set(0, "C"));
        assertEquals("C", array.get(0));
    }

    @Test
    void dynamicArrayIteratorTraversesElements() {
        DynamicArray<Integer> array = new DynamicArray<>(2);
        array.insert(1);
        array.insert(2);
        List<Integer> seen = new ArrayList<>();
        for (Integer value : array) {
            seen.add(value);
        }
        assertEquals(List.of(1, 2), seen);
    }

    @Test
    void linkedListAddsFirstAndLast() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.addFirst("B");
        list.addLast("C");
        list.addFirst("A");
        assertEquals(3, list.size());
    }

    @Test
    void linkedListInsertsAfterTarget() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.addLast("A");
        list.addLast("C");
        list.insertAfter("A", "B");
        assertEquals(List.of("A", "B", "C"), toList(list));
    }

    @Test
    void linkedListRemovesExistingValue() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.addLast("A");
        list.addLast("B");
        assertEquals("B", list.remove("B"));
        assertEquals(1, list.size());
    }

    @Test
    void linkedListRemoveFirstWorks() {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        assertEquals(1, list.removeFirst());
    }

    @Test
    void linkedListThrowsOnMissingTarget() {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.addLast("A");
        assertThrows(IllegalArgumentException.class, () -> list.insertAfter("Z", "B"));
    }

    @Test
    void stackPushesAndPops() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    void stackPeekReturnsTopValue() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.peek());
    }

    @Test
    void stackThrowsWhenEmpty() {
        Stack<Integer> stack = new Stack<>();
        assertThrows(NoSuchElementException.class, stack::pop);
    }

    @Test
    void circularQueueEnqueuesAndDequeues() {
        CircularQueue<Integer> queue = new CircularQueue<>(2);
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
    }

    @Test
    void circularQueueWrapsAroundBuffer() {
        CircularQueue<Integer> queue = new CircularQueue<>(2);
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(1, queue.dequeue());
        queue.enqueue(3);
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
    }

    @Test
    void circularQueueThrowsWhenEmpty() {
        CircularQueue<Integer> queue = new CircularQueue<>(1);
        assertThrows(NoSuchElementException.class, queue::dequeue);
    }

    @Test
    void circularQueueThrowsWhenFull() {
        CircularQueue<Integer> queue = new CircularQueue<>(1);
        queue.enqueue(1);
        assertThrows(IllegalStateException.class, () -> queue.enqueue(2));
    }

    @Test
    void heapExtractsMinimumInOrder() {
        MinHeapPriorityQueue<Integer> heap = new MinHeapPriorityQueue<>();
        heap.insert(4);
        heap.insert(2);
        heap.insert(7);
        assertEquals(2, heap.extractMin());
        assertEquals(4, heap.extractMin());
        assertEquals(7, heap.extractMin());
    }

    @Test
    void heapThrowsWhenEmpty() {
        MinHeapPriorityQueue<Integer> heap = new MinHeapPriorityQueue<>();
        assertThrows(NoSuchElementException.class, heap::extractMin);
    }

    @Test
    void bstStoresSortedValues() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(3);
        bst.insert(1);
        bst.insert(4);
        assertEquals(List.of(1, 3, 4), bst.inorder());
    }

    @Test
    void bstContainsInsertedValues() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(8);
        bst.insert(3);
        bst.insert(10);
        assertTrue(bst.contains(3));
        assertFalse(bst.contains(11));
    }

    @Test
    void redBlackTreeStoresSortedValues() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(7);
        tree.insert(3);
        tree.insert(9);
        assertTrue(tree.contains(9));
        assertEquals(List.of(3, 7, 9), tree.inorder());
    }

    @Test
    void redBlackTreeContainsInsertedValues() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(5);
        tree.insert(1);
        tree.insert(8);
        assertTrue(tree.contains(1));
        assertFalse(tree.contains(6));
    }

    @Test
    void bTreeStoresSortedValues() {
        BTree<Integer> tree = new BTree<>();
        for (int value : List.of(10, 20, 5, 6, 12, 30, 7, 17)) {
            tree.insert(value);
        }
        assertTrue(tree.contains(12));
        assertEquals(List.of(5, 6, 7, 10, 12, 17, 20, 30), tree.inorder());
    }

    @Test
    void bTreeContainsInsertedValues() {
        BTree<Integer> tree = new BTree<>();
        for (int value : List.of(4, 2, 9, 6)) {
            tree.insert(value);
        }
        assertTrue(tree.contains(6));
        assertFalse(tree.contains(7));
    }

    @Test
    void hashTableHandlesCollisions() {
        HashTable<String, Integer> table = new HashTable<>(2);
        table.put("A", 1);
        table.put("B", 2);
        assertEquals(1, table.get("A"));
        assertEquals(2, table.get("B"));
    }

    @Test
    void hashTableUpdatesExistingKey() {
        HashTable<String, Integer> table = new HashTable<>(2);
        table.put("A", 1);
        table.put("A", 3);
        assertEquals(3, table.get("A"));
    }

    @Test
    void hashTableRemovesExistingKey() {
        HashTable<String, Integer> table = new HashTable<>(2);
        table.put("A", 1);
        table.put("B", 2);
        assertEquals(2, table.remove("B"));
        assertEquals(null, table.get("B"));
    }

    @Test
    void disjointSetUnions() {
        DisjointSet set = new DisjointSet(4);
        set.union(0, 1);
        assertEquals(set.find(0), set.find(1));
    }

    @Test
    void disjointSetKeepsDifferentSetsSeparate() {
        DisjointSet set = new DisjointSet(4);
        set.union(0, 1);
        set.union(2, 3);
        assertFalse(set.find(0) == set.find(2));
    }

    @Test
    void searchSortLinearSearchFindsValue() {
        SearchSortEngine engine = new SearchSortEngine();
        assertEquals(2, engine.linearSearch(List.of(5, 2, 9, 1), 9));
    }

    @Test
    void searchSortLinearSearchReturnsMinusOneWhenMissing() {
        SearchSortEngine engine = new SearchSortEngine();
        assertEquals(-1, engine.linearSearch(List.of(1, 2, 3), 4));
    }

    @Test
    void searchSortBinarySearchFindsValue() {
        SearchSortEngine engine = new SearchSortEngine();
        assertEquals(2, engine.binarySearch(List.of(1, 2, 5, 9), 5));
    }

    @Test
    void searchSortBinarySearchReturnsMinusOneWhenMissing() {
        SearchSortEngine engine = new SearchSortEngine();
        assertEquals(-1, engine.binarySearch(List.of(1, 2, 5, 9), 8));
    }

    @Test
    void searchSortBinarySearchRequiresSortedInput() {
        SearchSortEngine engine = new SearchSortEngine();
        assertThrows(IllegalArgumentException.class, () -> engine.binarySearch(List.of(4, 2, 1), 1));
    }

    @Test
    void searchSortSelectionSortOrdersValues() {
        SearchSortEngine engine = new SearchSortEngine();
        List<Integer> values = new ArrayList<>(List.of(5, 2, 9, 1));
        engine.selectionSort(values);
        assertEquals(List.of(1, 2, 5, 9), values);
    }

    @Test
    void searchSortInsertionSortOrdersValues() {
        SearchSortEngine engine = new SearchSortEngine();
        List<Integer> values = new ArrayList<>(List.of(5, 2, 9, 1));
        engine.insertionSort(values);
        assertEquals(List.of(1, 2, 5, 9), values);
    }

    @Test
    void searchSortMergeSortOrdersValues() {
        SearchSortEngine engine = new SearchSortEngine();
        assertEquals(List.of(1, 2, 5, 9), engine.mergeSort(List.of(5, 2, 9, 1)));
    }

    @Test
    void searchSortQuickSortOrdersValues() {
        SearchSortEngine engine = new SearchSortEngine();
        assertEquals(List.of(1, 2, 5, 9), engine.quickSort(List.of(5, 2, 9, 1)));
    }

    @Test
    void graphRoutesWork() {
        GraphEngine graph = new GraphEngine();
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, 3);
        graph.addEdge(1, 3, 10);
        assertEquals(5.0, graph.dijkstra(1, 3).distance());
        assertEquals(List.of(1, 2, 3), graph.bfs(1));
    }

    @Test
    void graphBfsVisitsConnectedNodes() {
        GraphEngine graph = new GraphEngine();
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);
        assertEquals(List.of(1, 2, 3), graph.bfs(1));
    }

    @Test
    void graphDfsVisitsConnectedNodes() {
        GraphEngine graph = new GraphEngine();
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 1);
        assertEquals(List.of(1, 2, 3), graph.dfs(1));
    }

    @Test
    void graphDijkstraFindsShortestPath() {
        GraphEngine graph = new GraphEngine();
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, 3);
        graph.addEdge(1, 3, 10);
        assertEquals(List.of(1, 2, 3), graph.dijkstra(1, 3).path());
    }

    @Test
    void graphPrimConstructsMinimumSpanningTree() {
        GraphEngine graph = new GraphEngine();
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 2);
        graph.addEdge(1, 3, 5);
        assertEquals(2, graph.primMst(1).edges().size());
    }

    @Test
    void graphKruskalConstructsMinimumSpanningTree() {
        GraphEngine graph = new GraphEngine();
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 2);
        graph.addEdge(1, 3, 5);
        assertEquals(2, graph.kruskalMst().size());
    }

    @Test
    void graphMatrixTraversalWorks() {
        GraphEngine graph = new GraphEngine();
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 3, 1);
        assertEquals(List.of(1, 2, 3), graph.bfsMatrix(1));
        assertEquals(List.of(1, 2, 3), graph.dfsMatrix(1));
    }

    @Test
    void greedyByUrgencyOrdersHighestUrgencyFirst() {
        GreedyDpEngine engine = new GreedyDpEngine();
        List<ServiceRequest> requests = List.of(request(1, 8, 3), request(2, 6, 2), request(3, 6, 2));
        assertEquals(1, engine.greedyByUrgency(requests).get(0).requestId());
    }

    @Test
    void greedyDpKnapsackSelectsBestVolumeFit() {
        GreedyDpEngine engine = new GreedyDpEngine();
        List<ServiceRequest> requests = List.of(request(1, 8, 3), request(2, 6, 2), request(3, 6, 2));
        List<ServiceRequest> selected = engine.knapsackByVolume(requests, 4);
        assertEquals(2, selected.size());
        assertEquals(12, totalUrgency(selected));
    }

    @Test
    void greedyFailureCounterexampleShowsWhyDpIsNeeded() {
        GreedyDpEngine engine = new GreedyDpEngine();
        List<ServiceRequest> requests = List.of(request(1, 8, 3), request(2, 6, 2), request(3, 6, 2));
        List<ServiceRequest> greedyOrdered = engine.greedyByUrgency(requests);
        List<ServiceRequest> dpSelected = engine.knapsackByVolume(requests, 4);
        assertEquals(1, greedyOrdered.get(0).requestId());
        assertTrue(totalUrgency(dpSelected) > greedyOrdered.get(0).urgency());
    }

    @Test
    void seedFactoryProducesRequiredCounts() {
        SeedDataFactory factory = new SeedDataFactory();
        assertEquals(50, factory.locations().size());
        assertEquals(100, factory.roads().size());
        assertEquals(300, factory.serviceRequests().size());
        assertEquals(30, factory.resources().size());
        assertEquals(30, factory.algorithmRuns().size());
        assertEquals(30, factory.auditEvents().size());
    }

    @Test
    void teamParametersDeriveValues() {
        TeamParameters parameters = new TeamParameters(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13));
        assertTrue(parameters.hashTableSize() > 0);
        assertFalse(parameters.summary().isBlank());
    }

    @Test
    void locationRecordWorks() {
        Location location = new Location(1, "Korle Bu", "Accra", "Hospital Zone", 5.5, -0.2);
        assertEquals(1, location.locationId());
        assertEquals("Korle Bu", location.name());
    }

    private static List<String> toList(SinglyLinkedList<String> list) {
        List<String> values = new ArrayList<>();
        for (String value : list) {
            values.add(value);
        }
        return values;
    }

    private static int totalUrgency(List<ServiceRequest> requests) {
        return requests.stream().mapToInt(ServiceRequest::urgency).sum();
    }

    private static ServiceRequest request(int id, int urgency, double volume) {
        return new ServiceRequest(id, 1, 2, "Bin", urgency, LocalDateTime.now(), LocalDateTime.now().plusHours(1), "OPEN", volume, 10.0);
    }
}
