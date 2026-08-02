package gh.edu.ug.wastesys.model;

import java.time.LocalDateTime;

public record AlgorithmRun(
        int runId,
        String algorithmName,
        int inputSize,
        long timeNs,
        long memoryKb,
        LocalDateTime dateRun
) {
}
