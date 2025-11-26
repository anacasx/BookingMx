# BookingMx - Reservation System with Complete Testing

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-brightgreen)
![JavaScript](https://img.shields.io/badge/JavaScript-ES6+-yellow)
![Node.js](https://img.shields.io/badge/Node.js-18+-green)
![JUnit](https://img.shields.io/badge/JUnit-5-red)
![Jest](https://img.shields.io/badge/Jest-29-C21325)
![Coverage](https://img.shields.io/badge/Coverage-95%25+-success)
![Build](https://img.shields.io/badge/Build-Passing-brightgreen)

## Description

BookingMx is a complete hotel reservation system that demonstrates best practices in software development and testing. The project is divided into:

- **Backend:** REST API developed with Java and Spring Boot
- **Frontend:** Web interface with vanilla JavaScript and graph visualization
- **Testing:** Complete suite with JUnit 5 (backend) and Jest (frontend)

### Main Features

- Full CRUD reservation system
- Visualization of nearby cities using graph algorithms
- 120+ unit tests across backend and frontend
- Code coverage above 90% in both modules
- Complete documentation (Javadoc + JSDoc)
- Robust error handling and validations
- Documented REST API

---

## Project Progress

| Sprint | Goal | Status | Tests | Coverage |
|--------|----------|--------|-------|-----------|
| **Sprint 1** | JUnit Tests (Backend Java) | Completed | 48 tests | 95% |
| **Sprint 2** | Jest Tests (Frontend JS) | Completed | 75 tests | 96.5% |
| **Sprint 3** | Complete Documentation | In progress | - | - |

### Global Project Metrics

```
Total Tests:     123 tests 
Backend:         48 tests (95% coverage) 
Frontend:        75 tests (96.5% coverage)
Average:         95.75% coverage
Status:          ALL PASSING 
```

---

## Prerequisites

### For Backend (Sprint 1)
- Java JDK 21 or higher
- Maven 3.8 or higher

### For Frontend (Sprint 2)
- Node.js 18 or higher
- npm 9 or higher

### Verify Installations

```bash
# Java
java -version
# Expected output: java version "21.x.x"

# Maven
mvn -version
# Expected output: Apache Maven 3.x.x

# Node.js
node -v
# Expected output: v18.x.x or higher

# npm
npm -v
# Expected output: 9.x.x or higher
```

---

## Quick Installation

### Clone the Repository

```bash
git clone https://github.com/anacasx/BookingMx.git
cd BookingMx
```

### Install Backend

```bash
cd backend
mvn clean install
```

### Install Frontend

```bash
cd ../frontend
npm install
```

---

## Run Tests

### Backend Tests (JUnit)

```bash
cd backend

# Run all tests
mvn test

# With coverage report
mvn clean test jacoco:report

# View report (Windows)
start target\site\jacoco\index.html

# View report (Mac)
open target/site/jacoco/index.html
```

Expected result:
```
[INFO] Tests run: 48, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] Results:
[INFO] Line Coverage: 95%
[INFO] Branch Coverage: 92%
[INFO] Method Coverage: 100%
[INFO] BUILD SUCCESS
```

### Frontend Tests (Jest)

```bash
cd frontend

# Run all tests
npm test

# With coverage report
npm run test:coverage

# View report (Windows)
start coverage\lcov-report\index.html

# View report (Mac)
open coverage/lcov-report/index.html
```

Expected result:
```
Test Suites: 2 passed, 2 total
Tests:       75 passed, 75 total
Snapshots:   0 total
Time:        2.847 s

Coverage summary:
  Statements   : 96.5% ( 165/171 )
  Branches     : 93.2% ( 82/88 )
  Functions    : 95.8% ( 23/24 )
  Lines        : 96.1% ( 148/154 )
```

---

## Run the Application

### Start Backend

```bash
cd backend
mvn spring-boot:run
```

The backend server will be available at: `http://localhost:8080`

### Start Frontend

```bash
cd frontend
npm run serve
```

The frontend server will be available at: `http://localhost:5173`

### Access the Application

Open your browser at: `http://localhost:5173`

---

## Project Full Structure

```
bookingmx-testing/
│
├── backend/                          # Backend Java (Sprint 1)
│   ├── src/
│   │   ├── main/java/               # Production code
│   │   │   └── com/bookingmx/reservations/
│   │   │       ├── controller/      # REST Controllers
│   │   │       ├── dto/             # Data Transfer Objects
│   │   │       ├── exception/       # Exception handlers
│   │   │       ├── model/           # Domain models
│   │   │       ├── repo/            # Repositories
│   │   │       └── service/         # Business logic
│   │   ├── test/java/               # JUnit tests
│   │   │   └── com/bookingmx/reservations/
│   │   │       ├── controller/      # Controller tests (8)
│   │   │       ├── service/         # Service tests (15)
│   │   │       ├── model/           # Model tests (15)
│   │   │       └── repo/            # Repository tests (10)
│   │   └── resources/
│   │       └── application.properties
│   ├── pom.xml                      # Maven configuration
│   └── target/
│       └── site/jacoco/             # Coverage reports
│
├── frontend/                        # Frontend JavaScript (Sprint 2)
│   ├── src/
│   │   ├── api.js                   # API client
│   │   ├── graph.js                 # Graph algorithms
│   │   ├── app.js                   # Main application
│   │   ├── styles.css               # Styles
│   │   └── __tests__/               # Jest tests
│   │       ├── api.test.js          # API tests (30)
│   │       └── graph.test.js        # Graph tests (45)
│   ├── index.html                   # Main page
│   ├── package.json                 # npm configuration
│   ├── jest.config.js               # Jest configuration
│   └── coverage/                    # Coverage reports
│
├── docs/                            # Documentation (Sprint 3)
│   ├── diagrams/                    # System diagrams
│   └── testing-report.md            # Testing report
│
├── .gitignore                       # Git ignore rules
├── LICENSE                          # Project license
└── README.md                        # This file
```

---

## Detailed Test Coverage

### Sprint 1: Backend Java (JUnit)

| Module | Tests | Line Coverage | Branch Coverage |
|--------|-------|---------------|-----------------|
| ReservationService | 15 | 97% | 94% |
| ReservationController | 8 | 95% | 90% |
| ReservationRepository | 10 | 100% | 95% |
| Reservation (Model) | 15 | 92% | 88% |
| **TOTAL** | **48** | **95%** | **92%** |

### Sprint 2: Frontend JavaScript (Jest)

| Module | Tests | Statement Coverage | Branch Coverage |
|--------|-------|--------------------|-----------------|
| graph.js | 45 | 96.1% | 92.5% |
| api.js | 30 | 97.2% | 94.1% |
| **TOTAL** | **75** | **96.5%** | **93.2%** |

### Global Summary

```
┌─────────────────────────────────────────────┐
│         TOTAL PROJECT COVERAGE              │
├─────────────────────────────────────────────┤
│  Backend (Java):        95.0%               │
│  Frontend (JavaScript): 96.5%               │
│  ────────────────────────────────────────   │
│  GLOBAL AVERAGE:       95.75%               │
│  Total Tests:           123                 │
│  Tests Passing:         123/123 (100%)      │
└─────────────────────────────────────────────┘
```

---

## API Documentation

### Base URL
```
http://localhost:8080/api/reservations
```

### Endpoints

#### List All Reservations
```http
GET /api/reservations
```

**Response:**
```json
[
  {
    "id": 1,
    "guestName": "Juan Pérez",
    "hotelName": "Hotel Guadalajara",
    "checkIn": "2025-12-01",
    "checkOut": "2025-12-05",
    "status": "ACTIVE"
  }
]
```

#### Create New Reservation
```http
POST /api/reservations
Content-Type: application/json

{
  "guestName": "Juan Pérez",
  "hotelName": "Hotel Guadalajara",
  "checkIn": "2025-12-01",
  "checkOut": "2025-12-05"
}
```

#### Update Reservation
```http
PUT /api/reservations/{id}
Content-Type: application/json

{
  "guestName": "Juan Pérez Updated",
  "hotelName": "Hotel Guadalajara",
  "checkIn": "2025-12-01",
  "checkOut": "2025-12-06"
}
```

#### Cancel Reservation
```http
DELETE /api/reservations/{id}
```

### HTTP Status Codes

| Code | Meaning |
|------|---------|
| 200 | Success |
| 400 | Bad Request (validation failed) |
| 404 | Not Found (reservation does not exist) |
| 500 | Internal Server Error |

---

## Use Cases Covered

### Backend (Sprint 1)

#### ReservationService
- Create reservation with valid data
- Validate that check-out is after check-in
- Update existing reservation
- Cancel active reservation
- Prevent updating a cancelled reservation
- Prevent cancelling a reservation already cancelled
- List all reservations
- Handle custom exceptions

#### ReservationController
- REST endpoints working correctly
- Input validations with Bean Validation
- Appropriate HTTP responses
- CORS configuration
- JSON serialization/deserialization

#### ReservationRepository
- Basic CRUD operations
- Auto-generation of IDs
- Find by ID
- Thread-safe operations (ConcurrentHashMap)

#### Reservation (Model)
- Constructors and getters/setters
- Business method isActive()
- Correct equals() and hashCode()
- State transitions

### Frontend (Sprint 2)

#### Graph Module
- Add cities to the graph
- Validate city names
- Add edges with distances
- Validate negative or invalid distances
- Get neighbors of a city
- Validate graph data structure
- Build graph from data
- Calculate nearby cities
- Sort results by distance
- Handle edge cases (isolated cities, empty graphs)

#### API Client
- List reservations
- Create new reservation
- Update existing reservation
- Cancel reservation
- Handle HTTP errors (404, 400, 500)
- Handle network errors
- Payload serialization
- Mocking fetch API

---

## System Architecture

### Backend Architecture (MVC Pattern)

```
┌─────────────────────────────────────────────┐
│           Client (Frontend)                 │
└─────────────────┬───────────────────────────┘
                  │ HTTP/JSON
                  ↓
┌─────────────────────────────────────────────┐
│         Controller Layer                    │
│    (REST API Endpoints)                     │
│    ReservationController                    │
└─────────────────┬───────────────────────────┘
                  │
                  ↓
┌─────────────────────────────────────────────┐
│         Service Layer                       │
│    (Business Logic)                         │
│    ReservationService                       │
└─────────────────┬───────────────────────────┘
                  │
                  ↓
┌─────────────────────────────────────────────┐
│         Repository Layer                    │
│    (Data Persistence)                       │
│    ReservationRepository                    │
└─────────────────┬───────────────────────────┘
                  │
                  ↓
┌─────────────────────────────────────────────┐
│         Model Layer                         │
│    (Domain Entities)                        │
│    Reservation, ReservationStatus           │
└─────────────────────────────────────────────┘
```

### Frontend Architecture

```
┌─────────────────────────────────────────────┐
│            User Interface                   │
│         (HTML + CSS + DOM)                  │
└─────────────────┬───────────────────────────┘
                  │
                  ↓
┌─────────────────────────────────────────────┐
│         Application Layer                   │
│         (app.js)                            │
│    - Event handlers                         │
│    - UI updates                             │
└───────┬─────────────────┬───────────────────┘
        │                 │
        ↓                 ↓
┌───────────────┐ ┌──────────────────────────┐
│   Graph       │ │    API Client            │
│   Module      │ │    Module                │
│   (graph.js)  │ │    (api.js)              │
│               │ │                          │
│  - Graph      │ │  - listReservations()    │
│  - Algorithms │ │  - createReservation()   │
│  - Validation │ │  - updateReservation()   │
└───────────────┘ │  - cancelReservation()   │
                  └──────────┬───────────────┘
                             │
                             ↓
                  ┌─────────────────────────┐
                  │   Backend REST API      │
                  │   (Spring Boot)         │
                  └─────────────────────────┘
```

---

## Implemented Validations

### Backend Validations
- Guest name must not be empty
- Hotel name must not be empty
- Check-in must be a future date
- Check-out must be a future date
- Check-out must be after check-in
- Cannot update a cancelled reservation
- Cannot cancel a reservation already cancelled

### Frontend Validations
- City must be a non-empty string
- Distance must be a non-negative number
- City must exist in the graph
- Data structure validation
- Handle empty or inconsistent data

---

## Project Metrics

### Execution Time

| Operation | Time |
|-----------|--------|
| Backend Tests | ~10-15 seconds |
| Frontend Tests | ~2-3 seconds |
| Backend Build | ~30 seconds |
| Frontend Build | ~5 seconds |
| **Total CI/CD** | **~50 seconds** |

---

## Additional Documentation

### Individual Sprints

- [Sprint 1 README](https://github.com/anacasx/BookingMx/blob/sprint1/README.md) - Backend and JUnit
- [Sprint 2 README](https://github.com/anacasx/BookingMx/blob/sprint2/README.md) - Frontend and Jest
- Sprint 3 README - Pending

### Learning Resources

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Jest Documentation](https://jestjs.io/docs/getting-started)
- [Spring Boot Testing](https://spring.io/guides/gs/testing-web/)
- [JavaScript Testing Best Practices](https://github.com/goldbergyoni/javascript-testing-best-practices)

---

## Project Status

```
╔════════════════════════════════════════════╗
║     BOOKINGMX TESTING PROJECT STATUS       ║
╠════════════════════════════════════════════╣
║                                            ║
║    Sprint 1: COMPLETE (95% coverage)       ║
║    Sprint 2: COMPLETE (96.5% coverage)     ║
║    Sprint 3: IN PROGRESS                   ║
║                                            ║
║    Total Tests: 123                        ║
║    Coverage: 95.75%                        ║
║    Status: ALL PASSING                     ║
║                                            ║
╚════════════════════════════════════════════╝
```

---

**Updated:** After completing Sprint 1 and Sprint 2
**Version:** 2.0.0
**Status:** 2/3 Sprints Completed
