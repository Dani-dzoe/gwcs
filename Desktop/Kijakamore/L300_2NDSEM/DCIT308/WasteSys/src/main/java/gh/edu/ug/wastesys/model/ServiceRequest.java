package gh.edu.ug.wastesys.model;

import java.time.LocalDateTime;

public record ServiceRequest(
        int requestId,
        int source,
        int destination,
        String category,
        int urgency,
        LocalDateTime timeSubmitted,
        LocalDateTime deadline,
        String status,
        double volumeKg,
        double estimatedTimeMinutes
) {
}
