package gh.edu.ug.wastesys.model;

import java.time.LocalDateTime;

public record AuditEvent(
        int eventId,
        String eventType,
        LocalDateTime eventTime,
        String details
) {
}