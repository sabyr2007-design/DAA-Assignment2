package kz.daa.assignment2;

public class MinHeap {

    private int[] heap;
    private int size;
    private Metrics metrics;

    public MinHeap() {
        heap = new int[1];
        size = 0;
        metrics = new Metrics();
    }

    public void insert(int x) {
        if (size == heap.length) {
            grow();
        }

        heap[size] = x;

        int index = size;
        size++;

        bubbleUp(index);
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        return readHeap(0);
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        int result = readHeap(0);

        size--;

        if (size > 0) {
            heap[0] = readHeap(size);
            metrics.move();

            bubbleDown(0);
        }

        return result;
    }

    public int size() {
        return size;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            int parentValue = readHeap(parent);
            int currentValue = readHeap(index);

            metrics.comparison();

            if (parentValue <= currentValue) {
                break;
            }

            swap(parent, index);

            index = parent;
        }
    }

    private void bubbleDown(int index) {
        while (true) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;

            int smallest = index;

            if (left < size) {
                int leftValue = readHeap(left);
                int smallestValue = readHeap(smallest);

                metrics.comparison();

                if (leftValue < smallestValue) {
                    smallest = left;
                }
            }

            if (right < size) {
                int rightValue = readHeap(right);
                int smallestValue = readHeap(smallest);

                metrics.comparison();

                if (rightValue < smallestValue) {
                    smallest = right;
                }
            }

            if (smallest == index) {
                break;
            }

            swap(index, smallest);

            index = smallest;
        }
    }

    private void grow() {
        int[] newHeap = new int[heap.length * 2];

        for (int i = 0; i < size; i++) {
            newHeap[i] = readHeap(i);
            metrics.move();
        }

        heap = newHeap;
    }

    private void swap(int i, int j) {
        int temp = readHeap(i);
        int valueJ = readHeap(j);

        heap[i] = valueJ;
        metrics.move();

        heap[j] = temp;
        metrics.move();
    }

    private int readHeap(int index) {
        metrics.step();
        return heap[index];
    }
}