package com.ms.hotel.service;

import com.ms.hotel.entity.HotelEntity;
import com.ms.hotel.exception.ResourceNotFoundException;
import com.ms.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public HotelEntity saveHotel(HotelEntity hotelEntity){
        String hotelId = UUID.randomUUID().toString();
        hotelEntity.setHotel_id(hotelId);
        return hotelRepository.save(hotelEntity);
    }

    public List<HotelEntity> getAll(){
        return hotelRepository.findAll();
    }

    public HotelEntity getById(String hotelId){
        return hotelRepository.findById(hotelId).orElseThrow(()-> new ResourceNotFoundException("Hotel Not Found."));
    }
}
