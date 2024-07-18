package it.forcina.co2_tracking_core.dto;

import it.forcina.co2_tracking_core.persistence.entity.City;
import it.forcina.co2_tracking_core.persistence.entity.District;
import lombok.Data;

@Data
public class CityDistrictDto {
    City city;
    District district;
}
