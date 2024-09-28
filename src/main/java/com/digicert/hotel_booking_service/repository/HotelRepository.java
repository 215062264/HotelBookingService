package com.digicert.hotel_booking_service.repository;

import org.springframework.stereotype.Repository;
import com.digicert.hotel_booking_service.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
