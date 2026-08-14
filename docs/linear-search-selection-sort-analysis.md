# Linear Search & Selection Sort — Complexity Analysis & Trace Table

**Member:** Robert Aidoo (Part 2 — Searching, Sorting & Service Scheduling Engine)
**Project:** Ghana Smart Service Operations Optimizer

---

## 1. Linear Search

### 1.1 Description
Scans the array sequentially from index 0, comparing each element to the
target, until a match is found or the end of the array is reached. Unlike
Binary Search, it does not require the input to be sorted.

### 1.2 Complexity

| Case    | Comparisons | Time Complexity | Space Complexity |
|---------|-------------|------------------|-------------------|
| Best    | 1           | O(1)             | O(1)              |
| Average | n/2         | O(n)             | O(1)              |
| Worst   | n           | O(n)             | O(1)              |

### 1.3 Correctness Sketch (Loop Invariant)
**Invariant:** Before each iteration `i`, `target` is not equal to any
element in `array[0..i-1]`.

- **Initialization:** Before the loop starts, `i = 0`, so the range
  `array[0..-1]` is empty — the invariant holds trivially.
- **Maintenance:** If `array[i] == target`, the method returns `i`
  immediately (correct). Otherwise, the invariant extends to `i+1`
  because `array[i]` has now been confirmed not equal to `target`.
- **Termination:** The loop ends when `i == array.length`. By the
  invariant, `target` is not in `array[0..length-1]`, i.e. not in the
  array at all, so returning `-1` is correct.

### 1.4 Trace Table
Input: `array = [5, 3, 8, 1, 9]`, `target = 1`

| Step | i | array[i] | Comparison (array[i] == target?) | Action           |
|------|---|----------|-----------------------------------|------------------|
| 1    | 0 | 5        | 5 == 1 → false                    | continue         |
| 2    | 1 | 3        | 3 == 1 → false                    | continue         |
| 3    | 2 | 8        | 8 == 1 → false                    | continue         |
| 4    | 3 | 1        | 1 == 1 → true                     | return index 3   |

**Result:** `search(array, 1)` returns `3`. 4 comparisons made.

---

## 2. Selection Sort

### 2.1 Description
Divides the array into a sorted prefix and an unsorted suffix. On each
pass, it finds the minimum element in the unsorted suffix and swaps it
into the first position of that suffix, growing the sorted prefix by one.

### 2.2 Complexity

| Case    | Comparisons | Swaps  | Time Complexity | Space Complexity |
|---------|-------------|--------|------------------|-------------------|
| Best    | n(n-1)/2    | 0      | O(n^2)           | O(1)              |
| Average | n(n-1)/2    | ~n     | O(n^2)           | O(1)              |
| Worst   | n(n-1)/2    | n-1    | O(n^2)           | O(1)              |

Note: unlike Insertion Sort, Selection Sort always performs the same
number of comparisons regardless of input order — it always scans the
full unsorted suffix to find the minimum. Only the swap count varies,
which is why best and worst case time complexity are the same, O(n^2).

### 2.3 Correctness Sketch (Loop Invariant)
**Invariant:** Before each outer iteration `i`, `array[0..i-1]` contains
the `i` smallest elements of the original array, sorted in ascending
order.

- **Initialization:** At `i = 0`, `array[0..-1]` is empty — trivially
  sorted and trivially "the 0 smallest elements."
- **Maintenance:** The inner loop finds `minIndex`, the index of the
  minimum element in `array[i..n-1]`. Swapping it into position `i`
  extends the sorted prefix to `array[0..i]`, and since it is the
  minimum of everything remaining, the prefix stays sorted and correct.
- **Termination:** The outer loop ends when `i = n-1`. By the invariant,
  `array[0..n-2]` holds the `n-1` smallest elements sorted, which forces
  the last element to be the largest and correctly placed — the whole
  array is sorted.

### 2.4 Trace Table
Input: `array = [5, 3, 8, 1, 9]`

| Pass (i) | Unsorted suffix scanned | minIndex found | Swap performed | Array after pass       |
|----------|--------------------------|-----------------|------------------|--------------------------|
| 0        | [5, 3, 8, 1, 9]          | 3 (value 1)     | swap(0, 3)       | [1, 3, 8, 5, 9]          |
| 1        | [3, 8, 5, 9]             | 1 (value 3)     | none (already min)| [1, 3, 8, 5, 9]         |
| 2        | [8, 5, 9]                | 3 (value 5)     | swap(2, 3)       | [1, 3, 5, 8, 9]          |
| 3        | [8, 9]                   | 3 (value 8)     | none (already min)| [1, 3, 5, 8, 9]         |

**Result:** `[1, 3, 5, 8, 9]`. 4 passes, 10 total comparisons
(4 + 3 + 2 + 1), 2 swaps.

---

## 3. Testing Summary

| Class              | Test Class            | Tests Covered                                                        |
|--------------------|------------------------|------------------------------------------------------------------------|
| `LinearSearch`     | `LinearSearchTest`    | Normal, first/last-position match, not found, empty, single element, duplicates, null array, null target, unsorted input |
| `SelectionSort`    | `SelectionSortTest`   | Normal, already sorted, reverse sorted, empty, single element, duplicates, strings (generics), null array |

All tests pass under `mvn clean test`.
