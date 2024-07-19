package it.forcina.co2_tracking_core.controller;

import it.forcina.co2_tracking_core.dto.CityDto;
import it.forcina.co2_tracking_core.dto.response.Response;
import it.forcina.co2_tracking_core.exception.CityException;
import it.forcina.co2_tracking_core.persistence.entity.City;
import it.forcina.co2_tracking_core.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<String>> insertCity(@RequestBody CityDto dto) throws CityException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new Response.Builder<String>()
                        .message("New city created successfully")
                        .info(String.format("City ID: {%d}", cityService.insertCity(dto)))
                        .build()
        );
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Response<List<City>>> getAllCities() throws CityException {
        return ResponseEntity.status(HttpStatus.OK).body(
                new Response.Builder<List<City>>()
                        .message("Cities successfully retrieved")
                        .info(cityService.getAllCities())
                        .build()
        );
    }
}
