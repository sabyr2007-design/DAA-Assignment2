package kz.daa.assignment2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {
            100, 1_000, 10_000, 100_000
    };

    private static final int RANDOM_ACCESS_QUERIES = 10_000;
    private static final int SEARCH_QUERIES = 1_000;
    private static final int UPDATE_OPERATIONS = 1_000;

    private static final int WARMUP_RUNS = 1;
    private static final int MEASURED_RUNS = 5;

    private static final int RANDOM_SEED = 42;

    private static final Path RESULTS_PATH =
            Path.of("results", "results.csv");

    private static final String CSV_HEADER =
            "workload,variant,structure,n,time_ms,steps,moves,comparisons";

    public static void main(String[] args) throws IOException {
        Files.createDirectories(RESULTS_PATH.getParent());

        try (var writer = Files.newBufferedWriter(RESULTS_PATH)) {
            writer.write(CSV_HEADER);
            writer.newLine();

            for (int n : SIZES) {
                int[] data = generateData(n);
                int[] randomIndices = generateRandomIndices(n);
                int[] searchQueries = generateSearchQueries(data);
                int[] insertedValues = generateInsertedValues();

                // W1: Random access
                runAndWrite(
                        writer,
                        "W1",
                        "-",
                        "DynamicArray",
                        n,
                        () -> runArrayRandomAccess(data, randomIndices)
                );

                runAndWrite(
                        writer,
                        "W1",
                        "-",
                        "MyLinkedList",
                        n,
                        () -> runListRandomAccess(data, randomIndices)
                );

                // W2: Search
                runAndWrite(
                        writer,
                        "W2",
                        "-",
                        "DynamicArray",
                        n,
                        () -> runArraySearch(data, searchQueries)
                );

                runAndWrite(
                        writer,
                        "W2",
                        "-",
                        "MyLinkedList",
                        n,
                        () -> runListSearch(data, searchQueries)
                );

                // W3: Insert and remove at the head
                runAndWrite(
                        writer,
                        "W3",
                        "head",
                        "DynamicArray",
                        n,
                        () -> runArrayUpdates(data, insertedValues, 0)
                );

                runAndWrite(
                        writer,
                        "W3",
                        "head",
                        "MyLinkedList",
                        n,
                        () -> runListUpdates(data, insertedValues, 0)
                );

                // W3: Insert and remove at the middle
                int middleIndex = n / 2;

                runAndWrite(
                        writer,
                        "W3",
                        "middle",
                        "DynamicArray",
                        n,
                        () -> runArrayUpdates(
                                data, insertedValues, middleIndex
                        )
                );

                runAndWrite(
                        writer,
                        "W3",
                        "middle",
                        "MyLinkedList",
                        n,
                        () -> runListUpdates(
                                data, insertedValues, middleIndex
                        )
                );

                // W4: MinHeap
                runAndWrite(
                        writer,
                        "W4",
                        "-",
                        "MinHeap",
                        n,
                        () -> runHeap(data)
                );

                System.out.println("Finished benchmarks for n = " + n);
            }
        }

        System.out.println();
        System.out.println("Benchmark completed.");
        System.out.println("Results saved to: " + RESULTS_PATH);
    }

    // ------------------------------------------------------------
    // W1: Random access
    // ------------------------------------------------------------

    private static Result runArrayRandomAccess(
            int[] data,
            int[] indices
    ) {
        DynamicArray array = new DynamicArray();

        for (int value : data) {
            array.add(value);
        }

        Metrics metrics = array.getMetrics();
        metrics.reset();
        metrics.startTimer();

        long checksum = 0;

        for (int index : indices) {
            checksum += array.get(index);
        }

        metrics.stopTimer();

        // Prevents the result from being completely unused.
        if (checksum == Long.MIN_VALUE) {
            System.out.println(checksum);
        }

        return resultFrom(metrics);
    }

    private static Result runListRandomAccess(
            int[] data,
            int[] indices
    ) {
        MyLinkedList list = new MyLinkedList();

        for (int value : data) {
            list.add(value);
        }

        Metrics metrics = list.getMetrics();
        metrics.reset();
        metrics.startTimer();

        long checksum = 0;

        for (int index : indices) {
            checksum += list.get(index);
        }

        metrics.stopTimer();

        if (checksum == Long.MIN_VALUE) {
            System.out.println(checksum);
        }

        return resultFrom(metrics);
    }

    // ------------------------------------------------------------
    // W2: Search
    // ------------------------------------------------------------

    private static Result runArraySearch(
            int[] data,
            int[] queries
    ) {
        DynamicArray array = new DynamicArray();

        for (int value : data) {
            array.add(value);
        }

        Metrics metrics = array.getMetrics();
        metrics.reset();
        metrics.startTimer();

        int foundCount = 0;

        for (int query : queries) {
            if (array.contains(query)) {
                foundCount++;
            }
        }

        metrics.stopTimer();

        if (foundCount < 0) {
            System.out.println(foundCount);
        }

        return resultFrom(metrics);
    }

    private static Result runListSearch(
            int[] data,
            int[] queries
    ) {
        MyLinkedList list = new MyLinkedList();

        for (int value : data) {
            list.add(value);
        }

        Metrics metrics = list.getMetrics();
        metrics.reset();
        metrics.startTimer();

        int foundCount = 0;

        for (int query : queries) {
            if (list.contains(query)) {
                foundCount++;
            }
        }

        metrics.stopTimer();

        if (foundCount < 0) {
            System.out.println(foundCount);
        }

        return resultFrom(metrics);
    }

    // ------------------------------------------------------------
    // W3: Insert and remove
    // ------------------------------------------------------------

    private static Result runArrayUpdates(
            int[] data,
            int[] insertedValues,
            int index
    ) {
        DynamicArray array = new DynamicArray();

        for (int value : data) {
            array.add(value);
        }

        Metrics metrics = array.getMetrics();
        metrics.reset();
        metrics.startTimer();

        for (int value : insertedValues) {
            array.add(index, value);
        }

        for (int i = 0; i < UPDATE_OPERATIONS; i++) {
            array.remove(index);
        }

        metrics.stopTimer();

        return resultFrom(metrics);
    }

    private static Result runListUpdates(
            int[] data,
            int[] insertedValues,
            int index
    ) {
        MyLinkedList list = new MyLinkedList();

        for (int value : data) {
            list.add(value);
        }

        Metrics metrics = list.getMetrics();
        metrics.reset();
        metrics.startTimer();

        for (int value : insertedValues) {
            list.add(index, value);
        }

        for (int i = 0; i < UPDATE_OPERATIONS; i++) {
            list.remove(index);
        }

        metrics.stopTimer();

        return resultFrom(metrics);
    }

    // ------------------------------------------------------------
    // W4: MinHeap
    // ------------------------------------------------------------

    private static Result runHeap(int[] data) {
        MinHeap heap = new MinHeap();

        Metrics metrics = heap.getMetrics();
        metrics.reset();
        metrics.startTimer();

        for (int value : data) {
            heap.insert(value);
        }

        int previous = Integer.MIN_VALUE;

        while (heap.size() > 0) {
            int current = heap.extractMin();

            if (current < previous) {
                throw new IllegalStateException(
                        "MinHeap returned elements in the wrong order"
                );
            }

            previous = current;
        }

        metrics.stopTimer();

        return resultFrom(metrics);
    }

    // ------------------------------------------------------------
    // Data generation
    // ------------------------------------------------------------

    private static int[] generateData(int n) {
        Random random = new Random(RANDOM_SEED);
        int[] data = new int[n];

        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt(1_000_000);
        }

        return data;
    }

    private static int[] generateRandomIndices(int n) {
        Random random = new Random(RANDOM_SEED);
        int[] indices = new int[RANDOM_ACCESS_QUERIES];

        for (int i = 0; i < indices.length; i++) {
            indices[i] = random.nextInt(n);
        }

        return indices;
    }

    private static int[] generateSearchQueries(int[] data) {
        Random random = new Random(RANDOM_SEED);
        int[] queries = new int[SEARCH_QUERIES];

        // First half: values that are present.
        for (int i = 0; i < SEARCH_QUERIES / 2; i++) {
            queries[i] = data[random.nextInt(data.length)];
        }

        // Second half: values outside the generated data range.
        for (int i = SEARCH_QUERIES / 2;
             i < SEARCH_QUERIES;
             i++) {
            queries[i] = 1_000_001 + i;
        }

        return queries;
    }

    private static int[] generateInsertedValues() {
        Random random = new Random(RANDOM_SEED);
        int[] values = new int[UPDATE_OPERATIONS];

        for (int i = 0; i < values.length; i++) {
            values[i] = random.nextInt(1_000_000);
        }

        return values;
    }

    // ------------------------------------------------------------
    // Repetitions, median and CSV
    // ------------------------------------------------------------

    private static void runAndWrite(
            java.io.BufferedWriter writer,
            String workload,
            String variant,
            String structure,
            int n,
            CaseRunner runner
    ) throws IOException {

        // Warm-up: run once, but do not include it in the results.
        for (int i = 0; i < WARMUP_RUNS; i++) {
            runner.run();
        }

        Result[] results = new Result[MEASURED_RUNS];

        for (int i = 0; i < MEASURED_RUNS; i++) {
            results[i] = runner.run();
        }

        Arrays.sort(
                results,
                Comparator.comparingDouble(result -> result.timeMillis)
        );

        // Median of five measured runs.
        Result median = results[MEASURED_RUNS / 2];

        String line = String.format(
                Locale.US,
                "%s,%s,%s,%d,%.6f,%d,%d,%d",
                workload,
                variant,
                structure,
                n,
                median.timeMillis,
                median.steps,
                median.moves,
                median.comparisons
        );

        writer.write(line);
        writer.newLine();
        writer.flush();

        System.out.println(line);
    }

    private static Result resultFrom(Metrics metrics) {
        return new Result(
                metrics.getTimeMillis(),
                metrics.getSteps(),
                metrics.getMoves(),
                metrics.getComparisons()
        );
    }

    private interface CaseRunner {
        Result run();
    }

    private static class Result {
        private final double timeMillis;
        private final long steps;
        private final long moves;
        private final long comparisons;

        private Result(
                double timeMillis,
                long steps,
                long moves,
                long comparisons
        ) {
            this.timeMillis = timeMillis;
            this.steps = steps;
            this.moves = moves;
            this.comparisons = comparisons;
        }
    }
}