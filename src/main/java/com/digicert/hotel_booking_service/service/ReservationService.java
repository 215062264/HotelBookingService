package com.digicert.hotel_booking_service.service;

import com.digicert.hotel_booking_service.dto.ReservationDTO;
import com.digicert.hotel_booking_service.entity.Agency;
import com.digicert.hotel_booking_service.entity.Hotel;
import com.digicert.hotel_booking_service.entity.Reservation;
import com.digicert.hotel_booking_service.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;
    private final HotelService hotelService;
    private final AgencyService agencyService;

    public List<ReservationDTO> getAllReservations() {
        LOGGER.info("Retrieving all reservations");
        return reservationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReservationDTO getReservationDTOById(Long id) {
        LOGGER.info("Retrieving reservation by ID: {}", id);
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
        return new ReservationDTO(
                reservation.getId(),
                reservation.getGuestName(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate());
    }

    public ReservationDTO save(Reservation request) {
        LOGGER.info("Creating new reservation: {}", request);

        if (request.getHotel() == null || request.getAgency() == null) {
            throw new IllegalArgumentException("Hotel and Agency must be provided");
        }

        // Fetch the existing hotel and agency
        Hotel hotel = hotelService.getHotelEntityById(request.getHotel().getId());
        Agency agency = agencyService.getAgencyEntityById(request.getAgency().getId());

        if(request.getGuestName() == null || request.getCheckInDate() == null
                || request.getCheckOutDate() == null) {
            throw new IllegalArgumentException("The guest name, check-in and check-out dates need to be provided");
        }

        Reservation reservation = new Reservation();
        reservation.setGuestName(request.getGuestName());
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());
        reservation.setHotel(hotel);
        reservation.setAgency(agency);

        return convertToDTO(reservationRepository.save(reservation));
    }

    public ReservationDTO updateReservation(Long id, Reservation request) {
        LOGGER.info("Updating reservation by ID: {}", id);
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reservation not found during update"));

        reservation.setGuestName(request.getGuestName());
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());

        Hotel hotel = hotelService.getHotelEntityById(request.getHotel().getId()); // Get hotel entity
        Agency agency = agencyService.getAgencyEntityById(request.getAgency().getId()); // Get agency entity

        reservation.setHotel(hotel);
        reservation.setAgency(agency);

        return convertToDTO(reservationRepository.save(reservation));
    }

    public void deleteReservation(Long id) {
        LOGGER.info("Deleting reservation by ID: {}", id);
        if(!reservationRepository.existsById(id)) {
            throw new EntityNotFoundException("Reservation not found for deletion");
        }
        reservationRepository.deleteById(id);
    }

    private ReservationDTO convertToDTO(Reservation reservation) {
        return new ReservationDTO(
                reservation.getId(),
                reservation.getGuestName(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate());
    }
}


