package com.digicert.hotel_booking_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String guestName;

    @Column(nullable = false)
    private Date checkInDate;

    @Column(nullable = false)
    private Date checkOutDate;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

}
