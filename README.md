# BookingMx Backend - Sprint 1

![Java](https://img.shields.io/badge/Java-21-orange) ![Spring
Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-brightgreen)
![JUnit](https://img.shields.io/badge/JUnit-5-red)
![Coverage](https://img.shields.io/badge/Coverage-95%25-success)
![Tests](https://img.shields.io/badge/Tests-48%20passed-success)

## Description

Hotel reservation backend system developed with Java and Spring Boot.
This project includes a complete suite of unit tests using JUnit 5 and
Mockito, achieving over 90% code coverage.

## Sprint 1 Objectives

-   Implement reservation module with MVC architecture
-   Create unit test suite with JUnit 5
-   Achieve code coverage above 90%
-   Document code with Javadoc
-   Configure JaCoCo for coverage reports

## Technologies Used

### Core

-   **Java 21** - Programming language
-   **Spring Boot 3.3.4** - Application framework
-   **Maven 3.8+** - Dependency and build management

### Testing

-   **JUnit 5** - Testing framework
-   **Mockito** - Mocking framework
-   **Spring Boot Test** - Testing for Spring
-   **JaCoCo 0.8.11** - Code coverage analysis

## Prerequisites

-   Java JDK 21 or higher
-   Maven 3.8 or higher
-   Recommended IDE: IntelliJ IDEA or VS Code with Java extensions

### Verify Installation

``` bash
java -version
# Should display: java version "21.x.x"

mvn -version
# Should display: Apache Maven 3.x.x
```

## Installation

### 1. Clone or Create Project

``` bash
mkdir bookingmx
cd backend
```

### 2. Build the Project

``` bash
cd backend
mvn clean install
```

This will download all required dependencies (may take 2-5 minutes the
first time).

### 3. Verify Build

``` bash
mvn clean compile
```

**Expected output:** `BUILD SUCCESS`

## Run Tests

### Run All Tests

``` bash
mvn test
```

**Expected result:**

    [INFO] Tests run: 48, Failures: 0, Errors: 0, Skipped: 0
    [INFO] BUILD SUCCESS

### Generate Coverage Report

``` bash
mvn clean test jacoco:report
```

### View Coverage Report

The HTML report is generated in: `target/site/jacoco/index.html`

**Open in browser:**

``` bash
# Windows
start target\site\jacoco\index.html

# Mac
open target/site/jacoco/index.html

# Linux
xdg-open target/site/jacoco/index.html
```

## Project Structure

    backend/
    ├── pom.xml
    ├── src/
    │   ├── main/
    │   │   ├── java/com/bookingmx/reservations/
    │   │   │   ├── BookingMxApplication.java
    │   │   │   ├── controller/
    │   │   │   │   └── ReservationController.java
    │   │   │   ├── dto/
    │   │   │   │   ├── ReservationRequest.java
    │   │   │   │   └── ReservationResponse.java
    │   │   │   ├── exception/
    │   │   │   │   ├── ApiExceptionHandler.java
    │   │   │   │   ├── BadRequestException.java
    │   │   │   │   └── NotFoundException.java
    │   │   │   ├── model/
    │   │   │   │   ├── Reservation.java
    │   │   │   │   └── ReservationStatus.java
    │   │   │   ├── repo/
    │   │   │   │   └── ReservationRepository.java
    │   │   │   └── service/
    │   │   │       └── ReservationService.java
    │   │   └── resources/
    │   │       └── application.properties
    │   └── test/
    │       └── java/com/bookingmx/reservations/
    │           ├── controller/
    │           │   └── ReservationControllerTest.java
    │           ├── service/
    │           │   └── ReservationServiceTest.java
    │           ├── model/
    │           │   └── ReservationTest.java
    │           └── repo/
    │               └── ReservationRepositoryTest.java
    └── target/
        └── site/jacoco/

## Test Coverage

### Coverage Summary

  Metric         Coverage   Status
  -------------- ---------- --------
  **Lines**      95%        Passed
  **Branches**   92%        Passed
  **Methods**    100%       Passed
  **Classes**    100%       Passed

### Tests per Module

  Module                          Tests    Description
  ------------------------------- -------- -----------------
  **ReservationServiceTest**      15       Business logic
  **ReservationControllerTest**   8        REST endpoints
  **ReservationRepositoryTest**   10       Persistence
  **ReservationTest**             15       Data model
  **TOTAL**                       **48**   **All passing**

### Test Cases Covered

#### ReservationService

-   Create reservation with valid data
-   Validate invalid dates (checkout before checkin)
-   Update existing reservation
-   Cancel active reservation
-   Exception handling
-   List all reservations
-   Business validations

#### ReservationController

-   GET /api/reservations - List all
-   POST /api/reservations - Create new
-   PUT /api/reservations/{id} - Update
-   DELETE /api/reservations/{id} - Cancel
-   Input validations
-   HTTP responses
-   CORS configuration

#### ReservationRepository

-   Save with generated ID
-   FindById success and not found
-   FindAll with and without data
-   Delete
-   Concurrent operations
-   Sequential IDs

#### Reservation (Model)

-   Constructors
-   Getters and Setters
-   isActive()
-   equals() and hashCode()
-   State changes

## API Endpoints

### Base URL

    http://localhost:8080/api/reservations

### Available Endpoints

  Method     Endpoint   Description
  ---------- ---------- ------------------------
  `GET`      `/`        List all reservations
  `POST`     `/`        Create new reservation
  `PUT`      `/{id}`    Update reservation
  `DELETE`   `/{id}`    Cancel reservation

### Usage Examples

#### Create Reservation

**Request:**

``` bash
curl -X POST http://localhost:8080/api/reservations   -H "Content-Type: application/json"   -d '{
    "guestName": "Juan Pérez",
    "hotelName": "Hotel Guadalajara",
    "checkIn": "2025-12-01",
    "checkOut": "2025-12-05"
  }'
```

**Response:**

``` json
{
  "id": 1,
  "guestName": "Juan Pérez",
  "hotelName": "Hotel Guadalajara",
  "checkIn": "2025-12-01",
  "checkOut": "2025-12-05",
  "status": "ACTIVE"
}
```

## Architecture

### Application Layers

    ┌─────────────────────────────────┐
    │    REST Controller Layer        │
    │  ReservationController          │
    └─────────────────────────────────┘
                ↓
    ┌─────────────────────────────────┐
    │    Service Layer                │
    │  ReservationService             │
    └─────────────────────────────────┘
                ↓
    ┌─────────────────────────────────┐
    │    Repository Layer             │
    │  ReservationRepository          │
    └─────────────────────────────────┘
                ↓
    ┌─────────────────────────────────┐
    │    Model Layer                  │
    │  Reservation, Status            │
    └─────────────────────────────────┘

### Design Patterns

-   **MVC (Model-View-Controller)**
-   **DTO (Data Transfer Object)**
-   **Repository Pattern**
-   **Service Layer**
-   **Global Exception Handling**


## Continuous Integration

This project is configured to work with CI/CD.

``` bash
mvn clean verify
```

## Next Steps

1.  Sprint 2: Implement Jest tests for JavaScript frontend
2.  Sprint 3: Full documentation and diagrams
3.  Final: Integration and presentation

