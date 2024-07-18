package it.forcina.co2_tracking_core.controller;

import it.forcina.co2_tracking_core.dto.DistrictDto;
import it.forcina.co2_tracking_core.dto.response.Response;
import it.forcina.co2_tracking_core.exception.DistrictException;
import it.forcina.co2_tracking_core.persistence.entity.District;
import it.forcina.co2_tracking_core.service.DistrictService;
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
@RequestMapping("/district")
public class DistrictController {
    private final DistrictService districtService;

    @Autowired
    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response<String>> insertDistrict(@RequestBody DistrictDto dto) throws DistrictException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new Response.Builder<String>()
                        .message("New district successfully created")
                        .info(String.format("District ID: {%d}", districtService.insertDistrict(dto)))
                        .build()
        );
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Response<List<District>>> getAllDistricts() throws DistrictException {
        return ResponseEntity.status(HttpStatus.OK).body(
                new Response.Builder<List<District>>()
                        .message("Districts successfully retrieved")
                        .info(districtService.getAllDistricts())
                        .build()
        );
    }
}
