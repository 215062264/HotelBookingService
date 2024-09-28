package com.digicert.hotel_booking_service.service;

import com.digicert.hotel_booking_service.entity.Reservation;
import com.digicert.hotel_booking_service.entity.Hotel;
import com.digicert.hotel_booking_service.entity.Agency;
import com.digicert.hotel_booking_service.dto.ReservationDTO;
import com.digicert.hotel_booking_service.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private HotelService hotelService;

    @Mock
    private AgencyService agencyService;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;
    private Hotel hotel;
    private Agency agency;

    @BeforeEach
    public void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);

        // Set up the Hotel and Agency
        hotel = new Hotel(1L, "Test Hotel", "123 Test St", null);
        agency = new Agency(1L, "Test Agency", "456 Agency St", null);

        // Create Date objects for check-in and check-out
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date checkInDate = sdf.parse("2021-12-31");
        Date checkOutDate = sdf.parse("2022-01-02");

        // Create Reservation with Date objects
        reservation = new Reservation(1L, "John Doe", checkInDate, checkOutDate, hotel, agency);
    }

    @Test
    public void testGetAllReservations() {
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));
        when(hotelService.getHotelEntityById(any(Long.class))).thenReturn(hotel);
        when(agencyService.getAgencyEntityById(any(Long.class))).thenReturn(agency);

        List<ReservationDTO> reservations = reservationService.getAllReservations();

        assertEquals(1, reservations.size());
        assertEquals("John Doe", reservations.get(0).getGuestName());
    }

    @Test
    public void testGetReservationById() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        ReservationDTO foundReservation = reservationService.getReservationDTOById(1L);

        assertEquals("John Doe", foundReservation.getGuestName());
    }

    @Test
    public void testGetReservationById_NotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            reservationService.getReservationDTOById(1L);
        });

        assertEquals("Reservation not found", exception.getMessage());
    }

    @Test
    public void testCreateReservation() {
        when(hotelService.getHotelEntityById(1L)).thenReturn(hotel);
        when(agencyService.getAgencyEntityById(1L)).thenReturn(agency);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        reservation.setHotel(hotel);
        reservation.setAgency(agency);

        ReservationDTO createdReservation = reservationService.save(reservation);

        assertEquals("John Doe", createdReservation.getGuestName());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    public void testUpdateReservation() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        when(hotelService.getHotelEntityById(1L)).thenReturn(hotel);
        when(agencyService.getAgencyEntityById(1L)).thenReturn(agency);

        Reservation updatedReservation = new Reservation();
        updatedReservation.setGuestName("Jane Doe");
        updatedReservation.setHotel(hotel);
        updatedReservation.setAgency(agency);

        ReservationDTO result = reservationService.updateReservation(1L, updatedReservation);

        assertEquals("Jane Doe", result.getGuestName());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    public void testUpdateReservation_NotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            reservationService.updateReservation(1L, reservation);
        });

        assertEquals("Reservation not found during update", exception.getMessage());
    }

    @Test
    public void testDeleteReservation() {
        when(reservationRepository.existsById(1L)).thenReturn(true);

        reservationService.deleteReservation(1L);

        verify(reservationRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteReservation_NotFound() {
        when(reservationRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            reservationService.deleteReservation(1L);
        });

        assertEquals("Reservation not found for deletion", exception.getMessage());
    }
}
