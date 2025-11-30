package com.bookingmx.reservations.controller;

import com.bookingmx.reservations.dto.ReservationRequest;
import com.bookingmx.reservations.model.Reservation;
import com.bookingmx.reservations.model.ReservationStatus;
import com.bookingmx.reservations.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Unit tests for ReservationController.
 * Tests REST API endpoints for managing reservations.
 * Uses MockMvc to simulate HTTP requests without starting the server.
 *
 * @author BookingMx Team
 * @version 1.0
 */
@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Reservation reservation;
    private ReservationRequest request;

    /**
     * Sets up test data before each test execution.
     */
    @BeforeEach
    void setUp() {
        reservation = new Reservation(
                1L,
                "John Doe",
                "Grand Hotel",
                LocalDate.of(2025, 12, 1),
                LocalDate.of(2025, 12, 5)
        );

        request = new ReservationRequest();
        request.setGuestName("John Doe");
        request.setHotelName("Grand Hotel");
        request.setCheckIn(LocalDate.of(2025, 12, 1));
        request.setCheckOut(LocalDate.of(2025, 12, 5));
    }

    /**
     * Tests that GET /api/reservations returns list of all reservations.
     */
    @Test
    @DisplayName("GET /api/reservations - Should return list of reservations")
    void testListReservations() throws Exception {
        // Arrange
        List<Reservation> reservations = Arrays.asList(
                reservation,
                new Reservation(2L, "Jane Smith", "Beach Resort",
                        LocalDate.of(2025, 12, 10), LocalDate.of(2025, 12, 15))
        );
        when(service.list()).thenReturn(reservations);

        // Act & Assert
        mockMvc.perform(get("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].guestName", is("John Doe")))
                .andExpect(jsonPath("$[0].hotelName", is("Grand Hotel")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].guestName", is("Jane Smith")));
    }

    /**
     * Tests that GET /api/reservations returns empty array when no reservations exist.
     */
    @Test
    @DisplayName("GET /api/reservations - Should return empty array when no reservations")
    void testListReservations_Empty() throws Exception {
        // Arrange
        when(service.list()).thenReturn(Arrays.asList());

        // Act & Assert
        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    /**
     * Tests that POST /api/reservations creates a new reservation successfully.
     */
    @Test
    @DisplayName("POST /api/reservations - Should create reservation successfully")
    void testCreateReservation_Success() throws Exception {
        // Arrange
        when(service.create(any(ReservationRequest.class))).thenReturn(reservation);

        // Act & Assert
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.guestName", is("John Doe")))
                .andExpect(jsonPath("$.hotelName", is("Grand Hotel")))
                .andExpect(jsonPath("$.status", is("ACTIVE")));
    }

    /**
     * Tests that POST /api/reservations returns 400 when guest name is missing.
     */
    @Test
    @DisplayName("POST /api/reservations - Should return 400 when guest name is blank")
    void testCreateReservation_BlankGuestName() throws Exception {
        // Arrange
        request.setGuestName("");

        // Act & Assert
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that POST /api/reservations returns 400 when dates are in the past.
     */
    @Test
    @DisplayName("POST /api/reservations - Should return 400 when dates are in past")
    void testCreateReservation_PastDates() throws Exception {
        // Arrange
        request.setCheckIn(LocalDate.now().minusDays(1));
        request.setCheckOut(LocalDate.now().plusDays(1));

        // Act & Assert
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Tests that PUT /api/reservations/{id} updates a reservation successfully.
     */
    @Test
    @DisplayName("PUT /api/reservations/{id} - Should update reservation successfully")
    void testUpdateReservation_Success() throws Exception {
        // Arrange
        reservation.setGuestName("John Doe Updated");
        when(service.update(eq(1L), any(ReservationRequest.class))).thenReturn(reservation);

        request.setGuestName("John Doe Updated");

        // Act & Assert
        mockMvc.perform(put("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.guestName", is("John Doe Updated")));
    }

    /**
     * Tests that DELETE /api/reservations/{id} cancels a reservation successfully.
     */
    @Test
    @DisplayName("DELETE /api/reservations/{id} - Should cancel reservation successfully")
    void testCancelReservation_Success() throws Exception {
        // Arrange
        reservation.setStatus(ReservationStatus.CANCELED);
        when(service.cancel(1L)).thenReturn(reservation);

        // Act & Assert
        mockMvc.perform(delete("/api/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("CANCELED")));
    }

    /**
     * Tests CORS configuration allows requests from localhost:5173.
     */
    @Test
    @DisplayName("OPTIONS /api/reservations - Should allow CORS from localhost:5173")
    void testCorsConfiguration() throws Exception {
        mockMvc.perform(options("/api/reservations")
                        .header("Origin", "http://localhost:5173")
                        .header("Access-Control-Request-Method", "POST"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Access-Control-Allow-Origin"));
    }
}