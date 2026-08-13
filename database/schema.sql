-- ============================================================================
-- GHANA SMART WASTE COLLECTION - DATABASE SCHEMA
-- University of Ghana - DCIT 204308 Data Structures and Algorithms
-- Database: SQLite
-- ============================================================================

-- Enable foreign keys
PRAGMA foreign_keys = ON;

-- ============================================================================
-- DROP EXISTING TABLES (in reverse order of dependencies)
-- ============================================================================
DROP TABLE IF EXISTS algorithm_runs;
DROP TABLE IF EXISTS waste_requests;
DROP TABLE IF EXISTS roads;
DROP TABLE IF EXISTS trucks;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS auditevents;

-- ============================================================================
-- TABLE: locations
-- Purpose: Stores all nodes in the waste collection network
-- Minimum: 50 records (we have 56)
-- ============================================================================
CREATE TABLE locations (
    location_id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    area TEXT NOT NULL,
    location_type TEXT NOT NULL CHECK(location_type IN ('community', 'collection_point', 'facility', 'landfill', 'transfer_station')),
    latitude REAL,
    longitude REAL,
    is_active INTEGER DEFAULT 1,
    created_at TEXT DEFAULT CURRENT_TIMESTAMP,
    updated_at TEXT DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for faster lookups
CREATE INDEX idx_locations_name ON locations(name);
CREATE INDEX idx_locations_area ON locations(area);
CREATE INDEX idx_locations_type ON locations(location_type);

-- ============================================================================
-- TABLE: roads
-- Purpose: Stores weighted edges between locations (road network)
-- Minimum: 100 records (we have 207)
-- ============================================================================
CREATE TABLE roads (
    road_id INTEGER PRIMARY KEY AUTOINCREMENT,
    from_location_id INTEGER NOT NULL,
    to_location_id INTEGER NOT NULL,
    distance_km REAL NOT NULL CHECK(distance_km > 0),
    travel_time_minutes INTEGER NOT NULL CHECK(travel_time_minutes > 0),
    road_condition_weight REAL DEFAULT 1.0 CHECK(road_condition_weight >= 0.5 AND road_condition_weight <= 3.0),
    is_one_way INTEGER DEFAULT 0,
    created_at TEXT DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_location_id) REFERENCES locations(location_id) ON DELETE CASCADE,
    FOREIGN KEY (to_location_id) REFERENCES locations(location_id) ON DELETE CASCADE,
    CONSTRAINT chk_different_locations CHECK(from_location_id != to_location_id)
);

-- Indexes for graph traversal
CREATE INDEX idx_roads_from ON roads(from_location_id);
CREATE INDEX idx_roads_to ON roads(to_location_id);
CREATE INDEX idx_roads_pair ON roads(from_location_id, to_location_id);

-- ============================================================================
-- TABLE: trucks
-- Purpose: Stores vehicles/assets that can be assigned
-- Minimum: 30 records (we have 30)
-- ============================================================================
CREATE TABLE trucks (
    truck_id INTEGER PRIMARY KEY AUTOINCREMENT,
    truck_name TEXT NOT NULL,
    truck_type TEXT NOT NULL CHECK(truck_type IN ('compactor', 'skip', 'flatbed', 'mini_truck', 'recycling')),
    capacity_kg REAL NOT NULL CHECK(capacity_kg > 0),
    home_location_id INTEGER,
    availability_status TEXT DEFAULT 'available' CHECK(availability_status IN ('available', 'in_use', 'maintenance', 'out_of_service')),
    fuel_level REAL DEFAULT 100.0 CHECK(fuel_level >= 0 AND fuel_level <= 100),
    current_location_id INTEGER,
    assigned_route_id INTEGER,
    created_at TEXT DEFAULT CURRENT_TIMESTAMP,
    updated_at TEXT DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (home_location_id) REFERENCES locations(location_id),
    FOREIGN KEY (current_location_id) REFERENCES locations(location_id)
);

-- Indexes for truck queries
CREATE INDEX idx_trucks_status ON trucks(availability_status);
CREATE INDEX idx_trucks_home ON trucks(home_location_id);
CREATE INDEX idx_trucks_type ON trucks(truck_type);

