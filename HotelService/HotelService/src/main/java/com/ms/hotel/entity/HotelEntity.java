package com.ms.hotel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "hotels")
public class HotelEntity {

    @Id
    private String hotel_id;
    private String hotel_name;
    private String hotel_location;
    private String hotel_about;
}
