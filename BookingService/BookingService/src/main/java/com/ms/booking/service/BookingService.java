package com.ms.booking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ms.booking.DTO.BookingEvent;
import com.ms.booking.entity.BookingEntity;
import com.ms.booking.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
    private static final String TOPIC_NAME = "booking-events";

    private final BookingRepository bookingRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public BookingService(BookingRepository bookingRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.bookingRepository = bookingRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    public BookingEntity createBooking(BookingEntity booking) {
        booking.setBookingId(UUID.randomUUID().toString());
        booking.setStatus("PENDING");

        BookingEntity savedBooking = bookingRepository.save(booking);

        // Extract token safely from the active request thread
        String tokenValue = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            tokenValue = jwtAuth.getToken().getTokenValue();
        }

        BookingEvent event = BookingEvent.builder()
                .bookingId(savedBooking.getBookingId())
                .hotelId(savedBooking.getHotelId())
                .userId(savedBooking.getUserId())
                .checkInDate(savedBooking.getCheckInDate())
                .checkOutDate(savedBooking.getCheckOutDate())
                .status(savedBooking.getStatus())
                .token(tokenValue)
                .build();

        try {
            String jsonPayload = objectMapper.writeValueAsString(event);
            logger.info("Publishing BookingEvent JSON to Kafka: {}", jsonPayload);

            kafkaTemplate.send(TOPIC_NAME, savedBooking.getBookingId(), jsonPayload)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            logger.info("Kafka publish SUCCESS for booking: {} at offset: {}",
                                    savedBooking.getBookingId(),
                                    result.getRecordMetadata().offset());
                        } else {
                            logger.error("Kafka publish FAILED for booking: {}. Cause: {}",
                                    savedBooking.getBookingId(), ex.getMessage());
                        }
                    });

        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize BookingEvent to JSON", e);
        }

        return savedBooking;
    }

    public List<BookingEntity> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<BookingEntity> getBookingsByUserId(String userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<BookingEntity> getBookingsByHotelId(String hotelId) {
        return bookingRepository.findByHotelId(hotelId);
    }
}


