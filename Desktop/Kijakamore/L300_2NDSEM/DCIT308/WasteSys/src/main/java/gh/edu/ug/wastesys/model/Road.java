package gh.edu.ug.wastesys.model;

public record Road(
        int fromLocationId,
        int toLocationId,
        double distance,
        int travelTime,
        double roadConditionWeight
) {
}
