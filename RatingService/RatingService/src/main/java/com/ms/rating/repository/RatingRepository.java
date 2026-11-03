package com.ms.rating.repository;

import com.ms.rating.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, String> {

    List<RatingEntity> findByUserId(String userId);

    List<RatingEntity> findByHotelId(String hotelId);
}
