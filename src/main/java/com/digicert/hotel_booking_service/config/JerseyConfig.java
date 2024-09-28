package com.digicert.hotel_booking_service.config;

import com.digicert.hotel_booking_service.controller.AgencyController;
import com.digicert.hotel_booking_service.controller.HotelController;
import com.digicert.hotel_booking_service.controller.ReservationController;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(ReservationController.class);
        register(HotelController.class);
        register(AgencyController.class);
    }
}
