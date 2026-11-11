package com.ms.booking.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingEvent {

    private String bookingId;
    private String hotelId;
    private String userId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;
    private String token;
}
