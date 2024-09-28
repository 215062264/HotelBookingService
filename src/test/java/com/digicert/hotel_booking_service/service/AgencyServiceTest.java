package com.digicert.hotel_booking_service.service;

import com.digicert.hotel_booking_service.entity.Agency;
import com.digicert.hotel_booking_service.dto.AgencyDTO;
import com.digicert.hotel_booking_service.repository.AgencyRepository;
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

public class AgencyServiceTest {

    @Mock
    private AgencyRepository agencyRepository;

    @InjectMocks
    private AgencyService agencyService;

    private Agency agency;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        agency = new Agency(1L, "Test Agency", "123 Test St",null);
    }

    @Test
    public void testFindAll() {
        when(agencyRepository.findAll()).thenReturn(List.of(agency));

        List<AgencyDTO> agencies = agencyService.findAll();

        assertEquals(1, agencies.size());
        assertEquals("Test Agency", agencies.get(0).getName());
    }

    @Test
    public void testGetAgencyById() {
        when(agencyRepository.findById(1L)).thenReturn(Optional.of(agency));

        AgencyDTO agencyDTO = agencyService.getAgencyById(1L);

        assertEquals("Test Agency", agencyDTO.getName());
    }

    @Test
    public void testGetAgencyById_NotFound() {
        when(agencyRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            agencyService.getAgencyById(1L);
        });

        assertEquals("Agency not found", exception.getMessage());
    }

    @Test
    public void testSave() {
        when(agencyRepository.save(any(Agency.class))).thenReturn(agency);

        AgencyDTO savedAgency = agencyService.save(agency);

        assertEquals("Test Agency", savedAgency.getName());
        verify(agencyRepository, times(1)).save(any(Agency.class));
    }

    @Test
    public void testUpdateAgency() {
        when(agencyRepository.findById(1L)).thenReturn(Optional.of(agency));
        when(agencyRepository.save(any(Agency.class))).thenReturn(agency);

        AgencyDTO updatedAgency = agencyService.updateAgency(1L, agency);

        assertEquals("Test Agency", updatedAgency.getName());
        verify(agencyRepository, times(1)).save(any(Agency.class));
    }

    @Test
    public void testUpdateAgency_NotFound() {
        when(agencyRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            agencyService.updateAgency(1L, agency);
        });

        assertEquals("Agency not found during update", exception.getMessage());
    }

    @Test
    public void testDeleteAgency() {
        when(agencyRepository.existsById(1L)).thenReturn(true);

        agencyService.deleteAgency(1L);

        verify(agencyRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteAgency_NotFound() {
        when(agencyRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            agencyService.deleteAgency(1L);
        });

        assertEquals("Agency not found for deletion", exception.getMessage());
    }
}

