package com.digicert.hotel_booking_service.controller;

import com.digicert.hotel_booking_service.dto.HotelDTO;
import com.digicert.hotel_booking_service.entity.Hotel;
import com.digicert.hotel_booking_service.service.HotelService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Path("/hotels")
public class HotelController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelController.class);

    @Autowired
    private HotelService hotelService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HotelDTO> getAllHotels() {
        return hotelService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHotelById(@PathParam("id") Long id) {
        LOGGER.info("Received request to retrieve hotel by ID: {}", id);
        HotelDTO hotelDTO = hotelService.getHotelById(id);
        return Response.ok(hotelDTO).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createHotel(Hotel hotel) {
        HotelDTO createdHotel = hotelService.save(hotel);
        return Response.status(Response.Status.CREATED).entity(createdHotel).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateHotel(@PathParam("id") Long id, Hotel request) {
        LOGGER.info("Received request to update hotel by ID: {}", id);
        HotelDTO hotelDTO = hotelService.updateHotel(id, request);
        return Response.ok(hotelDTO).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteHotel(@PathParam("id") Long id) {
        LOGGER.info("Received request to delete hotel by ID: {}", id);
        hotelService.deleteHotel(id);
        return Response.noContent().build();
    }
}