package com.digicert.hotel_booking_service.controller;

import com.digicert.hotel_booking_service.dto.ReservationDTO;
import com.digicert.hotel_booking_service.entity.Reservation;
import com.digicert.hotel_booking_service.service.ReservationService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Path("/reservations")
public class ReservationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private ReservationService reservationService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReservationDTO> getAllReservations() { return reservationService.getAllReservations();}

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservationById(@PathParam("id") Long id) {
        LOGGER.info("Received request to retrieve reservation by ID: {}", id);
        ReservationDTO reservation = reservationService.getReservationDTOById(id);
        return Response.ok(reservation).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReservation(Reservation request) {
        ReservationDTO reservation = reservationService.save(request);
        return Response.ok(reservation).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateReservation(@PathParam("id") Long id, Reservation request) {
        LOGGER.info("Received request to update reservation by ID: {}", id);
        ReservationDTO reservation = reservationService.updateReservation(id, request);
        return Response.ok(reservation).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteReservation(@PathParam("id") Long id) {
        LOGGER.info("Received request to delete reservation by ID: {}", id);
        reservationService.deleteReservation(id);
        return Response.noContent().build();
    }
}
