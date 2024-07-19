package it.forcina.co2_tracking_core.persistence.entity;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class CO2Reading {
    private Long id;
    private Long sensorId;
    private String username;
    private float ppm;
    private ZonedDateTime recordDate;
}
