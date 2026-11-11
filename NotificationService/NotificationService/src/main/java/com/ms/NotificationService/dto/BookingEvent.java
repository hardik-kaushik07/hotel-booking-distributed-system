package com.ms.NotificationService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingEvent {

    private String bookingId;
    private String userId;
    private String hotelId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;
    private String token;
}
