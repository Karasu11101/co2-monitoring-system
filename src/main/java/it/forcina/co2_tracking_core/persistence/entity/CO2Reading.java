package it.forcina.co2_tracking_core.persistence.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CO2Reading {
    private Long id;
    private Sensor sensor;
    private User user;
    private float ppm;
    private LocalDateTime recordDate;
}
