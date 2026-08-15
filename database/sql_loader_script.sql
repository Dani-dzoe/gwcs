-- ============================================================================
-- CSV DATA LOADER - SQL Script
-- University of Ghana - DCIT 204308 Data Structures and Algorithms
-- 
-- Usage: sqlite3 database/ghana_waste.db < sql_loader_script.sql
-- ============================================================================

-- Enable foreign keys
PRAGMA foreign_keys = ON;

-- ============================================================================
-- CLEAR EXISTING DATA (optional - uncomment if needed)
-- ============================================================================
-- DELETE FROM algorithm_runs;
-- DELETE FROM waste_requests;
-- DELETE FROM roads;
-- DELETE FROM trucks;
-- DELETE FROM locations;

-- ============================================================================
-- IMPORT CSV FILES
-- Note: SQLite .import command requires CSV files to be in same directory
-- or provide full path
-- ============================================================================

-- Import locations
.mode csv
.import --skip 1 data/locations.csv locations_temp

INSERT INTO locations (name, area, location_type, latitude, longitude, is_active)
SELECT name, area, location_type, latitude, longitude, is_active FROM locations_temp;

DROP TABLE locations_temp;

-- Import trucks
.import --skip 1 data/trucks.csv trucks_temp

INSERT INTO trucks (truck_name, truck_type, capacity_kg, home_location_id, availability_status, fuel_level)
SELECT truck_name, truck_type, capacity_kg, home_location_id, availability_status, fuel_level FROM trucks_temp;

DROP TABLE trucks_temp;

-- Import roads
.import --skip 1 data/roads.csv roads_temp

INSERT INTO roads (from_location_id, to_location_id, distance_km, travel_time_minutes, road_condition_weight, is_one_way)
SELECT from_location_id, to_location_id, distance_km, travel_time_minutes, road_condition_weight, is_one_way FROM roads_temp;

DROP TABLE roads_temp;

-- Import waste_requests
.import --skip 1 data/waste_requests.csv waste_requests_temp

INSERT INTO waste_requests (source_location_id, destination_location_id, category, urgency_level, weight_estimate_kg, volume_estimate_m3, time_submitted, deadline, status, priority_score)
SELECT source_location_id, destination_location_id, category, urgency_level, weight_estimate_kg, volume_estimate_m3, time_submitted, deadline, status, priority_score FROM waste_requests_temp;

DROP TABLE waste_requests_temp;

-- ============================================================================
-- VERIFICATION QUERIES
-- ============================================================================

SELECT 'Locations' as table_name, COUNT(*) as record_count FROM locations
UNION ALL
SELECT 'Roads', COUNT(*) FROM roads
UNION ALL
SELECT 'Trucks', COUNT(*) FROM trucks
UNION ALL
SELECT 'Waste Requests', COUNT(*) FROM waste_requests;

-- ============================================================================
-- SAMPLE DATA VIEWS
-- ============================================================================

-- Top 10 locations by request count
SELECT 
    l.name,
    l.area,
    COUNT(wr.request_id) as request_count
FROM locations l
LEFT JOIN waste_requests wr ON l.location_id = wr.source_location_id
GROUP BY l.location_id, l.name, l.area
ORDER BY request_count DESC
LIMIT 10;

-- Truck availability summary
SELECT 
    truck_type,
    availability_status,
    COUNT(*) as count,
    AVG(capacity_kg) as avg_capacity
FROM trucks
GROUP BY truck_type, availability_status
ORDER BY truck_type, availability_status;

-- Request status distribution
SELECT 
    status,
    COUNT(*) as count,
    AVG(urgency_level) as avg_urgency
FROM waste_requests
GROUP BY status
ORDER BY status;

-- End of loader script
