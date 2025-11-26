# BookingMx API Documentation

## Base Information

- **Base URL:** `http://localhost:8080`
- **API Version:** 1.0
- **Content-Type:** `application/json`
- **Accept:** `application/json`

## Authentication

Current version: No authentication required  
Future versions will implement JWT-based authentication.

## Endpoints

### Reservations

All reservation endpoints are prefixed with `/api/reservations`

---

### 1. List All Reservations

Retrieves a list of all reservations in the system.

**Endpoint:** `GET /api/reservations`

**Request:**
```http
GET /api/reservations HTTP/1.1
Host: localhost:8080
Accept: application/json
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "guestName": "John Doe",
    "hotelName": "Grand Hotel",
    "checkIn": "2025-12-01",
    "checkOut": "2025-12-05",
    "status": "ACTIVE"
  },
  {
    "id": 2,
    "guestName": "Jane Smith",
    "hotelName": "Beach Resort",
    "checkIn": "2025-12-10",
    "checkOut": "2025-12-15",
    "status": "ACTIVE"
  }
]
```

**Empty Response (200 OK):**
```json
[]
```

**cURL Example:**
```bash
curl -X GET http://localhost:8080/api/reservations \
  -H "Accept: application/json"
```

---

### 2. Create Reservation

Creates a new hotel reservation.

**Endpoint:** `POST /api/reservations`

**Request Headers:**
```
Content-Type: application/json
Accept: application/json
```

**Request Body:**
```json
{
  "guestName": "John Doe",
  "hotelName": "Grand Hotel",
  "checkIn": "2025-12-01",
  "checkOut": "2025-12-05"
}
```

**Field Validations:**
| Field | Type | Required | Constraints |
|-------|------|----------|-------------|
| guestName | string | Yes | Not blank |
| hotelName | string | Yes | Not blank |
| checkIn | date (ISO 8601) | Yes | Future date |
| checkOut | date (ISO 8601) | Yes | Future date, after checkIn |

**Success Response (200 OK):**
```json
{
  "id": 1,
  "guestName": "John Doe",
  "hotelName": "Grand Hotel",
  "checkIn": "2025-12-01",
  "checkOut": "2025-12-05",
  "status": "ACTIVE"
}
```

**Error Responses:**

**400 Bad Request - Validation Error:**
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 400,
  "message": "Check-out date must be after check-in date"
}
```

**400 Bad Request - Blank Field:**
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 400,
  "message": "Guest name cannot be blank"
}
```

**400 Bad Request - Past Date:**
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 400,
  "message": "Check-in date must be in the future"
}
```

**cURL Example:**
```bash
curl -X POST http://localhost:8080/api/reservations \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "guestName": "John Doe",
    "hotelName": "Grand Hotel",
    "checkIn": "2025-12-01",
    "checkOut": "2025-12-05"
  }'
```

**JavaScript Example:**
```javascript
const response = await fetch('http://localhost:8080/api/reservations', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  },
  body: JSON.stringify({
    guestName: 'John Doe',
    hotelName: 'Grand Hotel',
    checkIn: '2025-12-01',
    checkOut: '2025-12-05'
  })
});

const reservation = await response.json();
```

---

### 3. Update Reservation

Updates an existing reservation. Only ACTIVE reservations can be updated.

**Endpoint:** `PUT /api/reservations/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| id | integer | Reservation ID |

**Request Headers:**
```
Content-Type: application/json
Accept: application/json
```

**Request Body:**
```json
{
  "guestName": "John Doe Updated",
  "hotelName": "Grand Hotel",
  "checkIn": "2025-12-01",
  "checkOut": "2025-12-06"
}
```

**Success Response (200 OK):**
```json
{
  "id": 1,
  "guestName": "John Doe Updated",
  "hotelName": "Grand Hotel",
  "checkIn": "2025-12-01",
  "checkOut": "2025-12-06",
  "status": "ACTIVE"
}
```

**Error Responses:**

**404 Not Found:**
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 404,
  "message": "Reservation not found with id: 999"
}
```

**400 Bad Request - Canceled Reservation:**
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 400,
  "message": "Cannot update a canceled reservation"
}
```

**cURL Example:**
```bash
curl -X PUT http://localhost:8080/api/reservations/1 \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "guestName": "John Doe Updated",
    "hotelName": "Grand Hotel",
    "checkIn": "2025-12-01",
    "checkOut": "2025-12-06"
  }'
```

---

### 4. Cancel Reservation

Cancels an existing reservation by setting its status to CANCELED.

**Endpoint:** `DELETE /api/reservations/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| id | integer | Reservation ID |

**Request:**
```http
DELETE /api/reservations/1 HTTP/1.1
Host: localhost:8080
Accept: application/json
```

**Success Response (200 OK):**
```json
{
  "id": 1,
  "guestName": "John Doe",
  "hotelName": "Grand Hotel",
  "checkIn": "2025-12-01",
  "checkOut": "2025-12-05",
  "status": "CANCELED"
}
```

**Error Responses:**

**404 Not Found:**
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 404,
  "message": "Reservation not found with id: 999"
}
```

**400 Bad Request - Already Canceled:**
```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 400,
  "message": "Reservation is already canceled"
}
```

**cURL Example:**
```bash
curl -X DELETE http://localhost:8080/api/reservations/1 \
  -H "Accept: application/json"
```

---

## Data Models

### ReservationRequest

Input model for creating or updating reservations.

```json
{
  "guestName": "string (required, not blank)",
  "hotelName": "string (required, not blank)",
  "checkIn": "date (required, ISO 8601, future)",
  "checkOut": "date (required, ISO 8601, future, after checkIn)"
}
```

### ReservationResponse

