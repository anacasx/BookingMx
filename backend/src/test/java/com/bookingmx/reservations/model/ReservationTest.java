package com.bookingmx.reservations.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Reservation model class.
 * Tests the data model, getters, setters, and business methods.
 *
 * @author BookingMx Team
 * @version 1.0
 */
class ReservationTest {

    private Reservation reservation;
    private LocalDate checkIn;
    private LocalDate checkOut;

    /**
     * Sets up test data before each test execution.
     */
    @BeforeEach
    void setUp() {
        checkIn = LocalDate.of(2025, 12, 1);
        checkOut = LocalDate.of(2025, 12, 5);
        
        reservation = new Reservation(
                1L,
                "John Doe",
                "Grand Hotel",
                checkIn,
                checkOut
        );
    }

    /**
     * Tests that constructor sets all fields correctly.
     */
    @Test
    @DisplayName("Should create reservation with all fields")
    void testConstructor_WithAllFields() {
        // Assert
        assertEquals(1L, reservation.getId());
        assertEquals("John Doe", reservation.getGuestName());
        assertEquals("Grand Hotel", reservation.getHotelName());
        assertEquals(checkIn, reservation.getCheckIn());
        assertEquals(checkOut, reservation.getCheckOut());
        assertEquals(ReservationStatus.ACTIVE, reservation.getStatus());
    }

    /**
     * Tests that default constructor creates reservation with null fields.
     */
    @Test
    @DisplayName("Should create reservation with default constructor")
    void testDefaultConstructor() {
        // Act
        Reservation emptyReservation = new Reservation();

        // Assert
        assertNotNull(emptyReservation);
        assertNull(emptyReservation.getId());
        assertNull(emptyReservation.getGuestName());
        assertNull(emptyReservation.getHotelName());
        assertNull(emptyReservation.getCheckIn());
        assertNull(emptyReservation.getCheckOut());
        assertEquals(ReservationStatus.ACTIVE, emptyReservation.getStatus());
    }

    /**
     * Tests all getter and setter methods.
     */
    @Test
    @DisplayName("Should set and get all properties correctly")
    void testGettersAndSetters() {
        // Arrange
        Reservation r = new Reservation();

        // Act
        r.setId(2L);
        r.setGuestName("Jane Smith");
        r.setHotelName("Beach Resort");
        r.setCheckIn(checkIn);
        r.setCheckOut(checkOut);
        r.setStatus(ReservationStatus.CANCELED);

        // Assert
        assertEquals(2L, r.getId());
        assertEquals("Jane Smith", r.getGuestName());
        assertEquals("Beach Resort", r.getHotelName());
        assertEquals(checkIn, r.getCheckIn());
        assertEquals(checkOut, r.getCheckOut());
        assertEquals(ReservationStatus.CANCELED, r.getStatus());
    }

    /**
     * Tests the isActive method returns true for ACTIVE status.
     */
    @Test
    @DisplayName("Should return true when reservation is active")
    void testIsActive_WhenActive() {
        // Arrange
        reservation.setStatus(ReservationStatus.ACTIVE);

        // Act & Assert
        assertTrue(reservation.isActive());
    }

    /**
     * Tests the isActive method returns false for CANCELED status.
     */
    @Test
    @DisplayName("Should return false when reservation is canceled")
    void testIsActive_WhenCanceled() {
        // Arrange
        reservation.setStatus(ReservationStatus.CANCELED);

        // Act & Assert
        assertFalse(reservation.isActive());
    }

    /**
     * Tests equals method for same ID.
     */
    @Test
    @DisplayName("Should be equal when IDs are the same")
    void testEquals_SameId() {
        // Arrange
        Reservation other = new Reservation(
                1L,
                "Different Name",
                "Different Hotel",
                checkIn,
                checkOut
        );

        // Act & Assert
        assertEquals(reservation, other);
    }

    /**
     * Tests equals method for different IDs.
     */
    @Test
    @DisplayName("Should not be equal when IDs are different")
    void testEquals_DifferentId() {
        // Arrange
        Reservation other = new Reservation(
                2L,
                "John Doe",
                "Grand Hotel",
                checkIn,
                checkOut
        );

        // Act & Assert
        assertNotEquals(reservation, other);
    }

    /**
     * Tests equals method with null.
     */
    @Test
    @DisplayName("Should not be equal to null")
    void testEquals_Null() {
        // Act & Assert
        assertNotEquals(null, reservation);
    }

    /**
     * Tests equals method with same instance.
     */
    @Test
    @DisplayName("Should be equal to itself")
    void testEquals_SameInstance() {
        // Act & Assert
        assertEquals(reservation, reservation);
    }

    /**
     * Tests equals method with different class.
     */
    @Test
    @DisplayName("Should not be equal to different class")
    void testEquals_DifferentClass() {
        // Act & Assert
        assertNotEquals("Not a reservation", reservation);
    }

    /**
     * Tests hashCode method consistency.
     */
    @Test
    @DisplayName("Should have consistent hashCode")
    void testHashCode_Consistency() {
        // Arrange
        int firstHashCode = reservation.hashCode();

        // Act
        int secondHashCode = reservation.hashCode();

        // Assert
        assertEquals(firstHashCode, secondHashCode);
    }

    /**
     * Tests hashCode method for equal objects.
     */
    @Test
    @DisplayName("Should have same hashCode when objects are equal")
    void testHashCode_EqualObjects() {
        // Arrange
        Reservation other = new Reservation(
                1L,
                "Different Name",
                "Different Hotel",
                checkIn,
                checkOut
        );

        // Act & Assert
        assertEquals(reservation.hashCode(), other.hashCode());
    }

    /**
     * Tests that reservation can change from ACTIVE to CANCELED.
     */
    @Test
    @DisplayName("Should allow status change from ACTIVE to CANCELED")
    void testStatusChange() {
        // Arrange
        assertEquals(ReservationStatus.ACTIVE, reservation.getStatus());
        assertTrue(reservation.isActive());

        // Act
        reservation.setStatus(ReservationStatus.CANCELED);

        // Assert
        assertEquals(ReservationStatus.CANCELED, reservation.getStatus());
        assertFalse(reservation.isActive());
    }

    /**
     * Tests that reservation handles null ID correctly.
     */
    @Test
    @DisplayName("Should handle null ID correctly")
    void testNullId() {
        // Arrange
        Reservation r1 = new Reservation();
        r1.setId(null);
        
        Reservation r2 = new Reservation();
        r2.setId(null);

        // Act & Assert
        assertNull(r1.getId());
        assertEquals(r1, r2); // Both have null IDs, so they're equal
    }

    /**
     * Tests that dates can be set to today.
     */
    @Test
    @DisplayName("Should allow dates to be set to today")
    void testDates_Today() {
        // Arrange
        LocalDate today = LocalDate.now();

        // Act
        reservation.setCheckIn(today);
        reservation.setCheckOut(today.plusDays(1));

        // Assert
        assertEquals(today, reservation.getCheckIn());
        assertEquals(today.plusDays(1), reservation.getCheckOut());
    }
}