-- ============================================================================
-- TABLE: waste_requests
-- Purpose: Stores service requests to be scheduled and processed
-- Minimum: 300 records (we have 300)
-- ============================================================================
CREATE TABLE waste_requests (
    request_id INTEGER PRIMARY KEY AUTOINCREMENT,
    source_location_id INTEGER NOT NULL,
    destination_location_id INTEGER NOT NULL,
    category TEXT NOT NULL CHECK(category IN ('household', 'commercial', 'industrial', 'medical', 'construction', 'recyclable')),
    urgency_level INTEGER NOT NULL CHECK(urgency_level BETWEEN 1 AND 5),
    weight_estimate_kg REAL,
    volume_estimate_m3 REAL,
    time_submitted TEXT NOT NULL,
    deadline TEXT,
    status TEXT DEFAULT 'pending' CHECK(status IN ('pending', 'scheduled', 'in_progress', 'completed', 'cancelled')),
    assigned_truck_id INTEGER,
    priority_score REAL,
    notes TEXT,
    created_at TEXT DEFAULT CURRENT_TIMESTAMP,
    updated_at TEXT DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (source_location_id) REFERENCES locations(location_id),
    FOREIGN KEY (destination_location_id) REFERENCES locations(location_id),
    FOREIGN KEY (assigned_truck_id) REFERENCES trucks(truck_id)
);

-- Indexes for request queries
CREATE INDEX idx_requests_status ON waste_requests(status);
CREATE INDEX idx_requests_urgency ON waste_requests(urgency_level);
CREATE INDEX idx_requests_source ON waste_requests(source_location_id);
CREATE INDEX idx_requests_destination ON waste_requests(destination_location_id);
CREATE INDEX idx_requests_category ON waste_requests(category);
CREATE INDEX idx_requests_submitted ON waste_requests(time_submitted);

-- ============================================================================
-- TABLE: algorithm_runs
-- Purpose: Stores empirical runtime measurements for performance analysis
-- Minimum: 30 records
-- ============================================================================
CREATE TABLE algorithm_runs (
    run_id INTEGER PRIMARY KEY AUTOINCREMENT,
    algorithm_name TEXT NOT NULL,
    algorithm_category TEXT NOT NULL CHECK(algorithm_category IN ('searching', 'sorting', 'graph', 'optimization', 'scheduling', 'data_structure')),
    input_size INTEGER NOT NULL CHECK(input_size > 0),
    time_nanoseconds INTEGER NOT NULL CHECK(time_nanoseconds >= 0),
    time_milliseconds REAL,
    memory_kb INTEGER,
    operations_count INTEGER,
    comparisons_count INTEGER,
    success INTEGER DEFAULT 1,
    error_message TEXT,
    parameters TEXT,
    date_run TEXT DEFAULT CURRENT_TIMESTAMP,
    machine_spec TEXT,
    notes TEXT
);

-- Indexes for performance analysis
CREATE INDEX idx_runs_algorithm ON algorithm_runs(algorithm_name);
CREATE INDEX idx_runs_category ON algorithm_runs(algorithm_category);
CREATE INDEX idx_runs_date ON algorithm_runs(date_run);

-- ============================================================================
-- TABLE: auditevents
-- Purpose: Stores undo/audit operations and system events
-- ============================================================================
CREATE TABLE auditevents (
    audit_id INTEGER PRIMARY KEY AUTOINCREMENT,
    event_type TEXT NOT NULL CHECK(event_type IN ('INSERT', 'UPDATE', 'DELETE', 'UNDO', 'SYSTEM', 'ALGORITHM')),
    table_name TEXT NOT NULL,
    record_id INTEGER,
    old_values TEXT,
    new_values TEXT,
    user_action TEXT,
    timestamp TEXT DEFAULT CURRENT_TIMESTAMP,
    session_id TEXT,
    stack_trace TEXT
);

-- Indexes for audit queries
CREATE INDEX idx_audit_table ON auditevents(table_name);
CREATE INDEX idx_audit_event ON auditevents(event_type);
CREATE INDEX idx_audit_timestamp ON auditevents(timestamp);

-- ============================================================================
-- VIEWS FOR COMMON QUERIES
-- ============================================================================

