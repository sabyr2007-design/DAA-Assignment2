package kz.daa.assignment2;

public class MyLinkedList {

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private Metrics metrics;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
        metrics = new Metrics();
    }

    public void add(int x) {
        Node newNode = new Node(x);

        if (head == null) {
            head = newNode;
            metrics.move();

            tail = newNode;
            metrics.move();
        } else {
            tail.next = newNode;
            metrics.move();

            tail = newNode;
            metrics.move();
        }

        size++;
    }

    public void add(int index, int x) {
        checkAddIndex(index);

        Node newNode = new Node(x);

        if (index == 0) {
            newNode.next = head;
            metrics.move();

            head = newNode;
            metrics.move();

            if (size == 0) {
                tail = newNode;
                metrics.move();
            }

            size++;
            return;
        }

        if (index == size) {
            tail.next = newNode;
            metrics.move();

            tail = newNode;
            metrics.move();

            size++;
            return;
        }

        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
            metrics.step();
        }

        newNode.next = current.next;
        metrics.move();

        current.next = newNode;
        metrics.move();

        size++;
    }

    public int remove(int index) {
        checkIndex(index);

        if (index == 0) {
            int removed = head.value;

            head = head.next;
            metrics.move();

            size--;

            if (size == 0) {
                tail = null;
                metrics.move();
            }

            return removed;
        }

        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
            metrics.step();
        }

        int removed = current.next.value;

        current.next = current.next.next;
        metrics.move();

        if (index == size - 1) {
            tail = current;
            metrics.move();
        }

        size--;

        return removed;
    }

    public int get(int index) {
        checkIndex(index);

        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
            metrics.step();
        }

        return current.value;
    }

    public boolean contains(int x) {
        Node current = head;

        while (current != null) {
            metrics.comparison();

            if (current.value == x) {
                return true;
            }

            current = current.next;
            metrics.step();
        }

        return false;
    }

    public int size() {
        return size;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", size: " + size
            );
        }
    }

    private void checkAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", size: " + size
            );
        }
    }
}