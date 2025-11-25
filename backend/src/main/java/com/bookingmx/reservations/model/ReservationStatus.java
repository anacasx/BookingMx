package com.bookingmx.reservations.model;

/**
 * Enumeration representing the possible states of a reservation.
 * A reservation can be either ACTIVE or CANCELED.
 * 
 * @author BookingMx Team
 * @version 1.0
 * @since 2024-01-01
 */
public enum ReservationStatus {
    /**
     * The reservation is active and valid.
     */
    ACTIVE,
    
    /**
     * The reservation has been canceled and is no longer valid.
     */
    CANCELED
}