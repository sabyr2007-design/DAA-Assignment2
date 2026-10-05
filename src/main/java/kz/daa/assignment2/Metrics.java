package kz.daa.assignment2;

public class Metrics {

    private long steps;
    private long moves;
    private long comparisons;

    private long startTime;
    private long endTime;

    public void reset() {
        steps = 0;
        moves = 0;
        comparisons = 0;
        startTime = 0;
        endTime = 0;
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

    public double getTimeMillis() {
        return (endTime - startTime) / 1_000_000.0;
    }

    public void step() {
        steps++;
    }

    public void move() {
        moves++;
    }

    public void comparison() {
        comparisons++;
    }

    public long getSteps() {
        return steps;
    }

    public long getMoves() {
        return moves;
    }

    public long getComparisons() {
        return comparisons;
    }
}
