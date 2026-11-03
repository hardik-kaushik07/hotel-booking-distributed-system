package com.ms.userservice.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    @Column(name = "rating_id")
    private String ratingId;
    @Column(name = "hotel_id")
    private String hotelId;
    @Column(name = "user_id")
    private String userId;
    private int rating;
    private String feedback;

    public Hotel hotel;
}
