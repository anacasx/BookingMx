package com.bookingmx.reservations.service;

import com.bookingmx.reservations.dto.ReservationRequest;
import com.bookingmx.reservations.exception.BadRequestException;
import com.bookingmx.reservations.exception.NotFoundException;
import com.bookingmx.reservations.model.Reservation;
import com.bookingmx.reservations.model.ReservationStatus;
import com.bookingmx.reservations.repo.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing hotel reservations.
 * 
 * <p>This service provides business logic for the complete lifecycle of hotel
 * reservations, including creation, modification, cancellation, and retrieval.
 * It implements validation rules and enforces business constraints.</p>
 * 
 * <p><b>Business Rules:</b></p>
 * <ul>
 *   <li>Check-out date must be after check-in date</li>
 *   <li>Only ACTIVE reservations can be updated</li>
 *   <li>Only ACTIVE reservations can be canceled</li>
 *   <li>Canceled reservations cannot be reactivated</li>
 * </ul>
 * 
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * ReservationService service = new ReservationService(repository);
 * 
 * ReservationRequest request = new ReservationRequest();
 * request.setGuestName("John Doe");
 * request.setHotelName("Grand Hotel");
 * request.setCheckIn(LocalDate.now().plusDays(1));
 * request.setCheckOut(LocalDate.now().plusDays(5));
 * 
 * Reservation reservation = service.create(request);
 * }</pre>
 * 
 * @author BookingMx Development Team
 * @version 3.0.0
 * @since 2024-01-01
 * @see Reservation
 * @see ReservationRepository
 * @see ReservationRequest
 */

@Service
public class ReservationService {

    private final ReservationRepository repository;

    /**
     * Constructs a ReservationService with the specified repository.
     *
     * @param repository the reservation repository to use for data persistence
     */
    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all reservations from the system.
     *
     * @return a list of all reservations, may be empty if none exist
     */
    public List<Reservation> list() {
        return repository.findAll();
    }

    /**
     * Creates a new reservation with the provided details.
     * Validates that the check-out date is after the check-in date.
     *
     * @param req the reservation request containing guest and hotel information
     * @return the created reservation with generated ID and ACTIVE status
     * @throws BadRequestException if check-out date is not after check-in date
     */
    public Reservation create(ReservationRequest req) {
        validateDates(req);

        Reservation reservation = new Reservation();
        reservation.setGuestName(req.getGuestName());
        reservation.setHotelName(req.getHotelName());
        reservation.setCheckIn(req.getCheckIn());
        reservation.setCheckOut(req.getCheckOut());
        reservation.setStatus(ReservationStatus.ACTIVE);

        return repository.save(reservation);
    }

    /**
     * Updates an existing reservation with new details.
     * Only active reservations can be updated.
     *
     * @param id the ID of the reservation to update
     * @param req the updated reservation information
     * @return the updated reservation
     * @throws NotFoundException if no reservation exists with the given ID
     * @throws BadRequestException if the reservation is canceled or dates are invalid
     */
    public Reservation update(Long id, ReservationRequest req) {
        validateDates(req);

        Reservation existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id: " + id));

        if (existing.getStatus() == ReservationStatus.CANCELED) {
            throw new BadRequestException("Cannot update a canceled reservation");
        }

        existing.setGuestName(req.getGuestName());
        existing.setHotelName(req.getHotelName());
        existing.setCheckIn(req.getCheckIn());
        existing.setCheckOut(req.getCheckOut());

        return repository.save(existing);
    }

    /**
     * Cancels an existing reservation by setting its status to CANCELED.
     * Only active reservations can be canceled.
     *
     * @param id the ID of the reservation to cancel
     * @return the canceled reservation
     * @throws NotFoundException if no reservation exists with the given ID
     * @throws BadRequestException if the reservation is already canceled
     */
    public Reservation cancel(Long id) {
        Reservation existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id: " + id));

        if (existing.getStatus() == ReservationStatus.CANCELED) {
            throw new BadRequestException("Reservation is already canceled");
        }

        existing.setStatus(ReservationStatus.CANCELED);
        return repository.save(existing);
    }

    /**
     * Validates that the check-out date is after the check-in date.
     *
     * @param req the reservation request to validate
     * @throws BadRequestException if check-out is not after check-in
     */
    private void validateDates(ReservationRequest req) {
        if (!req.getCheckOut().isAfter(req.getCheckIn())) {
            throw new BadRequestException("Check-out date must be after check-in date");
        }
    }
}