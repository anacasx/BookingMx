package com.bookingmx.reservations.controller;

import com.bookingmx.reservations.dto.ReservationRequest;
import com.bookingmx.reservations.dto.ReservationResponse;
import com.bookingmx.reservations.model.Reservation;
import com.bookingmx.reservations.service.ReservationService;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing hotel reservations.
 * Provides endpoints for CRUD operations on reservations.
 * 
 * <p>This controller handles HTTP requests and delegates business logic
 * to the {@link ReservationService}. All endpoints return JSON responses.</p>
 * 
 * <p>Base URL: {@code /api/reservations}</p>
 * 
 * <p>CORS is enabled for localhost development on ports 5173 and 127.0.0.1:5173.</p>
 *
 * @author BookingMx Team
 * @version 1.0
 * @since 2024-01-01
 * @see ReservationService
 * @see ReservationRequest
 * @see ReservationResponse
 */
@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173", "*"})
@RequestMapping(value = "/api/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {

    /**
     * Service layer for reservation business logic.
     */
    private final ReservationService service;

    /**
     * Constructs a ReservationController with the required service dependency.
     * 
     * @param service the reservation service to handle business operations
     */
    public ReservationController(ReservationService service) {
        this.service = service;
    }

    /**
     * Retrieves all reservations in the system.
     * 
     * <p>This endpoint returns a list of all reservations regardless of status.
     * The list will be empty if no reservations exist.</p>
     * 
     * <p><strong>Example Request:</strong></p>
     * <pre>
     * GET /api/reservations
     * </pre>
     * 
     * <p><strong>Example Response:</strong></p>
     * <pre>
     * [
     *   {
     *     "id": 1,
     *     "guestName": "John Doe",
     *     "hotelName": "Grand Hotel",
     *     "checkIn": "2024-12-01",
     *     "checkOut": "2024-12-05",
     *     "status": "ACTIVE"
     *   }
     * ]
     * </pre>
     *
     * @return a list of all reservations as {@link ReservationResponse} objects
     */
    @GetMapping
    public List<ReservationResponse> list() {
        return service.list().stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Creates a new reservation with the provided details.
     * 
     * <p>Validates the request data using Bean Validation annotations.
     * The check-in and check-out dates must be in the future, and
     * check-out must be after check-in.</p>
     * 
     * <p><strong>Example Request:</strong></p>
     * <pre>
     * POST /api/reservations
     * Content-Type: application/json
     * 
     * {
     *   "guestName": "John Doe",
     *   "hotelName": "Grand Hotel",
     *   "checkIn": "2024-12-01",
     *   "checkOut": "2024-12-05"
     * }
     * </pre>
     * 
     * <p><strong>Example Response:</strong></p>
     * <pre>
     * {
     *   "id": 1,
     *   "guestName": "John Doe",
     *   "hotelName": "Grand Hotel",
     *   "checkIn": "2024-12-01",
     *   "checkOut": "2024-12-05",
     *   "status": "ACTIVE"
     * }
     * </pre>
     *
     * @param req the reservation request containing guest and hotel details
     * @return the created reservation with generated ID and ACTIVE status
     * @throws com.bookingmx.reservations.exception.BadRequestException 
     *         if validation fails or business rules are violated
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReservationResponse create(@Valid @RequestBody ReservationRequest req) {
        return toResponse(service.create(req));
    }

    /**
     * Updates an existing reservation with new details.
     * 
     * <p>Only active reservations can be updated. Canceled reservations
     * cannot be modified. All fields in the request will replace the
     * existing values.</p>
     * 
     * <p><strong>Example Request:</strong></p>
     * <pre>
     * PUT /api/reservations/1
     * Content-Type: application/json
     * 
     * {
     *   "guestName": "John Doe Updated",
     *   "hotelName": "Grand Hotel",
     *   "checkIn": "2024-12-01",
     *   "checkOut": "2024-12-06"
     * }
     * </pre>
     * 
     * <p><strong>Example Response:</strong></p>
     * <pre>
     * {
     *   "id": 1,
     *   "guestName": "John Doe Updated",
     *   "hotelName": "Grand Hotel",
     *   "checkIn": "2024-12-01",
     *   "checkOut": "2024-12-06",
     *   "status": "ACTIVE"
     * }
     * </pre>
     *
     * @param id the ID of the reservation to update
     * @param req the updated reservation details
     * @return the updated reservation
     * @throws com.bookingmx.reservations.exception.NotFoundException 
     *         if no reservation exists with the given ID
     * @throws com.bookingmx.reservations.exception.BadRequestException 
     *         if the reservation is canceled or validation fails
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReservationResponse update(@PathVariable("id") Long id, @Valid @RequestBody ReservationRequest req) {
        return toResponse(service.update(id, req));
    }

    /**
     * Cancels an existing reservation.
     * 
     * <p>Sets the reservation status to CANCELED. This operation is
     * idempotent but will throw an exception if the reservation is
     * already canceled.</p>
     * 
     * <p>The reservation data is not deleted; only the status changes.
     * This allows for audit trail and historical data preservation.</p>
     * 
     * <p><strong>Example Request:</strong></p>
     * <pre>
     * DELETE /api/reservations/1
     * </pre>
     * 
     * <p><strong>Example Response:</strong></p>
     * <pre>
     * {
     *   "id": 1,
     *   "guestName": "John Doe",
     *   "hotelName": "Grand Hotel",
     *   "checkIn": "2024-12-01",
     *   "checkOut": "2024-12-05",
     *   "status": "CANCELED"
     * }
     * </pre>
     *
     * @param id the ID of the reservation to cancel
     * @return the canceled reservation with CANCELED status
     * @throws com.bookingmx.reservations.exception.NotFoundException 
     *         if no reservation exists with the given ID
     * @throws com.bookingmx.reservations.exception.BadRequestException 
     *         if the reservation is already canceled
     */
    @DeleteMapping("/{id}")
    public ReservationResponse cancel(@PathVariable("id") Long id) {
        return toResponse(service.cancel(id));
    }

    /**
     * Converts a Reservation entity to a ReservationResponse DTO.
     * 
     * <p>This private helper method transforms the domain model into
     * the API response format, hiding internal implementation details.</p>
     *
     * @param r the reservation entity to convert
     * @return a ReservationResponse DTO with the same data
     */
    private ReservationResponse toResponse(Reservation r) {
        return new ReservationResponse(
                r.getId(), 
                r.getGuestName(), 
                r.getHotelName(), 
                r.getCheckIn(), 
                r.getCheckOut(), 
                r.getStatus()
        );
    }
}