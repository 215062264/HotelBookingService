package com.digicert.hotel_booking_service.data;

import com.digicert.hotel_booking_service.entity.Agency;
import com.digicert.hotel_booking_service.entity.Hotel;
import com.digicert.hotel_booking_service.entity.Reservation;
import com.digicert.hotel_booking_service.service.AgencyService;
import com.digicert.hotel_booking_service.service.HotelService;
import com.digicert.hotel_booking_service.service.ReservationService;
import jakarta.annotation.PostConstruct;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
@AllArgsConstructor
public class DBDataInitializer {

    private final AgencyService agencyService;
    private final HotelService hotelService;
    private final ReservationService reservationService;


    @PostConstruct
    public void init() {
        // Create agencies
        Agency agency1 = new Agency();
        agency1.setName("Agency 1");
        agency1.setAddress("Agency 1");
        Agency agency2 = new Agency();
        agency2.setName("Agency 2");
        agency2.setAddress("456 Elm St");
        agencyService.save(agency1);
        agencyService.save(agency2);

        // Create hotels
        Hotel hotel1 = new Hotel();
        hotel1.setName("Hotel 1");
        hotel1.setAddress("789 Oak St");
        Hotel hotel2 = new Hotel();
        hotel2.setName("Hotel 2");
        hotel2.setAddress("321 Pine St");
        hotelService.save(hotel1);
        hotelService.save(hotel2);

        // Create reservations
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkinDate1 = LocalDate.parse("2022-01-01", formatter);
        Date checkinDate1Date = Date.from(checkinDate1.atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate checkoutDate1 = LocalDate.parse("2022-01-03", formatter);
        Date checkoutDate1Date = Date.from(checkoutDate1.atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate checkinDate2 = LocalDate.parse("2022-01-05", formatter);
        Date checkinDate2Date = Date.from(checkinDate2.atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate checkoutDate2 = LocalDate.parse("2022-01-07", formatter);
        Date checkoutDate2Date = Date.from(checkoutDate2.atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate checkinDate3 = LocalDate.parse("2022-01-10", formatter);
        Date checkinDate3Date = Date.from(checkinDate3.atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate checkoutDate3 = LocalDate.parse("2022-01-12", formatter);
        Date checkoutDate3Date = Date.from(checkoutDate3.atStartOfDay(ZoneId.systemDefault()).toInstant());


        Reservation reservation1 = new Reservation();
        reservation1.setGuestName("Nick Fury");
        reservation1.setCheckInDate(checkinDate1Date);
        reservation1.setCheckOutDate(checkoutDate1Date);
        reservation1.setHotel(hotel1);
        reservation1.setAgency(agency1);

        Reservation reservation2 = new Reservation();
        reservation2.setGuestName("Tony Stark");
        reservation2.setCheckInDate(checkinDate2Date);
        reservation2.setCheckOutDate(checkoutDate2Date);
        reservation2.setHotel(hotel2);
        reservation2.setAgency(agency2);

        Reservation reservation3 = new Reservation();
        reservation3.setGuestName("Steve Rogers");
        reservation3.setCheckInDate(checkinDate3Date);
        reservation3.setCheckOutDate(checkoutDate3Date);
        reservation3.setHotel(hotel2);
        reservation3.setAgency(agency1);

        reservationService.save(reservation1);
        reservationService.save(reservation2);
        reservationService.save(reservation3);
    }
}
