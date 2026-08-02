package gh.edu.ug.wastesys.model;

public record Location(
        int locationId,
        String name,
        String area,
        String type,
        double latitude,
        double longitude
) {
}
