package it.forcina.co2_tracking_core.dto;

import java.time.ZonedDateTime;

public record CO2ReadingDto(Long sensorId,
                            String sensorName,
                            Long districtId,
                            String districtName,
                            Long cityId,
                            String cityName,
                            float ppm,
                            ZonedDateTime recordDate) {}