-- View: Active locations with request counts
CREATE VIEW location_request_summary AS
SELECT 
    l.location_id,
    l.name,
    l.area,
    l.location_type,
    COUNT(wr.request_id) as total_requests,
    SUM(CASE WHEN wr.status = 'pending' THEN 1 ELSE 0 END) as pending_requests,
    SUM(CASE WHEN wr.status = 'completed' THEN 1 ELSE 0 END) as completed_requests
FROM locations l
LEFT JOIN waste_requests wr ON l.location_id = wr.source_location_id
WHERE l.is_active = 1
GROUP BY l.location_id, l.name, l.area, l.location_type;

-- View: Truck availability summary
CREATE VIEW truck_availability_summary AS
SELECT 
    truck_type,
    availability_status,
    COUNT(*) as truck_count,
    AVG(capacity_kg) as avg_capacity,
    AVG(fuel_level) as avg_fuel_level
FROM trucks
GROUP BY truck_type, availability_status;

-- View: Request urgency distribution
CREATE VIEW request_urgency_distribution AS
SELECT 
    urgency_level,
    category,
    status,
    COUNT(*) as request_count,
    AVG(weight_estimate_kg) as avg_weight,
    MIN(time_submitted) as earliest_request,
    MAX(time_submitted) as latest_request
FROM waste_requests
GROUP BY urgency_level, category, status;

-- ============================================================================
-- TRIGGERS FOR AUTOMATIC TIMESTAMP UPDATES
-- ============================================================================

-- Trigger: Update locations timestamp
CREATE TRIGGER update_locations_timestamp 
AFTER UPDATE ON locations
BEGIN
    UPDATE locations SET updated_at = CURRENT_TIMESTAMP WHERE location_id = NEW.location_id;
END;

-- Trigger: Update trucks timestamp
CREATE TRIGGER update_trucks_timestamp 
AFTER UPDATE ON trucks
BEGIN
    UPDATE trucks SET updated_at = CURRENT_TIMESTAMP WHERE truck_id = NEW.truck_id;
END;

-- Trigger: Update waste_requests timestamp
CREATE TRIGGER update_requests_timestamp 
AFTER UPDATE ON waste_requests
BEGIN
    UPDATE waste_requests SET updated_at = CURRENT_TIMESTAMP WHERE request_id = NEW.request_id;
END;

-- ============================================================================
-- SAMPLE DATA INSERTS (for testing)
-- ============================================================================

-- Insert sample locations (56 total)
INSERT INTO locations (name, area, location_type, latitude, longitude, is_active) VALUES
('Osu', 'Accra Metropolitan', 'community', 5.556, -0.1769, 1),
('Oxford Street', 'Osu', 'collection_point', 5.557, -0.176, 1),
('Cantonments', 'Accra Metropolitan', 'community', 5.585, -0.182, 1),
('Airport Residential', 'Accra Metropolitan', 'community', 5.6, -0.178, 1),
('Ridge', 'Accra Metropolitan', 'community', 5.57, -0.195, 1),
('Tema Community 1', 'Tema', 'community', 5.67, -0.01, 1),
('Tema Community 2', 'Tema', 'community', 5.675, 0.0, 1),
('Kpone Landfill', 'Ga East', 'landfill', 5.66, -0.1, 1),
('Sodom Landfill', 'Ga South', 'landfill', 5.52, -0.25, 1);

-- Insert sample trucks (30 total)
INSERT INTO trucks (truck_name, truck_type, capacity_kg, home_location_id, availability_status, fuel_level) VALUES
('Compactor-001', 'compactor', 8000, 1, 'available', 85),
('Compactor-002', 'compactor', 8000, 1, 'available', 90),
('Skip-001', 'skip', 10000, 1, 'available', 88),
('Mini-001', 'mini_truck', 3000, 1, 'available', 95),
('Recycling-001', 'recycling', 5000, 1, 'available', 90);

-- ============================================================================
-- VERIFICATION QUERIES
-- ============================================================================

-- Check table counts
SELECT 'locations' as table_name, COUNT(*) as record_count FROM locations
UNION ALL
SELECT 'roads', COUNT(*) FROM roads
UNION ALL
SELECT 'trucks', COUNT(*) FROM trucks
UNION ALL
SELECT 'waste_requests', COUNT(*) FROM waste_requests
UNION ALL
SELECT 'algorithm_runs', COUNT(*) FROM algorithm_runs;

-- End of schema
