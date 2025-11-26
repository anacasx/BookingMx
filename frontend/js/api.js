/**
 * API client for the backend reservations module.
 * 
 * Provides functions to interact with the Spring Boot backend API.
 * All functions use the Fetch API and return Promises.
 * Kept simple and modular for easy mocking in Jest tests.
 * 
 * @module api
 * @author BookingMx Team
 * @version 1.0
 * @since 2024-01-01
 */

/**
 * Base URL for the reservations API.
 * Points to the Spring Boot backend running on localhost:8080.
 * 
 * @constant {string}
 */
const BASE_URL = "http://localhost:8080/api/reservations";

/**
 * Fetches all reservations from the backend.
 * 
 * Makes a GET request to retrieve all reservations regardless of status.
 * The response includes both active and canceled reservations.
 * 
 * @async
 * @returns {Promise<Array<Object>>} Array of reservation objects
 * @throws {Error} If the HTTP request fails or returns a non-OK status
 * 
 * @example
 * try {
 *   const reservations = await listReservations();
 *   console.log(`Found ${reservations.length} reservations`);
 *   reservations.forEach(r => console.log(r.guestName));
 * } catch (error) {
 *   console.error('Failed to fetch reservations:', error.message);
 * }
 * 
 * @example
 * // Expected response format:
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
 */
export async function listReservations() {
  const res = await fetch(BASE_URL);
  
  if (!res.ok) {
    throw new Error("Failed to fetch reservations");
  }
  
  return res.json();
}

/**
 * Creates a new reservation in the backend.
 * 
 * Makes a POST request with the reservation details.
 * The backend validates dates and returns the created reservation with an ID.
 * 
 * @async
 * @param {Object} payload - The reservation details
 * @param {string} payload.guestName - Guest's full name
 * @param {string} payload.hotelName - Hotel name
 * @param {string} payload.checkIn - Check-in date (ISO format: YYYY-MM-DD)
 * @param {string} payload.checkOut - Check-out date (ISO format: YYYY-MM-DD)
 * @returns {Promise<Object>} The created reservation with generated ID and ACTIVE status
 * @throws {Error} If validation fails or the HTTP request fails
 * 
 * @example
 * try {
 *   const newReservation = await createReservation({
 *     guestName: 'Jane Smith',
 *     hotelName: 'Beach Resort',
 *     checkIn: '2024-12-10',
 *     checkOut: '2024-12-15'
 *   });
 *   console.log('Reservation created with ID:', newReservation.id);
 * } catch (error) {
 *   console.error('Creation failed:', error.message);
 * }
 * 
 * @example
 * // Validation error example:
 * try {
 *   await createReservation({
 *     guestName: 'John',
 *     hotelName: 'Hotel',
 *     checkIn: '2024-12-10',
 *     checkOut: '2024-12-08'  // Before check-in!
 *   });
 * } catch (error) {
 *   console.error(error.message);
 *   // "Check-out date must be after check-in date"
 * }
 */
export async function createReservation(payload) {
  const res = await fetch(BASE_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
  });
  
  if (!res.ok) {
    // Try to extract error message from response
    const errorData = await res.json();
    const message = errorData.message || "Create failed";
    throw new Error(message);
  }
  
  return res.json();
}

/**
 * Updates an existing reservation with new details.
 * 
 * Makes a PUT request to update a reservation identified by its ID.
 * Only active reservations can be updated; canceled reservations cannot.
 * All fields in the payload will replace the existing values.
 * 
 * @async
 * @param {number|string} id - The reservation ID to update
 * @param {Object} payload - The updated reservation details
 * @param {string} payload.guestName - Updated guest name
 * @param {string} payload.hotelName - Updated hotel name
 * @param {string} payload.checkIn - Updated check-in date (ISO format)
 * @param {string} payload.checkOut - Updated check-out date (ISO format)
 * @returns {Promise<Object>} The updated reservation
 * @throws {Error} If the reservation doesn't exist, is canceled, or validation fails
 * 
 * @example
 * try {
 *   const updated = await updateReservation(1, {
 *     guestName: 'John Doe (Updated)',
 *     hotelName: 'Grand Hotel',
 *     checkIn: '2024-12-01',
 *     checkOut: '2024-12-06'  // Extended stay
 *   });
 *   console.log('Reservation updated:', updated);
 * } catch (error) {
 *   console.error('Update failed:', error.message);
 * }
 * 
 * @example
 * // Error handling for non-existent reservation:
 * try {
 *   await updateReservation(999, payload);
 * } catch (error) {
 *   console.error(error.message);
 *   // "Reservation not found with id: 999"
 * }
 * 
 * @example
 * // Error handling for canceled reservation:
 * try {
 *   await updateReservation(5, payload);
 * } catch (error) {
 *   console.error(error.message);
 *   // "Cannot update a canceled reservation"
 * }
 */
export async function updateReservation(id, payload) {
  const res = await fetch(`${BASE_URL}/${encodeURIComponent(id)}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
  });
  
  if (!res.ok) {
    // Try to extract error message from response
    const errorData = await res.json();
    const message = errorData.message || "Update failed";
    throw new Error(message);
  }
  
  return res.json();
}

/**
 * Cancels an existing reservation.
 * 
 * Makes a DELETE request to cancel a reservation identified by its ID.
 * This changes the reservation status to CANCELED but doesn't delete the data.
 * Already canceled reservations cannot be canceled again.
 * 
 * @async
 * @param {number|string} id - The reservation ID to cancel
 * @returns {Promise<Object>} The canceled reservation with CANCELED status
 * @throws {Error} If the reservation doesn't exist or is already canceled
 * 
 * @example
 * try {
 *   const canceled = await cancelReservation(1);
 *   console.log('Reservation canceled:', canceled);
 *   console.log('Status:', canceled.status);  // "CANCELED"
 * } catch (error) {
 *   console.error('Cancellation failed:', error.message);
 * }
 * 
 * @example
 * // Error handling for non-existent reservation:
 * try {
 *   await cancelReservation(999);
 * } catch (error) {
 *   console.error(error.message);
 *   // "Reservation not found with id: 999"
 * }
 * 
 * @example
 * // Error handling for already canceled reservation:
 * try {
 *   await cancelReservation(1);  // Already canceled
 * } catch (error) {
 *   console.error(error.message);
 *   // "Reservation is already canceled"
 * }
 */
export async function cancelReservation(id) {
  const res = await fetch(`${BASE_URL}/${encodeURIComponent(id)}`, {
    method: "DELETE"
  });
  
  if (!res.ok) {
    // Try to extract error message from response
    const errorData = await res.json();
    const message = errorData.message || "Cancel failed";
    throw new Error(message);
  }
  
  return res.json();
}