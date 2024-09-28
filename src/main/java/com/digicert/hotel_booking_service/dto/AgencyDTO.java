package com.digicert.hotel_booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AgencyDTO {
    private Long id;
    private String name;
    private String address;
}
