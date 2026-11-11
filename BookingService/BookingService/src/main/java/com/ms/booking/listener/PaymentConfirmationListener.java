package com.ms.booking.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.booking.entity.BookingEntity;
import com.ms.booking.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class PaymentConfirmationListener {

    private static final Logger logger = LoggerFactory.getLogger(PaymentConfirmationListener.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ObjectMapper objectMapper;

   // It wakes up Booking Service, and it acts as Consumer and Payment acts as a Producer
    @KafkaListener(topics = "payment-events", groupId = "booking-group-final-1")
    public void handlePaymentSuccess(String paymentPayload) {
        try {
            logger.info("Received payment event: {}", paymentPayload);

            // From the payment-event we extract the bookingId and status, so that if we get the Confirmed status
            // then we update the pending status to confirmed into DataBase.
            JsonNode paymentNode = objectMapper.readTree(paymentPayload);
            String bookingId = paymentNode.get("bookingId").asText();
            String newStatus = paymentNode.get("status").asText();

            logger.info("Booking ID: {} | Payment Status: {}", bookingId, newStatus);

             // We find the Booking from bookingId.
            Optional<BookingEntity> bookingOpt = bookingRepository.findById(bookingId);

            // If booking is present then we change booking status to CONFIRMED as we have already
            // check whether payment  is success or not.
            if (bookingOpt.isPresent()) {
                BookingEntity booking = bookingOpt.get();

                if ("FAILED".equalsIgnoreCase(newStatus)) {
                    booking.setStatus("CANCELLED");
                    logger.warn("Payment failed. Booking {} updated to CANCELLED", bookingId);
                } else {
                    booking.setStatus("CONFIRMED");
                    logger.info("SUCCESS: Booking {} updated to CONFIRMED", bookingId);
                }

                bookingRepository.saveAndFlush(booking);
            }
        } catch (Exception e) {
            logger.error("Error processing payment confirmation", e);
            throw new RuntimeException(e);
        }
    }
}