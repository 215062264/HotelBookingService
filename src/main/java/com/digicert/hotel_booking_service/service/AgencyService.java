package com.digicert.hotel_booking_service.service;

import com.digicert.hotel_booking_service.dto.AgencyDTO;
import com.digicert.hotel_booking_service.entity.Agency;
import com.digicert.hotel_booking_service.repository.AgencyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AgencyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AgencyService.class);

    private final AgencyRepository agencyRepository;

    public List<AgencyDTO> findAll() {
        LOGGER.info("Retrieving all agencies");
        return agencyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AgencyDTO getAgencyById(Long id) {
        LOGGER.info("Retrieving agency by ID: {}", id);
        Agency agency = agencyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Agency not found"));
        return new AgencyDTO(agency.getId(), agency.getName(), agency.getAddress());
    }

    public Agency getAgencyEntityById(Long id) {
        LOGGER.info("Retrieving agency entity by ID: {}", id);
        return agencyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Agency not found"));
    }

    public AgencyDTO save(Agency request) {
        LOGGER.info("Creating new agency: {}", request);
        if (request.getName() == null || request.getAddress() == null) {
            throw new IllegalArgumentException("Agency name and address must not be null");
        }
        return convertToDTO(agencyRepository.save(request));
    }

    public AgencyDTO updateAgency(Long id, Agency request) {
        LOGGER.info("Updating agency by ID: {}", id);
        Agency agency = agencyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Agency not found during update"));
        agency.setName(request.getName());
        agency.setAddress(request.getAddress());
        return convertToDTO(agencyRepository.save(agency));
    }

    public void deleteAgency(Long id) {
        LOGGER.info("Deleting agency by ID: {}", id);
        if (!agencyRepository.existsById(id)) {
            throw new EntityNotFoundException("Agency not found for deletion");
        }
        agencyRepository.deleteById(id);
    }


    private AgencyDTO convertToDTO(Agency agency) {
        return new AgencyDTO(agency.getId(), agency.getName(), agency.getAddress());
    }
}

