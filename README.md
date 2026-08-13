# Ghana Smart Waste Collection

A comprehensive Data Structures and Algorithms (DSA) project implementing a smart waste collection 
optimization system for Ghanaian municipalities. This project demonstrates custom implementations 
of fundamental data structures, algorithms, and their empirical analysis.

## Project Overview

This system manages waste collection operations across local Ghanaian communities, including:
- **Location Management**: Stores and manages collection points, communities, and facilities
- **Route Planning**: Finds optimal routes between locations using graph algorithms
- **Request Scheduling**: Prioritizes and schedules waste collection requests
- **Truck Assignment**: Optimally assigns trucks to collection routes
- **Performance Analysis**: Empirical comparison of algorithm efficiency

## Project Structure

```
GhanaSmartWasteCollection/
├── database/          # Database schema, seed data, and connection
├── data/              # CSV data files for locations, roads, requests, trucks
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── app/            # Main application entry point and menu
│   │   │   ├── model/          # Data model classes
│   │   │   ├── datastructures/ # Custom data structure implementations
│   │   │   ├── algorithms/     # Algorithm implementations
│   │   │   ├── scheduling/     # Request scheduling strategies
│   │   │   ├── database/       # Database access objects (DAOs)
│   │   │   ├── performance/    # Performance experiments
│   │   │   └── util/           # Utility classes
│   │   └── resources/          # Configuration files
│   └── test/                   # Unit tests for all components
├── experiments/        # Performance experiment results and graphs
├── traces/             # Algorithm execution traces
├── docs/               # Project documentation and diagrams
└── demo/               # Demonstration scripts and Q&A
```

## Key Features

### Data Structures (Custom Implementations)
- Dynamic Array, Linked List, Stack, Queue, Circular Queue, Deque
- Heap, Priority Queue, Binary Search Tree, Red-Black Tree, B-Tree
- Hash Table, Custom Set, Custom Map, Disjoint Set, Graph

### Algorithms
- **Searching**: Linear Search, Binary Search
- **Sorting**: Selection Sort, Insertion Sort, Merge Sort, Quick Sort
- **Graph**: BFS, DFS, Dijkstra, Prim, Kruskal
- **Optimization**: Greedy Truck Assignment, Knapsack DP

### Database Integration
- SQLite database for persistent storage
- Schema with locations, roads, waste_requests, trucks, algorithm_runs tables
- Data loading from CSV files
- Full CRUD operations via DAO pattern

## Requirements

- Java 17 or higher
- Maven 3.6+
- SQLite (included via JDBC driver)

## Building and Running

### Build the project
```bash
mvn clean install
```

### Run the application
```bash
mvn exec:java -Dexec.mainClass="app.Main"
```

Or run the compiled JAR:
```bash
java -jar target/ghana-smart-waste-collection-1.0-SNAPSHOT.jar
```

### Run tests
```bash
mvn test
```

## Database Setup

The database is automatically initialized on first run. The schema includes:

- **locations**: Collection points and communities (50+ records)
- **roads**: Weighted edges between locations (100+ records)
- **waste_requests**: Service requests (300+ records)
- **trucks**: Available collection vehicles (30+ records)
- **algorithm_runs**: Performance experiment results (30+ records)

## Dataset

The project uses realistic Ghanaian local context data:
- Community names from Accra and surrounding areas
- Realistic road networks with distance and travel time weights
- Waste collection requests with urgency levels and deadlines
- Truck fleet with varying capacities and availability

## Performance Experiments

The project includes empirical analysis of:
- Search algorithms (linear vs binary)
- Sorting algorithms (selection, insertion, merge, quicksort)
- Hash table performance under different load factors
- Tree structures (BST vs Red-Black Tree)
- Graph algorithms (BFS, DFS, Dijkstra, MST)

## Team Information

**Course**: DCIT 204308 - Data Structures and Algorithms  
**Institution**: University of Ghana, Department of Computer Science  
**Academic Year**: 2025/2026

## Local Context

This project is localized for waste collection operations in Ghanaian municipalities, 
specifically designed around communities in the Greater Accra Region. The dataset 
includes realistic constraints such as:
- Traffic conditions affecting travel times
- Priority areas requiring urgent collection
- Limited truck capacity and availability
- Operating hours and route constraints

## License

This project is created for educational purposes as part of the DSA course requirements.

## Acknowledgments

- University of Ghana, Department of Computer Science
- DSA Course Instructors
- Team Members
