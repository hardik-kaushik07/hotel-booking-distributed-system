package com.ms.Payment_Service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentProcessor {
    private static final Logger logger = LoggerFactory.getLogger(PaymentProcessor.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "booking-events", groupId = "payment-group")
    public void processPayment(String eventPayload) {
        try {
            logger.info("Received booking event for payment processing: {}", eventPayload);

            // Parse the incoming JSON event
            JsonNode bookingNode = objectMapper.readTree(eventPayload);

            // Extract the booking ID
            String bookingId = bookingNode.has("bookingId") ?
                    bookingNode.get("bookingId").asText() :
                    bookingNode.get("id").asText();

            logger.info("Processing payment for Booking ID: {}", bookingId);

            // Fake 3-second bank transaction delay
            Thread.sleep(3000);

            // The random will generate random values between 0-1, so if it is less then 0.2 then payment FAILED
            // otherwise payment Confirmed.
            boolean isSuccess = Math.random() >= 0.2;
            String status = isSuccess ? "CONFIRMED" : "FAILED";

            // Create a successful payment payload
            String paymentSuccessEvent = "{\"bookingId\": \"" + bookingId + "\", \"status\": \"" + status + "\"}";

            // Send to payment-events and handle success/failure callback
            kafkaTemplate.send("payment-events", bookingId, paymentSuccessEvent)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            logger.info("CONFIRMED event published successfully for booking: {}", bookingId);
                        } else {
                            logger.error("Failed to publish payment event for booking: {}", bookingId, ex);
                        }
                    });

            logger.info("Payment successful. Sent CONFIRMED event back to Kafka.");

        } catch (Exception e) {
            logger.error("Error processing payment", e);
        }
    }
}
