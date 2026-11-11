    package com.ms.booking.entity;


    import jakarta.persistence.Entity;
    import jakarta.persistence.Id;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.time.LocalDate;

    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class BookingEntity {

        @Id
        private String bookingId;
        private String hotelId;
        private String userId;
        private LocalDate checkInDate;
        private LocalDate checkOutDate;
        private String status;
    }