Output model returned by the API.

```json
{
  "id": "integer (generated)",
  "guestName": "string",
  "hotelName": "string",
  "checkIn": "date (ISO 8601)",
  "checkOut": "date (ISO 8601)",
  "status": "enum (ACTIVE | CANCELED)"
}
```

### ReservationStatus

Enumeration of possible reservation statuses.

| Status | Description |
|--------|-------------|
| ACTIVE | Reservation is active and valid |
| CANCELED | Reservation has been canceled |

---

## HTTP Status Codes

| Code | Meaning | Usage |
|------|---------|-------|
| 200 | OK | Successful request |
| 400 | Bad Request | Validation failed or business rule violated |
| 404 | Not Found | Reservation ID does not exist |
| 500 | Internal Server Error | Unexpected server error |

---

## CORS Configuration

The API is configured to accept requests from:
- `http://localhost:5173`
- `http://127.0.0.1:5173`

Allowed methods: `GET`, `POST`, `PUT`, `DELETE`

---

## Rate Limiting

Current version: No rate limiting  
Production recommendation: 100 requests per minute per IP

---

## Error Response Format

All error responses follow this format:

```json
{
  "timestamp": "ISO 8601 timestamp",
  "status": "HTTP status code",
  "message": "Human-readable error message"
}
```

Example:
```json
{
  "timestamp": "2024-01-15T10:30:00.123Z",
  "status": 400,
  "message": "Check-out date must be after check-in date"
}
```

---

## Business Rules

### Date Validation
1. Both check-in and check-out must be future dates
2. Check-out must be after check-in
3. Same-day reservations are not allowed (check-in ≠ check-out)

### Status Management
1. New reservations are created with status ACTIVE
2. Only ACTIVE reservations can be updated
3. Only ACTIVE reservations can be canceled
4. Canceled reservations cannot be reactivated

### Field Validation
1. Guest name cannot be blank or whitespace-only
2. Hotel name cannot be blank or whitespace-only
3. All dates must be in ISO 8601 format (YYYY-MM-DD)

---

## Usage Examples

### Complete Workflow

#### 1. Create a Reservation
```javascript
const newReservation = await fetch('http://localhost:8080/api/reservations', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    guestName: 'John Doe',
    hotelName: 'Grand Hotel',
    checkIn: '2025-12-01',
    checkOut: '2025-12-05'
  })
}).then(r => r.json());

console.log('Created:', newReservation.id);
```

#### 2. List All Reservations
```javascript
const reservations = await fetch('http://localhost:8080/api/reservations')
  .then(r => r.json());

console.log(`Found ${reservations.length} reservations`);
```

#### 3. Update a Reservation
```javascript
const updated = await fetch(`http://localhost:8080/api/reservations/${newReservation.id}`, {
  method: 'PUT',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    guestName: 'John Doe',
    hotelName: 'Grand Hotel',
    checkIn: '2025-12-01',
    checkOut: '2025-12-07' // Extended stay
  })
}).then(r => r.json());

console.log('Updated checkout:', updated.checkOut);
```

#### 4. Cancel a Reservation
```javascript
const canceled = await fetch(`http://localhost:8080/api/reservations/${newReservation.id}`, {
  method: 'DELETE'
}).then(r => r.json());

console.log('Status:', canceled.status); // "CANCELED"
```

---

## Testing the API

### Using cURL

```bash
# List all
curl http://localhost:8080/api/reservations

# Create
curl -X POST http://localhost:8080/api/reservations \
  -H "Content-Type: application/json" \
  -d '{"guestName":"Test","hotelName":"Hotel","checkIn":"2025-12-01","checkOut":"2025-12-05"}'

# Update
curl -X PUT http://localhost:8080/api/reservations/1 \
  -H "Content-Type: application/json" \
  -d '{"guestName":"Test Updated","hotelName":"Hotel","checkIn":"2025-12-01","checkOut":"2025-12-06"}'

# Cancel
curl -X DELETE http://localhost:8080/api/reservations/1
```

### Using Postman

1. Import collection from `docs/postman/BookingMx.postman_collection.json`
2. Set environment variable: `baseUrl = http://localhost:8080`
3. Run requests in order

### Using API Testing Tools

**HTTPie:**
```bash
# List
http GET localhost:8080/api/reservations

# Create
http POST localhost:8080/api/reservations \
  guestName="John Doe" \
  hotelName="Grand Hotel" \
  checkIn="2025-12-01" \
  checkOut="2025-12-05"
```

---

## API Versioning

Current: v1 (no version prefix in URL)  
Future: `/api/v2/reservations` for breaking changes

### Versioning Strategy
- URL-based versioning for major changes
- Header-based versioning for minor changes
- Deprecation warnings for old versions

---

## Performance

### Response Times (Average)
- GET /api/reservations: < 50ms
- POST /api/reservations: < 100ms
- PUT /api/reservations/{id}: < 100ms
- DELETE /api/reservations/{id}: < 80ms

### Caching
Current: No caching  
Recommendation: Cache GET requests with 60s TTL

---

## Security

### Current Implementation
- Input validation on all fields
- Business rule enforcement
- Generic error messages

### Recommendations
- Add JWT authentication
- Implement role-based access control
- Add request signing
- Enable HTTPS only
- Implement rate limiting

---

## Future Enhancements

- [ ] Pagination for list endpoint
- [ ] Filtering and sorting
- [ ] Search functionality
- [ ] Bulk operations
- [ ] WebSocket support for real-time updates
- [ ] GraphQL endpoint
- [ ] API documentation with Swagger/OpenAPI

---

**API Version:** 1.0  
**Last Updated:** 2024-01-15  
**Maintainer:** BookingMx Development Team