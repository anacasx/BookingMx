/**
 * Main application logic for BookingMx frontend.
 * Handles UI interactions and connects frontend with backend API.
 * 
 * @module app
 * @author BookingMx Team
 * @version 1.0
 */

import { listReservations, createReservation, cancelReservation } from './api.js';
import { buildGraph, getNearbyCities, sampleData } from './graph.js';

// Initialize graph on page load
let cityGraph;

/**
 * Initializes the application when DOM is loaded.
 */
document.addEventListener('DOMContentLoaded', () => {
  console.log('BookingMx app initialized');
  
  // Build city graph
  cityGraph = buildGraph(sampleData.cities, sampleData.edges);
  
  // Setup event listeners
  setupEventListeners();
  
  // Load initial reservations
  loadReservations();
});

/**
 * Sets up all event listeners for the application.
 */
function setupEventListeners() {
  // Graph form
  const graphForm = document.getElementById('graph-form');
  if (graphForm) {
    graphForm.addEventListener('submit', handleGraphSearch);
  }
  
  // Reservation form
  const reservationForm = document.getElementById('reservation-form');
  if (reservationForm) {
    reservationForm.addEventListener('submit', handleCreateReservation);
  }
  
  // Refresh button
  const refreshBtn = document.getElementById('refresh');
  if (refreshBtn) {
    refreshBtn.addEventListener('click', loadReservations);
  }
}

/**
 * Handles graph search form submission.
 * 
 * @param {Event} event - Form submit event
 */
async function handleGraphSearch(event) {
  event.preventDefault();
  
  const destination = document.getElementById('destination').value;
  const maxDistance = parseInt(document.getElementById('maxDistance').value, 10);
  
  try {
    const nearby = getNearbyCities(cityGraph, destination, maxDistance);
    displayNearbyCities(nearby);
  } catch (error) {
    console.error('Error searching cities:', error);
    alert('Error: ' + error.message);
  }
}

/**
 * Displays nearby cities in the UI.
 * 
 * @param {Array} cities - Array of nearby city objects
 */
function displayNearbyCities(cities) {
  const list = document.getElementById('nearby-list');
  
  if (!cities || cities.length === 0) {
    list.innerHTML = '<li>No cities found within the specified distance.</li>';
    return;
  }
  
  list.innerHTML = cities.map(city => 
    `<li><strong>${city.city}</strong>: ${city.distance} km</li>`
  ).join('');
}

/**
 * Handles reservation form submission.
 * 
 * @param {Event} event - Form submit event
 */
async function handleCreateReservation(event) {
  event.preventDefault();
  
  const formData = new FormData(event.target);
  const payload = {
    guestName: formData.get('guestName'),
    hotelName: formData.get('hotelName'),
    checkIn: formData.get('checkIn'),
    checkOut: formData.get('checkOut')
  };
  
  try {
    await createReservation(payload);
    alert('Reservation created successfully!');
    event.target.reset();
    loadReservations();
  } catch (error) {
    console.error('Error creating reservation:', error);
    alert('Error creating reservation: ' + error.message);
  }
}

/**
 * Loads and displays all reservations.
 */
async function loadReservations() {
  try {
    const reservations = await listReservations();
    displayReservations(reservations);
  } catch (error) {
    console.error('Error loading reservations:', error);
    const list = document.getElementById('reservation-list');
    if (list) {
      list.innerHTML = '<li>Error loading reservations</li>';
    }
  }
}

/**
 * Displays reservations in the UI.
 * 
 * @param {Array} reservations - Array of reservation objects
 */
function displayReservations(reservations) {
  const list = document.getElementById('reservation-list');
  
  if (!reservations || reservations.length === 0) {
    list.innerHTML = '<li>No reservations found</li>';
    return;
  }
  
  list.innerHTML = reservations.map(res => `
    <li>
      <strong>${res.guestName}</strong> at ${res.hotelName}
      <br>
      ${res.checkIn} to ${res.checkOut}
      <br>
      Status: <span style="color: ${res.status === 'ACTIVE' ? 'green' : 'red'}">
        ${res.status}
      </span>
      ${res.status === 'ACTIVE' ? 
        `<button onclick="handleCancel(${res.id})">Cancel</button>` : 
        ''}
    </li>
  `).join('');
}

/**
 * Handles reservation cancellation.
 * 
 * @param {number} id - Reservation ID to cancel
 */
window.handleCancel = async function(id) {
  if (!confirm('Are you sure you want to cancel this reservation?')) {
    return;
  }
  
  try {
    await cancelReservation(id);
    alert('Reservation canceled successfully');
    loadReservations();
  } catch (error) {
    console.error('Error canceling reservation:', error);
    alert('Error canceling reservation: ' + error.message);
  }
};