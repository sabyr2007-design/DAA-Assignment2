package kz.daa.assignment2;

public class DynamicArray {

    private int[] data;
    private int size;
    private Metrics metrics;

    public DynamicArray() {
        data = new int[1];
        size = 0;
        metrics = new Metrics();
    }

    public void add(int x) {
        if (size == data.length) {
            grow();
        }

        data[size] = x;
        size++;
    }

    public void add(int index, int x) {
        checkAddIndex(index);

        if (size == data.length) {
            grow();
        }

        for (int i = size; i > index; i--) {
            metrics.step();
            data[i] = data[i - 1];
            metrics.move();
        }

        data[index] = x;
        size++;
    }

    public int remove(int index) {
        checkIndex(index);

        metrics.step();
        int removed = data[index];

        for (int i = index; i < size - 1; i++) {
            metrics.step();
            data[i] = data[i + 1];
            metrics.move();
        }

        size--;

        return removed;
    }

    public int get(int index) {
        checkIndex(index);

        metrics.step();
        return data[index];
    }

    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            metrics.step();

            metrics.comparison();
            if (data[i] == x) {
                return true;
            }
        }

        return false;
    }

    public int size() {
        return size;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    private void grow() {
        int[] newData = new int[data.length * 2];

        for (int i = 0; i < size; i++) {
            metrics.step();
            newData[i] = data[i];
            metrics.move();
        }

        data = newData;
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