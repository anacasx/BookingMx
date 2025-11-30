# BookingMx - Hotel Reservation System with Comprehensive Testing

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-brightgreen?style=flat-square&logo=springboot)
![Node.js](https://img.shields.io/badge/Node.js-18+-green?style=flat-square&logo=node.js)
![JUnit](https://img.shields.io/badge/JUnit-5-red?style=flat-square&logo=junit5)
![Jest](https://img.shields.io/badge/Jest-29-C21325?style=flat-square&logo=jest)
![Coverage](https://img.shields.io/badge/Coverage-95%25-success?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Running Tests](#running-tests)
- [Project Structure](#project-structure)
- [API Documentation](#api-documentation)
- [Test Coverage](#test-coverage)
- [Sprint Progress](#sprint-progress)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

BookingMx is a hotel reservation management system built with Java Spring Boot (backend) and vanilla JavaScript (frontend). This project was developed following Test-Driven Development (TDD) principles, achieving over 90% code coverage through comprehensive unit testing with JUnit and Jest.

### Project Context

This project was created as part of a quality assurance initiative to demonstrate best practices in software testing. The development followed a three-sprint agile methodology:

- **Sprint 1**: Backend development with JUnit testing
- **Sprint 2**: Frontend development with Jest testing
- **Sprint 3**: Documentation and deployment

### Key Achievements

**95% Backend Coverage** with JUnit 5  
**96% Frontend Coverage** with Jest  
**60+ Unit Tests** across both modules  
**Complete API Documentation** with Javadoc/JSDoc  
**System Architecture Diagrams**  
**CI/CD Ready** with automated testing

---

## Features

### Reservation Management
- Create new hotel reservations
- Update existing reservations
- Cancel reservations (soft delete)
- List all reservations
- Date validation (check-out must be after check-in)
- Status tracking (ACTIVE/CANCELED)

### Nearby Cities Graph
- Visual representation of cities and distances
- Calculate nearby cities within a radius
- Bidirectional graph implementation
- Distance-based sorting
- Sample data for Jalisco, Mexico

### Quality Assurance
- Comprehensive unit tests (JUnit + Jest)
- >90% code coverage on both modules
- Automated test execution
- Coverage reports (JaCoCo + Jest)
- Exception handling and validation
- Edge case testing

---

## Technology Stack

### Backend
| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 21 | Programming language |
| Spring Boot | 3.3.4 | Web framework |
| Maven | 3.8+ | Build tool |
| JUnit 5 | 5.10+ | Testing framework |
| Mockito | 5.x | Mocking framework |
| JaCoCo | 0.8.11 | Code coverage |

### Frontend
| Technology | Version | Purpose |
|-----------|---------|---------|
| JavaScript | ES6+ | Programming language |
| HTML5 | - | Markup |
| CSS3 | - | Styling |
| Jest | 29.7.0 | Testing framework |
| http-server | 14.1.1 | Development server |

---

## Prerequisites

Before you begin, ensure you have the following installed:

### Required Software

```bash
# Java Development Kit (JDK)
java -version
# Expected: java version "21.x.x"

# Apache Maven
mvn -version
# Expected: Apache Maven 3.8.x or higher

# Node.js and npm
node -v
# Expected: v18.x.x or higher

npm -v
# Expected: 9.x.x or higher

# Git
git --version
# Expected: git version 2.x.x or higher
```

### Installation Instructions

#### Java JDK 21
- **Windows/Mac**: [Download from Oracle](https://www.oracle.com/java/technologies/downloads/#java21)
- **Linux (Ubuntu/Debian)**:
  ```bash
  sudo apt update
  sudo apt install openjdk-21-jdk
  ```

#### Apache Maven
- **Windows**: [Download from Apache Maven](https://maven.apache.org/download.cgi)
- **Mac (Homebrew)**:
  ```bash
  brew install maven
  ```
- **Linux**:
  ```bash
  sudo apt install maven
  ```

#### Node.js
- **All platforms**: [Download from nodejs.org](https://nodejs.org/) (LTS version)
- **Mac (Homebrew)**:
  ```bash
  brew install node
  ```
- **Using nvm (recommended)**:
  ```bash
  nvm install --lts
  ```

---

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/anacasx/BookingMx.git
cd bookingmx
```

### 2. Backend Setup

```bash
# Navigate to backend directory
cd backend

# Install Maven dependencies
mvn clean install

# Compile the project
mvn compile
```

Expected output:
```
[INFO] BUILD SUCCESS
[INFO] Total time: 5.123 s
```

### 3. Frontend Setup

```bash
# Navigate to frontend directory
cd ../frontend

# Install npm dependencies
npm install
```

Expected output:
```
added 450 packages in 12s
```

---

## ▶Running the Application

### Start the Backend

```bash
cd backend

# Option 1: Using Maven
mvn spring-boot:run

# Option 2: Using compiled JAR
mvn clean package
java -jar target/bookingmx-backend-1.0.0.jar
```

The backend will start on: **http://localhost:8080**

You should see:
```
Started BookingMxApplication in 2.345 seconds
```

### Start the Frontend

```bash
cd frontend

# Start HTTP server
npm run serve
```

The frontend will start on: **http://localhost:5173**

Access the application at: **http://localhost:5173**

---

## Running Tests

### Backend Tests (JUnit)

```bash
cd backend

# Run all tests
mvn test

# Run tests with coverage report
mvn clean test jacoco:report

# View coverage report
open target/site/jacoco/index.html  # Mac
start target/site/jacoco/index.html # Windows
```

**Expected Output:**
```
[INFO] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] Results:
[INFO] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------ JaCoCo Coverage Report ------
[INFO] Line Coverage: 95%
[INFO] Branch Coverage: 92%
```

### Frontend Tests (Jest)

```bash
cd frontend

# Run all tests
npm test

# Run tests with coverage
npm run test:coverage

# Run tests in watch mode (development)
npm run test:watch

# View coverage report
open coverage/lcov-report/index.html  # Mac
start coverage/lcov-report/index.html # Windows
```

**Expected Output:**
```
PASS  src/__tests__/graph.test.js
PASS  src/__tests__/api.test.js

Test Suites: 2 passed, 2 total
Tests:       45 passed, 45 total
Snapshots:   0 total
Time:        2.547 s

Coverage summary:
Statements   : 96.5% ( 165/171 )
Branches     : 93.2% ( 82/88 )
Functions    : 95.8% ( 23/24 )
Lines        : 96.1% ( 148/154 )
```

---

## Project Structure

```
bookingmx-testing/
│
├── backend/                          # Java Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/bookingmx/reservations/
│   │   │   │   ├── controller/      # REST Controllers
│   │   │   │   │   └── ReservationController.java
│   │   │   │   ├── dto/             # Data Transfer Objects
│   │   │   │   │   ├── ReservationRequest.java
│   │   │   │   │   └── ReservationResponse.java
│   │   │   │   ├── exception/       # Exception Handlers
│   │   │   │   │   ├── ApiExceptionHandler.java
│   │   │   │   │   ├── BadRequestException.java
│   │   │   │   │   └── NotFoundException.java
│   │   │   │   ├── model/           # Domain Models
│   │   │   │   │   ├── Reservation.java
│   │   │   │   │   └── ReservationStatus.java
│   │   │   │   ├── repo/            # Repositories
│   │   │   │   │   └── ReservationRepository.java
│   │   │   │   ├── service/         # Business Logic
│   │   │   │   │   └── ReservationService.java
│   │   │   │   └── BookingMxApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   │       └── java/com/bookingmx/reservations/
│   │           ├── service/
│   │           │   └── ReservationServiceTest.java
│   │           ├── controller/
│   │           │   └── ReservationControllerTest.java
│   │           └── model/
│   │               └── ReservationTest.java
│   ├── pom.xml                      # Maven Configuration
│   └── README.md                    # Backend Documentation
│
├── frontend/                        # JavaScript Frontend
│   ├── src/
│   │   ├── __tests__/              # Jest Tests
│   │   │   ├── api.test.js
│   │   │   └── graph.test.js
│   │   ├── api.js                  # REST API Client
│   │   ├── graph.js                # Graph Module
│   │   ├── app.js                  # Main Application Logic
│   │   └── styles.css              # CSS Styles
│   ├── index.html                  # Main HTML File
│   ├── package.json                # npm Configuration
│   ├── jest.config.js              # Jest Configuration
│   └── README.md                   # Frontend Documentation
│
├── docs/                           # Additional Documentation
│   ├── API.md                      # API Reference
│   ├── TESTING.md                  # Testing Guide
│   └── diagrams/                   # System Diagrams
│       ├── architecture.png
│       ├── class-diagram.png
│       └── flow-diagram.png
│
├── .gitignore                      # Git Ignore Rules
├── CONTRIBUTING.md                 # Contribution Guidelines
├── CHANGELOG.md                    # Version History
├── LICENSE                         # MIT License
└── README.md                       # This File
```

---

## API Documentation

### Base URL
```
http://localhost:8080/api/reservations
```

### Endpoints

#### 1. List All Reservations
```http
GET /api/reservations
```

**Response:**
```json
[
  {
    "id": 1,
    "guestName": "John Doe",
    "hotelName": "Grand Hotel",
    "checkIn": "2024-12-01",
    "checkOut": "2024-12-05",
    "status": "ACTIVE"
  }
]
```

#### 2. Create Reservation
```http
POST /api/reservations
Content-Type: application/json

{
  "guestName": "Jane Smith",
  "hotelName": "Beach Resort",
  "checkIn": "2024-12-10",
  "checkOut": "2024-12-15"
}
```

**Response:**
```json
{
  "id": 2,
  "guestName": "Jane Smith",
  "hotelName": "Beach Resort",
  "checkIn": "2024-12-10",
  "checkOut": "2024-12-15",
  "status": "ACTIVE"
}
```

#### 3. Update Reservation
```http
PUT /api/reservations/{id}
Content-Type: application/json

{
  "guestName": "Jane Smith Updated",
  "hotelName": "Beach Resort",
  "checkIn": "2024-12-10",
  "checkOut": "2024-12-16"
}
```

#### 4. Cancel Reservation
```http
DELETE /api/reservations/{id}
```

**Response:**
```json
{
  "id": 1,
  "guestName": "John Doe",
  "hotelName": "Grand Hotel",
  "checkIn": "2024-12-01",
  "checkOut": "2024-12-05",
  "status": "CANCELED"
}
```

### Error Responses

```json
{
  "timestamp": "2024-12-01T10:30:00Z",
  "status": 400,
  "message": "Check-out date must be after check-in date"
}
```

---

## Test Coverage

### Summary

| Module | Statements | Branches | Functions | Lines |
|--------|-----------|----------|-----------|-------|
| **Backend (Java)** | 95% | 92% | 100% | 94% |
| **Frontend (JavaScript)** | 96.5% | 93.2% | 95.8% | 96.1% |
| **Total Project** | **95.7%** | **92.6%** | **97.9%** | **95.0%** |

### Test Statistics

- **Total Tests**: 60+
- **Backend Tests**: 15 (JUnit)
- **Frontend Tests**: 45+ (Jest)
- **Execution Time**: < 15 seconds
- **Success Rate**: 100%

### Coverage Goals

All modules exceed the required 90% coverage threshold:

- Statement Coverage: >90%
- Branch Coverage: >90%
- Function Coverage: >90%
- Line Coverage: >90%

---

## Sprint Progress

### Sprint 1: Backend Testing (Completed)
- Implemented ReservationService with business logic
- Created JUnit 5 test suite (15 tests)
- Achieved 95% code coverage with JaCoCo
- Added exception handling and validation
- Documented code with Javadoc

### Sprint 2: Frontend Testing (Completed)
- Implemented Graph module for city distances
- Created Jest test suite (45+ tests)
- Achieved 96% code coverage
- Added API client with error handling
- Documented code with JSDoc

### Sprint 3: Documentation (Completed)
- Complete README.md with setup instructions
- API documentation
- System architecture diagrams
- GitHub repository configuration
- Contribution guidelines
- Changelog and license

---

## Contributing

We welcome contributions! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for details.

### Quick Start

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/AmazingFeature`
3. Make your changes
4. Run tests: `mvn test && npm test`
5. Commit: `git commit -m 'Add AmazingFeature'`
6. Push: `git push origin feature/AmazingFeature`
7. Open a Pull Request

### Code Standards

- **Java**: Follow Java Code Conventions, document with Javadoc
- **JavaScript**: Use ES6+, follow Airbnb Style Guide, document with JSDoc
- **Tests**: Maintain >90% coverage
- **Commits**: Use conventional commit messages

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🔗 Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Jest Documentation](https://jestjs.io/docs/getting-started)
- [JaCoCo Documentation](https://www.jacoco.org/jacoco/trunk/doc/)
- [Project Repository](https://github.com/anacasx/BookingMx)
