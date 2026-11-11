package com.ms.NotificationService.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    private static final Logger logger = LoggerFactory.getLogger(NotificationListener.class);

    @Autowired
    private ObjectMapper objectMapper;

    // 1. Listens for the initial PENDING booking, Here booking is created and this acts as consume
    // It handles the partition of the topic, and sends the mail to the booking id which is Fetch from
    // the booking-event.
    @KafkaListener(topics = "booking-events", groupId = "notification-group")
    public void handleBookingPendingEvent(String eventPayload) {
        try {
            JsonNode bookingNode = objectMapper.readTree(eventPayload);
            String bookingId = bookingNode.has("bookingId") ?
                    bookingNode.get("bookingId").asText() :
                    bookingNode.get("id").asText();
            String userId = bookingNode.has("userId") ? bookingNode.get("userId").asText() : "Unknown";

            logger.info("EMAIL SENT TO USER ID: " + userId);
            logger.info("Message: We have received your booking request (ID: " + bookingId + "). Payment is currently processing.");


        } catch (Exception e) {
            logger.error("Error processing notification for booking-events", e);
        }
    }

    // 2. Listens for the CONFIRMED payment, here the payment service which acts as producer will sends the
    // confirmation of payment so that notification sevice notify by mail for that booking Id.
    @KafkaListener(topics = "payment-events", groupId = "notification-group-v6")
    public void handlePaymentSuccessEvent(String paymentPayload) {
        try {
            JsonNode paymentNode = objectMapper.readTree(paymentPayload);
            String bookingId = paymentNode.get("bookingId").asText();


            logger.info("EMAIL SENT FOR BOOKING ID: " + bookingId);
            logger.info("Message: Payment successful! Your booking is now CONFIRMED.");


        } catch (Exception e) {
            logger.error("Error processing notification for payment-events", e);
        }
    }
}
