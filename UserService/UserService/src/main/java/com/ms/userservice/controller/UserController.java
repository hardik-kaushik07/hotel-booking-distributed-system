package com.ms.userservice.controller;

import com.ms.userservice.entity.UserEntity;
import com.ms.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @PostMapping("/upload")
    public ResponseEntity<UserEntity> saveUsers(@RequestBody UserEntity users){
        UserEntity userEntity = userService.saveUsers(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(userEntity);
    }

    @GetMapping("/getAll")
    @CircuitBreaker(name = "hotelRatingBreaker", fallbackMethod = "hotelRatingBreaker")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    int retryCount = 1;
    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelBreaker")
//    @Retry(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelBreaker")
    @RateLimiter(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelBreaker")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String userId){
        logger.info("retryCount " + retryCount);
        retryCount++;
        UserEntity user = userService.getUserById(userId);return ResponseEntity.ok(user);
    }

    public ResponseEntity<UserEntity> ratingHotelBreaker(String userId, Throwable ex){

        logger.info(ex.getMessage());
        UserEntity user = UserEntity.builder()
                .name("Dummy")
                .email("noreply@gmail.com")
                .about("This is a Dummy user because some service is down.")
                .user_id("3645")
                .build();
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<List<UserEntity>> hotelRatingBreaker( Throwable ex){
        logger.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(List.of());
    }
}
