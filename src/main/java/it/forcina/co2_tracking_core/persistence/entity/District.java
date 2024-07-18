package it.forcina.co2_tracking_core.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class District {
    private Long id;
    private String name;
    private City city;
}
