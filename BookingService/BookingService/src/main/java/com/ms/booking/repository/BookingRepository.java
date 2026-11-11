package com.ms.booking.repository;

import com.ms.booking.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, String> {

    List<BookingEntity> findByUserId(String userId);
    List<BookingEntity> findByHotelId(String hotelId);
}
