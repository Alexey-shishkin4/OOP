package org.example;  // Add this to your test file

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class HeapSortTest {

    @Test
    public void testSortWithUnsortedArray() {
        int[] inputArray = {12, 11, 13, 5, 6, 7};
        int[] expectedArray = {5, 6, 7, 11, 12, 13};
        HeapSort heapSort = new HeapSort();
        heapSort.sort(inputArray);
        assertArrayEquals(expectedArray, inputArray, "The array should be sorted in ascending order.");
    }

    @Test
    public void testSortWithSortedArray() {
        int[] inputArray = {1, 2, 3, 4, 5};
        int[] expectedArray = {1, 2, 3, 4, 5};
        HeapSort heapSort = new HeapSort();
        heapSort.sort(inputArray);
        assertArrayEquals(expectedArray, inputArray, "The already sorted array should remain sorted.");
    }

    @Test
    public void testSortWithReverseSortedArray() {
        int[] inputArray = {5, 4, 3, 2, 1};
        int[] expectedArray = {1, 2, 3, 4, 5};
        HeapSort heapSort = new HeapSort();
        heapSort.sort(inputArray);
        assertArrayEquals(expectedArray, inputArray, "The reverse sorted array should be sorted in ascending order.");
    }

    @Test
    public void testSortWithSingleElementArray() {
        int[] inputArray = {1};
        int[] expectedArray = {1};
        HeapSort heapSort = new HeapSort();
        heapSort.sort(inputArray);
        assertArrayEquals(expectedArray, inputArray, "A single-element array should remain unchanged.");
    }

    @Test
    public void testSortWithEmptyArray() {
        int[] inputArray = {};
        int[] expectedArray = {};
        HeapSort heapSort = new HeapSort();
        heapSort.sort(inputArray);
        assertArrayEquals(expectedArray, inputArray, "An empty array should remain unchanged.");
    }

    @Test
    public void testSortWithDuplicates() {
        int[] inputArray = {4, 1, 3, 4, 2, 4};
        int[] expectedArray = {1, 2, 3, 4, 4, 4};
        HeapSort heapSort = new HeapSort();
        heapSort.sort(inputArray);
        assertArrayEquals(expectedArray, inputArray, "The array with duplicates should be sorted correctly.");
    }
}
