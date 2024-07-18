package it.forcina.co2_tracking_core.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class City {
    private Long id;
    private String name;
}
