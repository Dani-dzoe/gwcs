package gh.edu.ug.wastesys.service;

import gh.edu.ug.wastesys.algo.GraphEngine;
import gh.edu.ug.wastesys.algo.EfficiencyLab;
import gh.edu.ug.wastesys.algo.SearchSortEngine;
import gh.edu.ug.wastesys.config.TeamParameters;
import gh.edu.ug.wastesys.db.DatabaseManager;
import gh.edu.ug.wastesys.data.SeedDataFactory;
import gh.edu.ug.wastesys.model.AlgorithmRun;
import gh.edu.ug.wastesys.model.AuditEvent;
import gh.edu.ug.wastesys.model.Location;
import gh.edu.ug.wastesys.model.Resource;
import gh.edu.ug.wastesys.model.Road;
import gh.edu.ug.wastesys.model.ServiceRequest;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class WasteService {
    private final SearchSortEngine searchSortEngine = new SearchSortEngine();
    private final GraphEngine graphEngine = new GraphEngine();
    private final EfficiencyLab efficiencyLab = new EfficiencyLab();
    private final DatabaseManager databaseManager;
    private final TeamParameters teamParameters;
    private final SeedDataFactory seedDataFactory = new SeedDataFactory();
    private final List<Location> seedLocations = new ArrayList<>();
    private final List<Road> seedRoads = new ArrayList<>();
    private final List<ServiceRequest> seedRequests = new ArrayList<>();
    private final List<Resource> seedResources = new ArrayList<>();
    private final List<AlgorithmRun> seedAlgorithmRuns = new ArrayList<>();
    private final List<AuditEvent> seedAuditEvents = new ArrayList<>();

    public WasteService() {
        this(defaultTeamParameters());
    }

    public WasteService(TeamParameters teamParameters) {
        this.teamParameters = teamParameters;
        this.databaseManager = new DatabaseManager("jdbc:sqlite:" + Path.of("wastesys.db").toAbsolutePath());
        this.databaseManager.initializeSchema();
        buildSeedData();
        buildSampleGraph();
    }

    public String loadSeedData() {
        databaseManager.clearData();
        databaseManager.saveAll(seedLocations, seedRoads, seedRequests, seedResources, seedAlgorithmRuns, seedAuditEvents);
        return "Loaded seed data: locations=" + seedLocations.size()
                + ", roads=" + seedRoads.size()
                + ", requests=" + seedRequests.size()
                + ", resources=" + seedResources.size()
                + ", algorithmRuns=" + seedAlgorithmRuns.size()
                + ", auditEvents=" + seedAuditEvents.size();
    }

    public String dispatchSummary() {
        List<Location> locations = databaseManager.loadLocations();
        if (locations.isEmpty()) {
            locations = seedLocations;
        }
        List<String> names = new ArrayList<>();
        for (Location location : locations) {
            names.add(location.name());
        }
        searchSortEngine.selectionSort(names);
        return "Sorted service zones: " + names;
    }

    public String algorithmDemo() {
        GraphEngine.PathResult result = graphEngine.dijkstra(1, 5);
        return "Dijkstra route distance=" + result.distance() + ", path=" + result.path();
    }

    public String teamParameterSummary() {
        return teamParameters.summary();
    }

    public String databaseSummary() {
        return "DB counts: locations=" + databaseManager.count("locations")
                + ", roads=" + databaseManager.count("roads")
                + ", requests=" + databaseManager.count("service_requests")
                + ", resources=" + databaseManager.count("resources")
                + ", algorithmRuns=" + databaseManager.count("algorithm_runs")
                + ", auditEvents=" + databaseManager.count("audit_events");
    }

    public String efficiencyLabSummary() {
        EfficiencyLab.LabResult result = efficiencyLab.runSearchAndSortLab(List.of(100, 500, 1000, 5000, 10000), teamParameters.randomSeed());
        databaseManager.saveAlgorithmRuns(result.runs());
        Path outputFile = Paths.get("results", "efficiency-search-sort.csv");
        efficiencyLab.writeCsv(outputFile, result.csvContent());
        return "Efficiency lab exported to " + outputFile.toAbsolutePath() + " and stored " + result.runs().size() + " algorithm runs.";
    }

    private void buildSeedData() {
        seedLocations.addAll(seedDataFactory.locations());
        seedRoads.addAll(seedDataFactory.roads());
        seedRequests.addAll(seedDataFactory.serviceRequests());
        seedResources.addAll(seedDataFactory.resources());
        seedAlgorithmRuns.addAll(seedDataFactory.algorithmRuns());
        seedAuditEvents.addAll(seedDataFactory.auditEvents());
    }

    private void buildSampleGraph() {
        for (Road road : seedRoads) {
            graphEngine.addEdge(road.fromLocationId(), road.toLocationId(), road.distance());
        }
    }

    private static TeamParameters defaultTeamParameters() {
        return new TeamParameters(List.of(
                22298829, 22141090, 22397138, 22386021, 22337992, 22413979, 22394307,
                10952013, 22059600, 22018743, 22329411, 22389827, 210013
        ));
    }
}
