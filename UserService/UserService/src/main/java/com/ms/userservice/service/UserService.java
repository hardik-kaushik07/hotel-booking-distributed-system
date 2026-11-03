package com.ms.userservice.service;

import com.ms.userservice.entity.Hotel;
import com.ms.userservice.entity.Rating;
import com.ms.userservice.entity.UserEntity;
import com.ms.userservice.exception.ResourceNotFoundException;
import com.ms.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<UserEntity> getAllUsers(){
        List<UserEntity> users = userRepository.findAll();
        for(UserEntity user : users) {
            Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/user/" + user.getUser_id(), Rating[].class);

            List<Rating> ratings =  Arrays.stream(ratingsOfUser).toList();

            ratings.forEach(rating -> {

                Hotel hotels = restTemplate.getForObject("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
                rating.setHotel(hotels);
            });


            user.setRatings(ratings);
        }
        return users;
    }

    public UserEntity getUserById(String id){
        UserEntity users = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No User Found."));
//        Here in this link, localhost:8082 can be change on runtime, so it should be dynamic but now it is not dynamic.
//        and this code will only find ratings not hotel.

//        ArrayList<Rating> ratingOfUser = restTemplate.getForObject("http://localhost:8082/ratings/user/" + users.getUser_id(), ArrayList.class);
//        users.setRatings(ratingOfUser);
//        return users;

//        Now how to find ratings as well as hotel inside UserService?

//        Here we have localhost:8082, and localhost 8081 which can be change in future , so we can make it dynamic also, currently it is
//        hard coded.


//        To make it dynamic, we can change to localhost:8082 to Rating-Service(This is the name which is visible on the
//        eureka server on port 8761).
//        Example: http://Rating-Service/ratings/user/

//        Do not forget to add @LoadBalanced in the configuration over RestTemplate.

//        Rating[] ratingsOfUser = restTemplate.getForObject(
//                "http://localhost:8082/ratings/user/" + users.getUser_id(), Rating[].class);

        Rating[] ratingsOfUser = restTemplate.getForObject(
                "http://RATING-SERVICE/ratings/user/" + users.getUser_id(), Rating[].class);

        List<Rating> ratings =  Arrays.stream(ratingsOfUser).toList();

        ratings.forEach(rating -> {

            Hotel hotels = restTemplate.getForObject("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);

//            we can do this thing (Hotel hotels = restTemplate.getForObject("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);)
//            by FeignClient also, both RestTemplate and FeignClient do same thing but there implementation is different.
//            In FeignClient we use interface to implement.

            rating.setHotel(hotels);
        });
        users.setRatings(ratings);
        return users;
    }

    public UserEntity saveUsers(UserEntity users){
        String userId = UUID.randomUUID().toString();
        users.setUser_id(userId);
        return userRepository.save(users);
    }
}
