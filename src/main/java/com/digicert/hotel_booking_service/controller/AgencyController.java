package com.digicert.hotel_booking_service.controller;

import com.digicert.hotel_booking_service.dto.AgencyDTO;
import com.digicert.hotel_booking_service.entity.Agency;
import com.digicert.hotel_booking_service.service.AgencyService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Path("/agencies")
public class AgencyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AgencyController.class);

    @Autowired
    private AgencyService agencyService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AgencyDTO> getAllAgencies() {
        LOGGER.info("Received request to retrieve all agencies");
        List<AgencyDTO> agencies = agencyService.findAll();
        return agencies;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAgency(Agency request) {
        AgencyDTO agency = agencyService.save(request);
        return Response.ok(agency).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAgencyById(@PathParam("id") Long id) {
        LOGGER.info("Received request to retrieve agency by ID: {}", id);
        AgencyDTO agency = agencyService.getAgencyById(id);
        return Response.ok(agency).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAgency(@PathParam("id") Long id, Agency request) {
        LOGGER.info("Received request to update agency by ID: {}", id);
        AgencyDTO agency = agencyService.updateAgency(id, request);
        return Response.ok(agency).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAgency(@PathParam("id") Long id) {
        LOGGER.info("Received request to delete agency by ID: {}", id);
        agencyService.deleteAgency(id);
        return Response.noContent().build();
    }
}
