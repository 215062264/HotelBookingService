package com.digicert.hotel_booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationDTO {
    private Long id;
    private String guestName;
    private Date checkInDate;
    private Date checkOutDate;
}
