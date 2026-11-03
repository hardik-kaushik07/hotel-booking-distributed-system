package com.ms.rating.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "ratings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingEntity {

    @Id
    @Column(name = "rating_id")
    private String ratingId;
    @Column(name = "hotel_id")
    private String hotelId;
    @Column(name = "user_id")
    private String userId;
    private int rating;
    private String feedback;
}
