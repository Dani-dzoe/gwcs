package util;

/**
 * Rough in-process memory measurement using Runtime free/total memory.

 */
public class MemoryMonitor {

    private long beforeBytes;
    private long afterBytes;

    public void snapshotBefore() {
        beforeBytes = usedMemory();
    }

    public long snapshotAfter() {
        afterBytes = usedMemory();
        return usedDeltaBytes();
    }

    public long usedDeltaBytes() {
        return afterBytes - beforeBytes;
    }

    public long usedDeltaKb() {
        return usedDeltaBytes() / 1024;
    }

    private long usedMemory() {
        Runtime rt = Runtime.getRuntime();
        System.gc();
        return rt.totalMemory() - rt.freeMemory();
    }
}
