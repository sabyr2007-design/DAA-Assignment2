package kz.daa.assignment2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {

    @Test
    void testAddAndGet() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(3, array.size());
        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    void testAddAtIndex() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(30);
        array.add(1, 20);

        assertEquals(3, array.size());
        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    void testRemove() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        int removed = array.remove(1);

        assertEquals(20, removed);
        assertEquals(2, array.size());
        assertEquals(10, array.get(0));
        assertEquals(30, array.get(1));
    }

    @Test
    void testContains() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertTrue(array.contains(20));
        assertFalse(array.contains(99));
    }

    @Test
    void testEmptyArray() {
        DynamicArray array = new DynamicArray();

        assertEquals(0, array.size());
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(0)
        );
    }

    @Test
    void testInvalidIndex() {
        DynamicArray array = new DynamicArray();
        array.add(10);

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(-1)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(1)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.remove(1)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.add(2, 50)
        );
    }

    @Test
    void testDuplicates() {
        DynamicArray array = new DynamicArray();

        array.add(5);
        array.add(5);
        array.add(5);

        assertEquals(3, array.size());
        assertTrue(array.contains(5));
    }
}
