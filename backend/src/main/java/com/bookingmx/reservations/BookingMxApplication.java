package com.bookingmx.reservations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.bookingmx.reservations.repo.ReservationRepository;
import com.bookingmx.reservations.service.ReservationService;

/**
 * Main application class for BookingMx reservation system.
 * This is the entry point of the Spring Boot application.
 * 
 * @author BookingMx Team
 * @version 1.0
 * @since 2024-01-01
 */
@SpringBootApplication
public class BookingMxApplication {

    /**
     * Main method to start the Spring Boot application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BookingMxApplication.class, args);
    }

    /**
     * Creates and configures the ReservationRepository bean.
     * 
     * @return a new instance of ReservationRepository
     */
    @Bean
    public ReservationRepository reservationRepository() {
        return new ReservationRepository();
    }

    /**
     * Creates and configures the ReservationService bean.
     * 
     * @param repository the repository to be injected
     * @return a new instance of ReservationService
     */
    @Bean
    public ReservationService reservationService(ReservationRepository repository) {
        return new ReservationService(repository);
    }
}