package model;



/**
 * Mirrors the "algorithm_runs" table.
 * One instance = one timed execution of an algorithm at a given input size.
 */
public class AlgorithmRun {

    private final String runId;
    private final String algorithmName;
    private final int inputSize;
    private final long timeNs;
    private final long memoryKb;
    private final String dateRun;

    public AlgorithmRun(String runId, String algorithmName, int inputSize,
                         long timeNs, long memoryKb, String dateRun) {
        this.runId = runId;
        this.algorithmName = algorithmName;
        this.inputSize = inputSize;
        this.timeNs = timeNs;
        this.memoryKb = memoryKb;
        this.dateRun = dateRun;
    }

    public String getRunId() { return runId; }
    public String getAlgorithmName() { return algorithmName; }
    public int getInputSize() { return inputSize; }
    public long getTimeNs() { return timeNs; }
    public long getMemoryKb() { return memoryKb; }
    public String getDateRun() { return dateRun; }

    /** CSV row representation: runId,algorithmName,inputSize,timeNs,memoryKb,dateRun */
    public String toCsvRow() {
        return runId + "," + algorithmName + "," + inputSize + "," +
               timeNs + "," + memoryKb + "," + dateRun;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s n=%d time=%dns mem=%dKB (%s)",
                runId, algorithmName, inputSize, timeNs, memoryKb, dateRun);
    }
}
