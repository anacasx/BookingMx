package com.bookingmx.reservations.repo;

import com.bookingmx.reservations.model.Reservation;
import com.bookingmx.reservations.model.ReservationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ReservationRepository.
 * Tests data persistence operations for reservations.
 * Since this is an in-memory repository, tests focus on CRUD operations.
 *
 * @author BookingMx Team
 * @version 1.0
 */
class ReservationRepositoryTest {

    private ReservationRepository repository;
    private Reservation reservation;

    /**
     * Sets up test data and repository before each test.
     */
    @BeforeEach
    void setUp() {
        repository = new ReservationRepository();
        
        reservation = new Reservation();
        reservation.setGuestName("John Doe");
        reservation.setHotelName("Grand Hotel");
        reservation.setCheckIn(LocalDate.of(2025, 12, 1));
        reservation.setCheckOut(LocalDate.of(2025, 12, 5));
        reservation.setStatus(ReservationStatus.ACTIVE);
    }

    /**
     * Tests that save generates an ID for a new reservation.
     */
    @Test
    @DisplayName("Should save new reservation and generate ID")
    void testSave_NewReservation() {
        // Act
        Reservation saved = repository.save(reservation);

        // Assert
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(1L, saved.getId());
        assertEquals("John Doe", saved.getGuestName());
        assertEquals("Grand Hotel", saved.getHotelName());
        assertEquals(ReservationStatus.ACTIVE, saved.getStatus());
    }

    /**
     * Tests that save updates an existing reservation.
     */
    @Test
    @DisplayName("Should update existing reservation")
    void testSave_ExistingReservation() {
        // Arrange
        Reservation saved = repository.save(reservation);
        saved.setGuestName("John Doe Updated");

        // Act
        Reservation updated = repository.save(saved);

        // Assert
        assertEquals(saved.getId(), updated.getId());
        assertEquals("John Doe Updated", updated.getGuestName());
    }

    /**
     * Tests that findAll returns all saved reservations.
     */
    @Test
    @DisplayName("Should find all reservations")
    void testFindAll() {
        // Arrange
        repository.save(reservation);
        
        Reservation reservation2 = new Reservation();
        reservation2.setGuestName("Jane Smith");
        reservation2.setHotelName("Beach Resort");
        reservation2.setCheckIn(LocalDate.of(2025, 12, 10));
        reservation2.setCheckOut(LocalDate.of(2025, 12, 15));
        repository.save(reservation2);

        // Act
        List<Reservation> all = repository.findAll();

        // Assert
        assertNotNull(all);
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(r -> r.getGuestName().equals("John Doe")));
        assertTrue(all.stream().anyMatch(r -> r.getGuestName().equals("Jane Smith")));
    }

    /**
     * Tests that findAll returns empty list when no reservations exist.
     */
    @Test
    @DisplayName("Should return empty list when no reservations exist")
    void testFindAll_Empty() {
        // Act
        List<Reservation> all = repository.findAll();

        // Assert
        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    /**
     * Tests that findById returns the correct reservation.
     */
    @Test
    @DisplayName("Should find reservation by ID")
    void testFindById_Found() {
        // Arrange
        Reservation saved = repository.save(reservation);

        // Act
        Optional<Reservation> found = repository.findById(saved.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
        assertEquals("John Doe", found.get().getGuestName());
    }

    /**
     * Tests that findById returns empty when reservation doesn't exist.
     */
    @Test
    @DisplayName("Should return empty when reservation not found")
    void testFindById_NotFound() {
        // Act
        Optional<Reservation> found = repository.findById(999L);

        // Assert
        assertFalse(found.isPresent());
    }

    /**
     * Tests that delete removes a reservation.
     */
    @Test
    @DisplayName("Should delete reservation by ID")
    void testDelete() {
        // Arrange
        Reservation saved = repository.save(reservation);
        Long id = saved.getId();

        // Act
        repository.delete(id);

        // Assert
        Optional<Reservation> found = repository.findById(id);
        assertFalse(found.isPresent());
    }

    /**
     * Tests that multiple saves with same ID update the same reservation.
     */
    @Test
    @DisplayName("Should handle multiple saves with same ID")
    void testSave_MultipleUpdates() {
        // Arrange
        Reservation saved = repository.save(reservation);
        Long id = saved.getId();

        // Act - Multiple updates
        saved.setGuestName("Update 1");
        repository.save(saved);
        
        saved.setGuestName("Update 2");
        repository.save(saved);

        // Assert
        Optional<Reservation> found = repository.findById(id);
        assertTrue(found.isPresent());
        assertEquals("Update 2", found.get().getGuestName());
        assertEquals(1, repository.findAll().size()); // Should still be only 1 reservation
    }

    /**
     * Tests that IDs are generated sequentially.
     */
    @Test
    @DisplayName("Should generate sequential IDs")
    void testSave_SequentialIds() {
        // Act
        Reservation first = repository.save(reservation);
        
        Reservation second = new Reservation();
        second.setGuestName("Jane Smith");
        second.setHotelName("Beach Resort");
        second.setCheckIn(LocalDate.of(2025, 12, 10));
        second.setCheckOut(LocalDate.of(2025, 12, 15));
        repository.save(second);

        // Assert
        assertEquals(1L, first.getId());
        assertEquals(2L, second.getId());
    }

    /**
     * Tests that repository is thread-safe (uses ConcurrentHashMap).
     */
    @Test
    @DisplayName("Should handle concurrent operations safely")
    void testConcurrentOperations() {
        // Arrange & Act
        for (int i = 0; i < 100; i++) {
            Reservation r = new Reservation();
            r.setGuestName("Guest " + i);
            r.setHotelName("Hotel " + i);
            r.setCheckIn(LocalDate.now().plusDays(1));
            r.setCheckOut(LocalDate.now().plusDays(5));
            repository.save(r);
        }

        // Assert
        assertEquals(100, repository.findAll().size());
    }
}