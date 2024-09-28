package com.digicert.hotel_booking_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

}
