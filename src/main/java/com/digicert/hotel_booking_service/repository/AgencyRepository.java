package com.digicert.hotel_booking_service.repository;

import com.digicert.hotel_booking_service.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {
}
