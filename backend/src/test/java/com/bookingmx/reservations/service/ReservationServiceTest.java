package com.bookingmx.reservations.service;

import com.bookingmx.reservations.dto.ReservationRequest;
import com.bookingmx.reservations.exception.BadRequestException;
import com.bookingmx.reservations.exception.NotFoundException;
import com.bookingmx.reservations.model.Reservation;
import com.bookingmx.reservations.model.ReservationStatus;
import com.bookingmx.reservations.repo.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ReservationService.
 * Tests the business logic for managing hotel reservations including
 * creation, updates, cancellations, and validation.
 *
 * @author BookingMx Team
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository repository;

    @InjectMocks
    private ReservationService service;

    private ReservationRequest validRequest;
    private Reservation existingReservation;

    /**
     * Sets up test data before each test execution.
     * Creates valid reservation requests and existing reservations for testing.
     */
    @BeforeEach
    void setUp() {
        // Setup valid reservation request
        validRequest = new ReservationRequest();
        validRequest.setGuestName("John Doe");
        validRequest.setHotelName("Grand Hotel");
        validRequest.setCheckIn(LocalDate.now().plusDays(1));
        validRequest.setCheckOut(LocalDate.now().plusDays(5));

        // Setup existing reservation
        existingReservation = new Reservation(
                1L,
                "Jane Smith",
                "Beach Resort",
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(7)
        );
    }

    /**
     * Tests successful creation of a new reservation with valid data.
     * Verifies that the reservation is saved and returned correctly.
     */
    @Test
    @DisplayName("Should create reservation successfully with valid data")
    void testCreateReservation_Success() {
        // Arrange
        when(repository.save(any(Reservation.class))).thenAnswer(invocation -> {
            Reservation r = invocation.getArgument(0);
            r.setId(1L);
            return r;
        });

        // Act
        Reservation result = service.create(validRequest);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getGuestName());
        assertEquals("Grand Hotel", result.getHotelName());
        assertEquals(ReservationStatus.ACTIVE, result.getStatus());
        verify(repository, times(1)).save(any(Reservation.class));
    }

    /**
     * Tests that creating a reservation with check-out before check-in throws BadRequestException.
     */
    @Test
    @DisplayName("Should throw BadRequestException when check-out is before check-in")
    void testCreateReservation_InvalidDates() {
        // Arrange
        validRequest.setCheckOut(LocalDate.now().minusDays(1));

        // Act & Assert
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> service.create(validRequest)
        );
        assertEquals("Check-out date must be after check-in date", exception.getMessage());
        verify(repository, never()).save(any());
    }

    /**
     * Tests that creating a reservation with same check-in and check-out dates throws exception.
     */
    @Test
    @DisplayName("Should throw BadRequestException when check-in equals check-out")
    void testCreateReservation_SameDates() {
        // Arrange
        LocalDate sameDate = LocalDate.now().plusDays(1);
        validRequest.setCheckIn(sameDate);
        validRequest.setCheckOut(sameDate);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> service.create(validRequest));
        verify(repository, never()).save(any());
    }

    /**
     * Tests retrieving all reservations from the repository.
     * Verifies that all reservations are returned correctly.
     */
    @Test
    @DisplayName("Should list all reservations")
    void testListReservations() {
        // Arrange
        List<Reservation> expectedList = Arrays.asList(
                existingReservation,
                new Reservation(2L, "Bob Wilson", "City Hotel",
                        LocalDate.now().plusDays(3), LocalDate.now().plusDays(6))
        );
        when(repository.findAll()).thenReturn(expectedList);

        // Act
        List<Reservation> result = service.list();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Jane Smith", result.get(0).getGuestName());
        assertEquals("Bob Wilson", result.get(1).getGuestName());
        verify(repository, times(1)).findAll();
    }

    /**
     * Tests retrieving an empty list when no reservations exist.
     */
    @Test
    @DisplayName("Should return empty list when no reservations exist")
    void testListReservations_EmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Reservation> result = service.list();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    /**
     * Tests successful update of an existing reservation.
     * Verifies that reservation details are updated correctly.
     */
    @Test
    @DisplayName("Should update reservation successfully")
    void testUpdateReservation_Success() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(existingReservation));
        when(repository.save(any(Reservation.class))).thenAnswer(i -> i.getArgument(0));

        ReservationRequest updateRequest = new ReservationRequest();
        updateRequest.setGuestName("Jane Smith Updated");
        updateRequest.setHotelName("Beach Resort Updated");
        updateRequest.setCheckIn(LocalDate.now().plusDays(3));
        updateRequest.setCheckOut(LocalDate.now().plusDays(8));

        // Act
        Reservation result = service.update(1L, updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals("Jane Smith Updated", result.getGuestName());
        assertEquals("Beach Resort Updated", result.getHotelName());
        assertEquals(LocalDate.now().plusDays(3), result.getCheckIn());
        assertEquals(LocalDate.now().plusDays(8), result.getCheckOut());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Reservation.class));
    }

    /**
     * Tests updating a non-existent reservation throws NotFoundException.
     */
    @Test
    @DisplayName("Should throw NotFoundException when updating non-existent reservation")
    void testUpdateReservation_NotFound() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> service.update(999L, validRequest)
        );
        assertEquals("Reservation not found with id: 999", exception.getMessage());
        verify(repository, times(1)).findById(999L);
        verify(repository, never()).save(any());
    }

    /**
     * Tests that updating a canceled reservation throws BadRequestException.
     */
    @Test
    @DisplayName("Should throw BadRequestException when updating canceled reservation")
    void testUpdateReservation_AlreadyCanceled() {
        // Arrange
        existingReservation.setStatus(ReservationStatus.CANCELED);
        when(repository.findById(1L)).thenReturn(Optional.of(existingReservation));

        // Act & Assert
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> service.update(1L, validRequest)
        );
        assertEquals("Cannot update a canceled reservation", exception.getMessage());
        verify(repository, never()).save(any());
    }

    /**
     * Tests successful cancellation of an active reservation.
     * Verifies that the status is changed to CANCELED.
     */
    @Test
    @DisplayName("Should cancel reservation successfully")
    void testCancelReservation_Success() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(existingReservation));
        when(repository.save(any(Reservation.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Reservation result = service.cancel(1L);

        // Assert
        assertNotNull(result);
        assertEquals(ReservationStatus.CANCELED, result.getStatus());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(existingReservation);
    }

    /**
     * Tests canceling a non-existent reservation throws NotFoundException.
     */
    @Test
    @DisplayName("Should throw NotFoundException when canceling non-existent reservation")
    void testCancelReservation_NotFound() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> service.cancel(999L)
        );
        assertEquals("Reservation not found with id: 999", exception.getMessage());
        verify(repository, times(1)).findById(999L);
        verify(repository, never()).save(any());
    }

    /**
     * Tests that canceling an already canceled reservation throws BadRequestException.
     */
    @Test
    @DisplayName("Should throw BadRequestException when canceling already canceled reservation")
    void testCancelReservation_AlreadyCanceled() {
        // Arrange
        existingReservation.setStatus(ReservationStatus.CANCELED);
        when(repository.findById(1L)).thenReturn(Optional.of(existingReservation));

        // Act & Assert
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> service.cancel(1L)
        );
        assertEquals("Reservation is already canceled", exception.getMessage());
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any());
    }
}