package it.forcina.co2_tracking_core.controller;

import it.forcina.co2_tracking_core.dto.SensorDto;
import it.forcina.co2_tracking_core.dto.response.Response;
import it.forcina.co2_tracking_core.exception.SensorException;
import it.forcina.co2_tracking_core.persistence.entity.Sensor;
import it.forcina.co2_tracking_core.service.SensorService;
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
@RequestMapping("/sensor")
public class SensorController {
    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Response<List<Sensor>>> getAllSensors() throws SensorException {
        return ResponseEntity.status(HttpStatus.OK).body(
                new Response.Builder<List<Sensor>>()
                        .message("Sensors successfully retrieved")
                        .info(sensorService.getAllSensors())
                        .build()
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<String>> insertSensor(@RequestBody SensorDto dto) throws SensorException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new Response.Builder<String>()
                        .message("New sensor successfully created")
                        .info(String.format("Sensor ID: {%d}", sensorService.insertSensor(dto)))
                        .build()
        );
    }
}
