package gh.edu.ug.wastesys.data;

import gh.edu.ug.wastesys.model.AlgorithmRun;
import gh.edu.ug.wastesys.model.AuditEvent;
import gh.edu.ug.wastesys.model.Location;
import gh.edu.ug.wastesys.model.Resource;
import gh.edu.ug.wastesys.model.Road;
import gh.edu.ug.wastesys.model.ServiceRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class SeedDataFactory {
    private static final String[] LOCATION_NAMES = {
            "Korle Bu", "Kaneshie Market", "Madina", "East Legon", "Tema Station",
            "Dansoman", "Achimota", "Labone", "Osu", "Circle",
            "Airport Residential", "Spintex", "Teshie", "Amasaman", "Lapaz",
            "Nima", "Kanda", "Mampong Road", "Bortianor", "Weija",
            "Kasoa Toll", "Avenor", "Dansoman Junction", "Adabraka", "Pig Farm",
            "Sakumono", "Tema Community 1", "Tema Community 25", "Kokomlemle", "Korle Gonno",
            "Bubuashie", "Abokobi", "Oyibi", "Ashaiman", "Odorkor",
            "Ablekuma", "North Kaneshie", "South Legon", "Labadi", "Ring Road",
            "Atomic Junction", "Pokuase", "Mile 7", "Darkuman", "Kisseman",
            "Tudu", "Gbawe", "Gonno", "Newtown", "Kwashieman"
    };

    private static final String[] AREAS = {
            "Accra", "Greater Accra", "Tema", "Kasoa", "Ashanti"
    };

    private static final String[] TYPES = {
            "Residential", "Commercial", "Market", "Hospital Zone", "Transport Hub", "Community"
    };

    private static final String[] REQUEST_CATEGORIES = {
            "Waste Pickup", "Overflow Bin", "Transfer Run", "Urgent Spill", "Medical Waste"
    };

    private static final String[] RESOURCE_TYPES = {
            "Truck", "Tricycle", "Supervisor", "Loader", "Pickup"
    };

    private static final String[] ALGORITHM_NAMES = {
            "Linear Search", "Binary Search", "Selection Sort", "Insertion Sort", "Merge Sort",
            "Quicksort", "BFS", "DFS", "Dijkstra", "Prim", "Kruskal", "Greedy Dispatch", "Knapsack DP"
    };

    private static final String[] EVENT_TYPES = {
            "LOAD", "SAVE", "ROUTE", "SEARCH", "SORT", "MST"
    };

    public List<Location> locations() {
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            String name = LOCATION_NAMES[i];
            String area = AREAS[i % AREAS.length];
            String type = TYPES[i % TYPES.length];
            double latitude = 5.40 + (i * 0.007);
            double longitude = -0.35 + (i * 0.004);
            locations.add(new Location(i + 1, name, area, type, latitude, longitude));
        }
        return locations;
    }

    public List<Road> roads() {
        List<Road> roads = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            int next = (i % 50) + 1;
            roads.add(new Road(i, next, 2.0 + (i % 9), 8 + (i % 15), 0.8 + ((i % 5) * 0.1)));
        }
        for (int i = 1; i <= 50 && roads.size() < 100; i++) {
            int jump = ((i + 7) % 50) + 1;
            roads.add(new Road(i, jump, 3.5 + (i % 11), 10 + (i % 17), 0.9 + ((i % 4) * 0.15)));
        }
        return roads;
    }

    public List<ServiceRequest> serviceRequests() {
        List<Location> locations = locations();
        List<ServiceRequest> requests = new ArrayList<>();
        LocalDateTime base = LocalDateTime.of(2026, 7, 1, 8, 0);
        for (int i = 0; i < 300; i++) {
            int source = locations.get(i % locations.size()).locationId();
            int destination = locations.get((i * 7 + 3) % locations.size()).locationId();
            if (source == destination) {
                destination = (destination % locations.size()) + 1;
            }
            String category = REQUEST_CATEGORIES[i % REQUEST_CATEGORIES.length];
            int urgency = 1 + (i % 10);
            LocalDateTime submitted = base.plusMinutes(i * 12L);
            LocalDateTime deadline = submitted.plusHours(2 + (i % 4));
            String status = switch (i % 3) {
                case 0 -> "OPEN";
                case 1 -> "ASSIGNED";
                default -> "CLOSED";
            };
            double volumeKg = 1.0 + (i % 8);
            double estimatedMinutes = 10 + (i % 45);
            requests.add(new ServiceRequest(i + 1, source, destination, category, urgency, submitted, deadline, status, volumeKg, estimatedMinutes));
        }
        return requests;
    }

    public List<Resource> resources() {
        List<Location> locations = locations();
        List<Resource> resources = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String type = RESOURCE_TYPES[i % RESOURCE_TYPES.length];
            int homeLocation = locations.get(i % locations.size()).locationId();
            int capacity = 5 + (i % 10);
            String status = i % 4 == 0 ? "AVAILABLE" : "IN_SERVICE";
            resources.add(new Resource(i + 1, type, homeLocation, capacity, status));
        }
        return resources;
    }

    public List<AlgorithmRun> algorithmRuns() {
        List<AlgorithmRun> runs = new ArrayList<>();
        LocalDateTime base = LocalDateTime.of(2026, 7, 1, 9, 0);
        for (int i = 0; i < 30; i++) {
            String algorithmName = ALGORITHM_NAMES[i % ALGORITHM_NAMES.length];
            int inputSize = 100 + (i * 250);
            long timeNs = 1_000_000L + (i * 125_000L);
            long memoryKb = 128L + (i * 16L);
            runs.add(new AlgorithmRun(i + 1, algorithmName, inputSize, timeNs, memoryKb, base.plusDays(i)));
        }
        return runs;
    }

    public List<AuditEvent> auditEvents() {
        List<AuditEvent> events = new ArrayList<>();
        LocalDateTime base = LocalDateTime.of(2026, 7, 1, 10, 0);
        for (int i = 0; i < 30; i++) {
            events.add(new AuditEvent(i + 1, EVENT_TYPES[i % EVENT_TYPES.length], base.plusHours(i), "Seed event " + (i + 1)));
        }
        return events;
    }
}