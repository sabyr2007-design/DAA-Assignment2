package kz.daa.assignment2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {

    @Test
    void testAddAndGet() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void testAddAtIndex() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(30);
        list.add(1, 20);

        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void testAddAtBeginning() {
        MyLinkedList list = new MyLinkedList();

        list.add(20);
        list.add(30);
        list.add(0, 10);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void testRemove() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        int removed = list.remove(1);

        assertEquals(20, removed);
        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(30, list.get(1));
    }

    @Test
    void testRemoveFirst() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        int removed = list.remove(0);

        assertEquals(10, removed);
        assertEquals(2, list.size());
        assertEquals(20, list.get(0));
    }

    @Test
    void testContains() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertTrue(list.contains(20));
        assertFalse(list.contains(99));
    }

    @Test
    void testEmptyList() {
        MyLinkedList list = new MyLinkedList();

        assertEquals(0, list.size());

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(0)
        );
    }

    @Test
    void testInvalidIndex() {
        MyLinkedList list = new MyLinkedList();
        list.add(10);

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(-1)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(1)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.remove(1)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.add(2, 50)
        );
    }

    @Test
    void testDuplicates() {
        MyLinkedList list = new MyLinkedList();

        list.add(5);
        list.add(5);
        list.add(5);

        assertEquals(3, list.size());
        assertTrue(list.contains(5));
    }
}
