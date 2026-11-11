package com.ms.booking.controller;

import com.ms.booking.entity.BookingEntity;
import com.ms.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class    BookingController {

    @Autowired
    private BookingService bookingService;


    @PostMapping("/create")
    public ResponseEntity<BookingEntity> createBooking(@RequestBody BookingEntity booking) {
        BookingEntity savedBooking = bookingService.createBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BookingEntity>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingEntity>> getBookingsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
    }
}
