CREATE TABLE IF NOT EXISTS locations (
    locationId INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    area TEXT NOT NULL,
    type TEXT NOT NULL,
    latitude REAL NOT NULL,
    longitude REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS roads (
    fromLocationId INTEGER NOT NULL,
    toLocationId INTEGER NOT NULL,
    distance REAL NOT NULL,
    travelTime INTEGER NOT NULL,
    roadConditionWeight REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS service_requests (
    requestId INTEGER PRIMARY KEY,
    source INTEGER NOT NULL,
    destination INTEGER NOT NULL,
    category TEXT NOT NULL,
    urgency INTEGER NOT NULL,
    timeSubmitted TEXT NOT NULL,
    deadline TEXT NOT NULL,
    status TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS resources (
    resourceId INTEGER PRIMARY KEY,
    type TEXT NOT NULL,
    homeLocation INTEGER NOT NULL,
    capacity INTEGER NOT NULL,
    availabilityStatus TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS algorithm_runs (
    runId INTEGER PRIMARY KEY,
    algorithmName TEXT NOT NULL,
    inputSize INTEGER NOT NULL,
    timeNs INTEGER NOT NULL,
    memoryKb INTEGER NOT NULL,
    dateRun TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS audit_events (
    eventId INTEGER PRIMARY KEY,
    eventType TEXT NOT NULL,
    eventTime TEXT NOT NULL,
    details TEXT NOT NULL
);
