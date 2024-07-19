package it.forcina.co2_tracking_core.controller;

import it.forcina.co2_tracking_core.dto.CO2ReadingDto;
import it.forcina.co2_tracking_core.dto.response.Response;
import it.forcina.co2_tracking_core.exception.CO2ReadingException;
import it.forcina.co2_tracking_core.persistence.entity.User;
import it.forcina.co2_tracking_core.service.CO2ReadingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping(value = "/bySensor", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Response<List<Map<String, Object>>>> getAllReadingsBySensorId(
            @RequestParam("sensorId") Long sensorId, Authentication authentication) throws CO2ReadingException {
        User username = (User) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response.Builder<List<Map<String, Object>>>()
                        .message("Readings successfully retrieved")
                        .info(readingsService.getAllReadingsBySensorId(sensorId, username.getUsername()))
                        .build());
    }

    @GetMapping(value = "/byCity", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Response<List<Map<String, Object>>>> getAllReadingsByCityId(
            @RequestParam("cityId") Long cityId, Authentication authentication) throws CO2ReadingException {
        User username = (User) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response.Builder<List<Map<String, Object>>>()
                        .message("Readings successfully retrieved")
                        .info(readingsService.getAllReadingsByCityId(cityId, username.getUsername()))
                        .build());
    }

    @GetMapping(value = "/byDistrict", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Response<List<Map<String, Object>>>> getAllReadingsByDistrictId(
            @RequestParam("districtId") Long districtId, Authentication authentication) throws CO2ReadingException {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response.Builder<List<Map<String, Object>>>()
                        .message("Readings successfully retrieved")
                        .info(readingsService.getAllReadingsByDistrictId(districtId, user.getUsername()))
                        .build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<String>> insertReading(
            @RequestBody CO2ReadingDto readingDto, Authentication authentication) throws CO2ReadingException {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response.Builder<String>()
                        .message("New reading created successfully")
                        .info(String.format("CO2 reading ID: {%d}", readingsService.insertRecording(readingDto, user)))
                        .build());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<Integer>> updateReading(@RequestBody CO2ReadingDto dto, @RequestParam("id") Long id) throws CO2ReadingException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new Response.Builder<Integer>()
                        .message("Reading successfully updated")
                        .info(readingsService.updateReading(dto, id))
                        .build()
        );
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<Integer>> deleteReading(@RequestParam("id") Long id) throws CO2ReadingException {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new Response.Builder<Integer>()
                        .message("Reading successfully deleted")
                        .info(readingsService.deleteReading(id))
                        .build()
        );
    }
}
