package org.example;


/**
 * Class to implement the Heap Sort algorithm.
 */
public class HeapSort {
    /**
     * Sorts an array using the heap sort algorithm.
     *
     * @param arr The array to be sorted.
     */
    public void sort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    /**
     * Maintains the max-heap property for a subtree rooted at the given index.
     *
     * @param arr The array representing the heap.
     * @param n   The size of the heap.
     * @param i   The index of the subtree root.
     */
    void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    /**
     * Swaps two elements in an array.
     *
     * @param arr The array.
     * @param i   The index of the first element.
     * @param j   The index of the second element.
     */
    void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
