/**
 * Unit tests for the API client module.
 * Tests HTTP requests to the backend reservations API.
 * Uses mocking to avoid actual HTTP calls during testing.
 * 
 * @module api.test
 * @author BookingMx Team
 * @version 1.0
 */

import {
  listReservations,
  createReservation,
  updateReservation,
  cancelReservation
} from '../js/api.js';

import { jest } from '@jest/globals';

// Single global.fetch mock (use jest.fn())
global.fetch = jest.fn();

// Mock global fetch before tests
// (already assigned above)

/* ---------------------------
   Tests
----------------------------*/
describe('API Client Module', () => {
  beforeEach(() => {
    // Clear all mocks before each test
    jest.clearAllMocks();
  });

  /**
   * Tests for listReservations function
   */
  describe('listReservations', () => {
    test('should fetch and return list of reservations successfully', async () => {
      // Arrange
      const mockReservations = [
        {
          id: 1,
          guestName: 'John Doe',
          hotelName: 'Grand Hotel',
          checkIn: '2025-12-01',
          checkOut: '2025-12-05',
          status: 'ACTIVE'
        },
        {
          id: 2,
          guestName: 'Jane Smith',
          hotelName: 'Beach Resort',
          checkIn: '2025-12-10',
          checkOut: '2025-12-15',
          status: 'ACTIVE'
        }
      ];

      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => mockReservations
      });

      // Act
      const result = await listReservations();

      // Assert
      expect(result).toEqual(mockReservations);
      expect(result).toHaveLength(2);
      expect(global.fetch).toHaveBeenCalledTimes(1);
      expect(global.fetch).toHaveBeenCalledWith('http://localhost:8080/api/reservations');
    });

    test('should return empty array when no reservations exist', async () => {
      // Arrange
      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => []
      });

      // Act
      const result = await listReservations();

      // Assert
      expect(result).toEqual([]);
      expect(result).toHaveLength(0);
    });

    test('should throw error when fetch fails', async () => {
      // Arrange
      global.fetch.mockResolvedValueOnce({
        ok: false,
        status: 500,
        statusText: 'Internal Server Error'
      });

      // Act & Assert
      await expect(listReservations()).rejects.toThrow('Failed to fetch reservations');
    });

    test('should throw error when network error occurs', async () => {
      // Arrange
      global.fetch.mockRejectedValueOnce(new Error('Network error'));

      // Act & Assert
      await expect(listReservations()).rejects.toThrow('Network error');
    });

    test('should handle malformed JSON response', async () => {
      // Arrange
      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => { throw new Error('Invalid JSON'); }
      });

      // Act & Assert
      await expect(listReservations()).rejects.toThrow('Invalid JSON');
    });
  });

  /**
   * Tests for createReservation function
   */
  describe('createReservation', () => {
    const validPayload = {
      guestName: 'John Doe',
      hotelName: 'Grand Hotel',
      checkIn: '2025-12-01',
      checkOut: '2025-12-05'
    };

    test('should create reservation successfully with valid payload', async () => {
      // Arrange
      const mockResponse = {
        id: 1,
        ...validPayload,
        status: 'ACTIVE'
      };

      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => mockResponse
      });

      // Act
      const result = await createReservation(validPayload);

      // Assert
      expect(result).toEqual(mockResponse);
      expect(result.id).toBe(1);
      expect(result.status).toBe('ACTIVE');
      
      expect(global.fetch).toHaveBeenCalledTimes(1);
      expect(global.fetch).toHaveBeenCalledWith(
        'http://localhost:8080/api/reservations',
        {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(validPayload)
        }
      );
    });

    test('should throw error when creating with invalid dates', async () => {
      // Arrange
      const invalidPayload = {
        ...validPayload,
        checkOut: '2025-11-30' // Before check-in
      };

      global.fetch.mockResolvedValueOnce({
        ok: false,
        status: 400,
        json: async () => ({ message: 'Check-out date must be after check-in date' })
      });

      // Act & Assert
      await expect(createReservation(invalidPayload)).rejects.toThrow(
        'Check-out date must be after check-in date'
      );
    });

    test('should throw error when server returns 500', async () => {
      // Arrange
      global.fetch.mockResolvedValueOnce({
        ok: false,
        status: 500,
        json: async () => ({ message: 'Internal server error' })
      });

      // Act & Assert
      await expect(createReservation(validPayload)).rejects.toThrow('Internal server error');
    });

    test('should throw default error message when no message in response', async () => {
      // Arrange
      global.fetch.mockResolvedValueOnce({
        ok: false,
        status: 400,
        json: async () => ({})
      });

      // Act & Assert
      await expect(createReservation(validPayload)).rejects.toThrow('Create failed');
    });

    test('should handle network timeout', async () => {
      // Arrange
      global.fetch.mockRejectedValueOnce(new Error('Request timeout'));

      // Act & Assert
      await expect(createReservation(validPayload)).rejects.toThrow('Request timeout');
    });

    test('should properly serialize complex payload', async () => {
      // Arrange
      const complexPayload = {
        ...validPayload,
        specialRequests: ['early check-in', 'ocean view']
      };

      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => ({ id: 1, ...complexPayload, status: 'ACTIVE' })
      });

      // Act
      await createReservation(complexPayload);

      // Assert
      expect(global.fetch).toHaveBeenCalledWith(
        expect.any(String),
        expect.objectContaining({
          body: JSON.stringify(complexPayload)
        })
      );
    });
  });

  /**
   * Tests for updateReservation function
   */
  describe('updateReservation', () => {
    const updatePayload = {
      guestName: 'John Doe Updated',
      hotelName: 'Grand Hotel',
      checkIn: '2025-12-01',
      checkOut: '2025-12-06'
    };

    test('should update reservation successfully', async () => {
      // Arrange
      const mockResponse = {
        id: 1,
        ...updatePayload,
        status: 'ACTIVE'
      };

      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => mockResponse
      });

      // Act
      const result = await updateReservation(1, updatePayload);

      // Assert
      expect(result).toEqual(mockResponse);
      expect(result.guestName).toBe('John Doe Updated');
      
      expect(global.fetch).toHaveBeenCalledWith(
        'http://localhost:8080/api/reservations/1',
        {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(updatePayload)
        }
      );
    });

    test('should throw error when updating non-existent reservation', async () => {
      // Arrange
      global.fetch.mockResolvedValueOnce({
        ok: false,
        status: 404,
        json: async () => ({ message: 'Reservation not found with id: 999' })
      });

      // Act & Assert
      await expect(updateReservation(999, updatePayload)).rejects.toThrow(
        'Reservation not found with id: 999'
      );
    });

    test('should throw error when updating canceled reservation', async () => {
      // Arrange
      global.fetch.mockResolvedValueOnce({
        ok: false,
        status: 400,
        json: async () => ({ message: 'Cannot update a canceled reservation' })
      });

      // Act & Assert
      await expect(updateReservation(1, updatePayload)).rejects.toThrow(
        'Cannot update a canceled reservation'
      );
    });

    test('should properly encode ID in URL', async () => {
      // Arrange
      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => ({ id: 123, ...updatePayload, status: 'ACTIVE' })
      });

      // Act
      await updateReservation(123, updatePayload);

      // Assert
      expect(global.fetch).toHaveBeenCalledWith(
        'http://localhost:8080/api/reservations/123',
        expect.any(Object)
      );
    });

    test('should handle special characters in ID', async () => {
      // Arrange - Testing with string ID that needs encoding
      const specialId = 'test-123';
      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => ({ id: specialId, ...updatePayload, status: 'ACTIVE' })
      });

      // Act
      await updateReservation(specialId, updatePayload);

      // Assert
      expect(global.fetch).toHaveBeenCalledWith(
        'http://localhost:8080/api/reservations/test-123',
        expect.any(Object)
      );
    });
  });

  /**
   * Tests for cancelReservation function
   */
  describe('cancelReservation', () => {
    test('should cancel reservation successfully', async () => {
      // Arrange
      const mockResponse = {
        id: 1,
        guestName: 'John Doe',
        hotelName: 'Grand Hotel',
        checkIn: '2025-12-01',
        checkOut: '2025-12-05',
        status: 'CANCELED'
      };

      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => mockResponse
      });

      // Act
      const result = await cancelReservation(1);

      // Assert
      expect(result).toEqual(mockResponse);
      expect(result.status).toBe('CANCELED');
      
      expect(global.fetch).toHaveBeenCalledWith(
        'http://localhost:8080/api/reservations/1',
        { method: 'DELETE' }
      );
    });

    test('should throw error when canceling non-existent reservation', async () => {
      // Arrange
      global.fetch.mockResolvedValueOnce({
        ok: false,
        status: 404,
        json: async () => ({ message: 'Reservation not found with id: 999' })
      });

      // Act & Assert
      await expect(cancelReservation(999)).rejects.toThrow(
        'Reservation not found with id: 999'
      );
    });

    test('should throw error when canceling already canceled reservation', async () => {
      // Arrange
      global.fetch.mockResolvedValueOnce({
        ok: false,
        status: 400,
        json: async () => ({ message: 'Reservation is already canceled' })
      });

      // Act & Assert
      await expect(cancelReservation(1)).rejects.toThrow(
        'Reservation is already canceled'
      );
    });

    test('should use DELETE HTTP method', async () => {
      // Arrange
      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => ({ id: 1, status: 'CANCELED' })
      });

      // Act
      await cancelReservation(1);

      // Assert
      const fetchCall = global.fetch.mock.calls[0];
      expect(fetchCall[1].method).toBe('DELETE');
    });

    test('should handle server error during cancellation', async () => {
      // Arrange
      global.fetch.mockResolvedValueOnce({
        ok: false,
        status: 500,
        json: async () => ({ message: 'Database error' })
      });

      // Act & Assert
      await expect(cancelReservation(1)).rejects.toThrow('Database error');
    });

    test('should throw default error when no message provided', async () => {
      // Arrange
      global.fetch.mockResolvedValueOnce({
        ok: false,
        status: 500,
        json: async () => ({})
      });

      // Act & Assert
      await expect(cancelReservation(1)).rejects.toThrow('Cancel failed');
    });
  });

  /**
   * Integration-like tests for multiple operations
   */
  describe('API Client Integration Scenarios', () => {
    test('should handle complete reservation lifecycle', async () => {
      // Create
      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => ({ id: 1, guestName: 'John', status: 'ACTIVE' })
      });
      const created = await createReservation({ guestName: 'John', hotelName: 'Hotel' });
      expect(created.id).toBe(1);

      // Update
      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => ({ id: 1, guestName: 'John Updated', status: 'ACTIVE' })
      });
      const updated = await updateReservation(1, { guestName: 'John Updated' });
      expect(updated.guestName).toBe('John Updated');

      // Cancel
      global.fetch.mockResolvedValueOnce({
        ok: true,
        json: async () => ({ id: 1, guestName: 'John Updated', status: 'CANCELED' })
      });
      const canceled = await cancelReservation(1);
      expect(canceled.status).toBe('CANCELED');
    });

    test('should handle concurrent requests correctly', async () => {
      // Arrange
      global.fetch.mockResolvedValue({
        ok: true,
        json: async () => ([{ id: 1 }, { id: 2 }])
      });

      // Act
      const [result1, result2] = await Promise.all([
        listReservations(),
        listReservations()
      ]);

      // Assert
      expect(result1).toHaveLength(2);
      expect(result2).toHaveLength(2);
      expect(global.fetch).toHaveBeenCalledTimes(2);
    });
  });
});
