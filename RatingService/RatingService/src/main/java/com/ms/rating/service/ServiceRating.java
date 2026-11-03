package com.ms.rating.service;


import com.ms.rating.entity.RatingEntity;
import com.ms.rating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServiceRating {

    @Autowired
    private RatingRepository ratingRepository;

    public RatingEntity save(RatingEntity rating){

        String ratingId = UUID.randomUUID().toString();
        rating.setRatingId(ratingId);
        return ratingRepository.save(rating);
    }

    public List<RatingEntity> getAll(){
        return ratingRepository.findAll();
    }

    public List<RatingEntity> findByUserId(String userId){
        return ratingRepository.findByUserId(userId);
    }

    public List<RatingEntity> findByHotelId(String hotelId){
        return ratingRepository.findByHotelId(hotelId);
    }
}
