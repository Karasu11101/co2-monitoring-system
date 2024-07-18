package it.forcina.co2_tracking_core.dto;

public record SensorDto(String name,
                        Long districtId,
                        String districtName,
                        Long cityId,
                        String cityName) {}
