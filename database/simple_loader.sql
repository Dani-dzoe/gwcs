-- ============================================================================
-- SIMPLE CSV LOADER - Run from project root
-- Usage: sqlite3 database/ghana_waste.db < database/simple_loader.sql
-- ============================================================================

-- Create tables if they don't exist
CREATE TABLE IF NOT EXISTS locations (
    location_id INTEGER PRIMARY KEY,
    name TEXT,
    area TEXT,
    location_type TEXT,
    latitude REAL,
    longitude REAL,
    is_active INTEGER
);

CREATE TABLE IF NOT EXISTS trucks (
    truck_id INTEGER PRIMARY KEY,
    truck_name TEXT,
    truck_type TEXT,
    capacity_kg REAL,
    home_location_id INTEGER,
    availability_status TEXT,
    fuel_level REAL
);

CREATE TABLE IF NOT EXISTS roads (
    road_id INTEGER PRIMARY KEY,
    from_location_id INTEGER,
    to_location_id INTEGER,
    distance_km REAL,
    travel_time_minutes INTEGER,
    road_condition_weight REAL,
    is_one_way INTEGER
);

CREATE TABLE IF NOT EXISTS waste_requests (
    request_id INTEGER PRIMARY KEY,
    source_location_id INTEGER,
    destination_location_id INTEGER,
    category TEXT,
    urgency_level INTEGER,
    weight_estimate_kg REAL,
    volume_estimate_m3 REAL,
    time_submitted TEXT,
    deadline TEXT,
    status TEXT,
    assigned_truck_id INTEGER,
    priority_score REAL
);

-- Clear old data
DELETE FROM waste_requests;
DELETE FROM roads;
DELETE FROM trucks;
DELETE FROM locations;

-- Import CSV files (run from project root)
.mode csv
.import data/locations.csv locations
.import data/trucks.csv trucks
.import data/roads.csv roads
.import data/waste_requests.csv waste_requests

-- Delete header rows that got imported
DELETE FROM locations WHERE name = 'name';
DELETE FROM trucks WHERE truck_name = 'truck_name';
DELETE FROM roads WHERE typeof(from_location_id) = 'text';
DELETE FROM waste_requests WHERE typeof(source_location_id) = 'text';

-- Verify
SELECT '=== DATA LOADED ===' as message;
SELECT 'Locations: ' || COUNT(*) FROM locations;
SELECT 'Trucks: ' || COUNT(*) FROM trucks;
SELECT 'Roads: ' || COUNT(*) FROM roads;
SELECT 'Requests: ' || COUNT(*) FROM waste_requests;
