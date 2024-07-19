package it.forcina.co2_tracking_core.dto;

import java.time.ZonedDateTime;

public record CO2ReadingDto(Long sensorId,
                            float ppm,
                            ZonedDateTime recordDate) {}
