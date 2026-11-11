package com.ms.NotificationService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {

    private String hotelId;
    private String hotelName;
    private String hotelLocation;
    private String hotelAbout;
}
