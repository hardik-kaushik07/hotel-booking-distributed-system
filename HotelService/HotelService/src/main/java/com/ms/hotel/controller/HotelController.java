package com.ms.hotel.controller;

import com.ms.hotel.entity.HotelEntity;
import com.ms.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;


    @PostMapping("/upload")
    public ResponseEntity<HotelEntity> save(@RequestBody HotelEntity hotel){
        HotelEntity hotelEntity = hotelService.saveHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelEntity);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<HotelEntity>> getAll(){
        List<HotelEntity> hotels = hotelService.getAll();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelEntity> getById(@PathVariable String hotelId){
        HotelEntity hotels = hotelService.getById(hotelId);
        return ResponseEntity.ok(hotels);
    }
}
