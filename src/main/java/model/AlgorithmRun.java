package model;

import java.sql.Timestamp;

/**
 * AlgorithmRun - Model class for algorithm performance measurements
 * University of Ghana - DCIT 204308 Data Structures and Algorithms
 * 
 * Records empirical runtime and memory measurements for algorithm analysis.
 */
public class AlgorithmRun {
    private int runId;
    private String algorithmName;
    private String algorithmCategory;
    private int inputSize;
    private long timeNanoseconds;
    private Double timeMilliseconds;
    private Integer memoryKb;
    private Integer operationsCount;
    private Integer comparisonsCount;
    private boolean success;
    private String errorMessage;
    private String parameters;
    private Timestamp dateRun;
    private String machineSpec;
    private String notes;

    /**
     * Default constructor
     */
    public AlgorithmRun() {
        this.success = true;
        this.dateRun = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Constructor with required fields
     */
    public AlgorithmRun(String algorithmName, String algorithmCategory, 
                        int inputSize, long timeNanoseconds) {
        this.algorithmName = algorithmName;
        this.algorithmCategory = algorithmCategory;
        this.inputSize = inputSize;
        this.timeNanoseconds = timeNanoseconds;
        this.timeMilliseconds = timeNanoseconds / 1_000_000.0;
        this.success = true;
        this.dateRun = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Full constructor
     */
    public AlgorithmRun(int runId, String algorithmName, String algorithmCategory,
                        int inputSize, long timeNanoseconds, Double timeMilliseconds,
                        Integer memoryKb, Integer operationsCount, Integer comparisonsCount,
                        boolean success, String errorMessage, String parameters,
                        Timestamp dateRun, String machineSpec, String notes) {
        this.runId = runId;
        this.algorithmName = algorithmName;
        this.algorithmCategory = algorithmCategory;
        this.inputSize = inputSize;
        this.timeNanoseconds = timeNanoseconds;
        this.timeMilliseconds = timeMilliseconds;
        this.memoryKb = memoryKb;
        this.operationsCount = operationsCount;
        this.comparisonsCount = comparisonsCount;
        this.success = success;
        this.errorMessage = errorMessage;
        this.parameters = parameters;
        this.dateRun = dateRun;
        this.machineSpec = machineSpec;
        this.notes = notes;
    }

    /**
     * Compatibility Constructor for wisdom-performance branch runners
     */
    public AlgorithmRun(String runId, String algorithmName, int inputSize, long timeNanoseconds, long memoryKb, String dateRun) {
        try {
            this.runId = Integer.parseInt(runId);
        } catch (NumberFormatException e) {
            this.runId = 0; // Fallback default if UUID strings are used. //
        }
        this.algorithmName = algorithmName;
        this.algorithmCategory = "Performance Test";
        this.inputSize = inputSize;
        this.timeNanoseconds = timeNanoseconds;
        this.timeMilliseconds = timeNanoseconds / 1_000_000.0;
        this.memoryKb = (int) memoryKb;
        this.success = true;
        try {
            this.dateRun = Timestamp.valueOf(dateRun);
        } catch (Exception e) {
            this.dateRun = new Timestamp(System.currentTimeMillis());
        }
    }
    // Getters and Setters

    public int getRunId() {
        return runId;
    }

    public void setRunId(int runId) {
        this.runId = runId;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getAlgorithmCategory() {
        return algorithmCategory;
    }

    public void setAlgorithmCategory(String algorithmCategory) {
        this.algorithmCategory = algorithmCategory;
    }

    public int getInputSize() {
        return inputSize;
    }

    public void setInputSize(int inputSize) {
        this.inputSize = inputSize;
    }

    public long getTimeNanoseconds() {
        return timeNanoseconds;
    }

    public void setTimeNanoseconds(long timeNanoseconds) {
        this.timeNanoseconds = timeNanoseconds;
        this.timeMilliseconds = timeNanoseconds / 1_000_000.0;
    }

    public Double getTimeMilliseconds() {
        return timeMilliseconds;
    }

    public void setTimeMilliseconds(Double timeMilliseconds) {
        this.timeMilliseconds = timeMilliseconds;
    }

    public Integer getMemoryKb() {
        return memoryKb;
    }

    public void setMemoryKb(Integer memoryKb) {
        this.memoryKb = memoryKb;
    }

    public Integer getOperationsCount() {
        return operationsCount;
    }

    public void setOperationsCount(Integer operationsCount) {
        this.operationsCount = operationsCount;
    }

    public Integer getComparisonsCount() {
        return comparisonsCount;
    }

    public void setComparisonsCount(Integer comparisonsCount) {
        this.comparisonsCount = comparisonsCount;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public Timestamp getDateRun() {
        return dateRun;
    }

    public void setDateRun(Timestamp dateRun) {
        this.dateRun = dateRun;
    }

    public String getMachineSpec() {
        return machineSpec;
    }

    public void setMachineSpec(String machineSpec) {
        this.machineSpec = machineSpec;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Get time in milliseconds (calculated if not set)
     */
    public double getCalculatedTimeMs() {
        if (timeMilliseconds != null) {
            return timeMilliseconds;
        }
        return timeNanoseconds / 1_000_000.0;
    }

    /**
     * Get complexity class based on algorithm name
     */
    public String getExpectedComplexity() {
        String name = algorithmName.toLowerCase();
        if (name.contains("binary")) return "O(log n)";
        if (name.contains("linear")) return "O(n)";
        if (name.contains("merge")) return "O(n log n)";
        if (name.contains("quick")) return "O(n log n)";
        if (name.contains("insertion")) return "O(n^2)";
        if (name.contains("selection")) return "O(n^2)";
        if (name.contains("dijkstra")) return "O((V+E) log V)";
        if (name.contains("kruskal")) return "O(E log E)";
        if (name.contains("prim")) return "O((V+E) log V)";
        return "Unknown";
    }

    /** CSV row representation from wisdom-performance branch adapted to main schema */
    public String toCsvRow() {
        return runId + "," + algorithmName + "," + inputSize + "," +
               timeNanoseconds + "," + memoryKb + "," + dateRun;
    }

    @Override
    public String toString() {
        return "AlgorithmRun{" +
               "algorithm='" + algorithmName + '\'' +
               ", category='" + algorithmCategory + '\'' +
               ", inputSize=" + inputSize +
               ", time=" + getCalculatedTimeMs() + "ms" +
               ", success=" + success +
               '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AlgorithmRun that = (AlgorithmRun) obj;
        return runId == that.runId;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(runId);
    }
}

