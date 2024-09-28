package com.digicert.hotel_booking_service.service;

import com.digicert.hotel_booking_service.dto.HotelDTO;
import com.digicert.hotel_booking_service.entity.Hotel;
import com.digicert.hotel_booking_service.repository.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelService.class);
    private final HotelRepository hotelRepository;

    public List<HotelDTO> findAll() {
        LOGGER.info("Retrieving all hotels");
        return hotelRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public HotelDTO getHotelById(Long id) {
        LOGGER.info("Retrieving hotel details by ID: {}", id);
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));
        // Convert to DTO
        return new HotelDTO(hotel.getId(), hotel.getName(), hotel.getAddress());
    }

    public Hotel getHotelEntityById(Long id) {
        LOGGER.info("Retrieving hotel entity by ID: {}", id);
        return hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));
    }

    public HotelDTO save(Hotel hotel) {
        LOGGER.info("Creating new hotel: {}", hotel);
        if(hotel.getName() == null || hotel.getAddress() == null) {
            throw new IllegalArgumentException("Hotel name and address must not be null");
        }
        return convertToDTO(hotelRepository.save(hotel));
    }

    public HotelDTO updateHotel(Long id, Hotel request) {
        LOGGER.info("Updating hotel by ID: {}", id);
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Hotel not found"));
        hotel.setName(request.getName());
        hotel.setAddress(request.getAddress());
        return convertToDTO(hotelRepository.save(hotel));
    }

    public void deleteHotel(Long id) {
        LOGGER.info("Deleting hotel by ID: {}", id);
        if(!hotelRepository.existsById(id)) {
            throw new EntityNotFoundException("Hotel not found for deletion");
        }
        hotelRepository.deleteById(id);
    }

    private HotelDTO convertToDTO(Hotel hotel) {
        return new HotelDTO(hotel.getId(), hotel.getName(), hotel.getAddress());
    }
}

