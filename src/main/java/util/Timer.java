package util;

/**
 * Timer - Utility for measuring execution time
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 */
public class Timer {
    private long startTime;
    private long endTime;

    /**
     * Start timer
     */
    public void start() {
        startTime = System.nanoTime();
    }

    /**
     * Stop timer
     */
    public void stop() {
        endTime = System.nanoTime();
    }

    /**
     * Get elapsed time in nanoseconds
     * CHANGED FROM elapsedNanos TO elapsedNs
     */
    public long elapsedNs() {
        if (endTime == 0) {
            return System.nanoTime() - startTime;
        }
        return endTime - startTime;
    }

    /**
     * Get elapsed time in milliseconds
     */
    public double elapsedMillis() {
        return elapsedNs() / 1_000_000.0;
    }

    /**
     * Get elapsed time in seconds
     */
    public double elapsedSeconds() {
        return elapsedNs() / 1_000_000_000.0;
    }

    /**
     * Reset timer
     */
    public void reset() {
        startTime = 0;
        endTime = 0;
    }
}

