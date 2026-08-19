package util;

/**
 * Simple nanosecond-precision timer wrapping System.nanoTime().
 */
public class Timer {

    private long startNs;
    private long elapsedNs;
    private boolean running;

    public void start() {
        startNs = System.nanoTime();
        running = true;
    }

    public long stop() {
        if (!running) {
            throw new IllegalStateException("Timer was not started");
        }
        elapsedNs = System.nanoTime() - startNs;
        running = false;
        return elapsedNs;
    }

    public long elapsedNs() {
        return elapsedNs;
    }

    public double elapsedMs() {
        return elapsedNs / 1_000_000.0;
    }
}
