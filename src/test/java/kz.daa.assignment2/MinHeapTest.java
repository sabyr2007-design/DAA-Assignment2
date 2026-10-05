package kz.daa.assignment2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinHeapTest {

    @Test
    void testInsertAndPeekMin() {
        MinHeap heap = new MinHeap();

        heap.insert(5);
        heap.insert(2);
        heap.insert(8);
        heap.insert(1);

        assertEquals(4, heap.size());
        assertEquals(1, heap.peekMin());
    }

    @Test
    void testExtractMinReturnsSortedOrder() {
        MinHeap heap = new MinHeap();

        heap.insert(5);
        heap.insert(2);
        heap.insert(8);
        heap.insert(1);
        heap.insert(3);

        assertEquals(1, heap.extractMin());
        assertEquals(2, heap.extractMin());
        assertEquals(3, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(8, heap.extractMin());

        assertEquals(0, heap.size());
    }

    @Test
    void testDuplicates() {
        MinHeap heap = new MinHeap();

        heap.insert(5);
        heap.insert(5);
        heap.insert(2);
        heap.insert(2);

        assertEquals(2, heap.extractMin());
        assertEquals(2, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
    }

    @Test
    void testOneElement() {
        MinHeap heap = new MinHeap();

        heap.insert(42);

        assertEquals(42, heap.peekMin());
        assertEquals(42, heap.extractMin());
        assertEquals(0, heap.size());
    }

    @Test
    void testEmptyHeap() {
        MinHeap heap = new MinHeap();

        assertThrows(
                IllegalStateException.class,
                heap::peekMin
        );

        assertThrows(
                IllegalStateException.class,
                heap::extractMin
        );
    }

    @Test
    void testNegativeValues() {
        MinHeap heap = new MinHeap();

        heap.insert(-5);
        heap.insert(10);
        heap.insert(-20);
        heap.insert(0);

        assertEquals(-20, heap.extractMin());
        assertEquals(-5, heap.extractMin());
        assertEquals(0, heap.extractMin());
        assertEquals(10, heap.extractMin());
    }

    @Test
    void testRandomValuesAreSorted() {
        MinHeap heap = new MinHeap();

        int[] values = {
                17, 4, 9, 1, 25, 3, 8, 12, 2, 20
        };

        for (int value : values) {
            heap.insert(value);
        }

        int previous = Integer.MIN_VALUE;

        while (heap.size() > 0) {
            int current = heap.extractMin();

            assertTrue(current >= previous);

            previous = current;
        }
    }
}