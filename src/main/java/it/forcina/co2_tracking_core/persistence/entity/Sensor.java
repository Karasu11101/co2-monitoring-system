package it.forcina.co2_tracking_core.persistence.entity;

import lombok.Data;

@Data
public class Sensor {
    private Long id;
    private City city;
}
