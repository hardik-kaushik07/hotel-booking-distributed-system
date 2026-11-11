package com.ms.rating.controller;

import com.ms.rating.entity.RatingEntity;
import com.ms.rating.service.ServiceRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private ServiceRating serviceRating;


    @PostMapping("/upload")
    public ResponseEntity<RatingEntity> save(@RequestBody RatingEntity ratings){
        RatingEntity savedRating = serviceRating.save(ratings);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<RatingEntity>> getAll(){
        return ResponseEntity.ok(serviceRating.getAll());
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RatingEntity>> getByUserId(@PathVariable String userId){
        return ResponseEntity.ok(serviceRating.findByUserId(userId));
    }


    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<RatingEntity>> getByHotelId(@PathVariable String hotelId){
        return ResponseEntity.ok(serviceRating.findByHotelId(hotelId));
    }
}
