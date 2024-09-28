package com.digicert.hotel_booking_service.service;

import com.digicert.hotel_booking_service.entity.Hotel;
import com.digicert.hotel_booking_service.dto.HotelDTO;
import com.digicert.hotel_booking_service.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    private Hotel hotel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        hotel = new Hotel(1L, "Test Hotel", "123 Test Ave", null);
    }

    @Test
    public void testFindAll() {
        when(hotelRepository.findAll()).thenReturn(List.of(hotel));

        List<HotelDTO> hotels = hotelService.findAll();

        assertEquals(1, hotels.size());
        assertEquals("Test Hotel", hotels.get(0).getName());
    }

    @Test
    public void testGetHotelById() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        HotelDTO hotelDTO = hotelService.getHotelById(1L);

        assertEquals("Test Hotel", hotelDTO.getName());
    }

    @Test
    public void testGetHotelById_NotFound() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            hotelService.getHotelById(1L);
        });

        assertEquals("Hotel not found", exception.getMessage());
    }

    @Test
    public void testSave() {
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        HotelDTO savedHotel = hotelService.save(hotel);

        assertEquals("Test Hotel", savedHotel.getName());
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    public void testUpdateHotel() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        Hotel updatedHotel = new Hotel(1L, "Updated Hotel", "456 Updated St", null);
        HotelDTO hotelDTO = hotelService.updateHotel(1L, updatedHotel);

        assertEquals("Updated Hotel", hotelDTO.getName());
        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

    @Test
    public void testUpdateHotel_NotFound() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            hotelService.updateHotel(1L, hotel);
        });

        assertEquals("Hotel not found", exception.getMessage());
    }


    @Test
    public void testDeleteHotel() {
        when(hotelRepository.existsById(1L)).thenReturn(true);

        hotelService.deleteHotel(1L);

        verify(hotelRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteHotel_NotFound() {
        when(hotelRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            hotelService.deleteHotel(1L);
        });

        assertEquals("Hotel not found for deletion", exception.getMessage());
    }
}
