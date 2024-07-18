package it.forcina.co2_tracking_core.controller;

import it.forcina.co2_tracking_core.dto.response.Response;
import it.forcina.co2_tracking_core.persistence.entity.User;
import it.forcina.co2_tracking_core.service.CO2ReadingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/readings")
public class CO2ReadingsController {
    private final CO2ReadingsService readingsService;

    @Autowired
    public CO2ReadingsController(CO2ReadingsService readingsService) {
        this.readingsService = readingsService;
    }

    @GetMapping(value = "/sensor", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Response> getAllReadingsBySensorId(@RequestParam("sensorId") Long sensorId,
                                                                                       Authentication authentication) {
        User username = (User) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response.Builder<>().message("Readings successfully retrieved")
                        .info(readingsService.getAllReadingsBySensorId(sensorId, username.getUsername())).build());
    }
}
