package it.forcina.co2_tracking_core.persistence.entity;

import lombok.Data;

@Data
public class District {
    private Long id;
    private String name;
    private City city;
}
