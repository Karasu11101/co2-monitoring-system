package it.forcina.co2_tracking_core.dto;

import it.forcina.co2_tracking_core.persistence.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class LoginUserDto {
    private String username;
    private String password;
    private Set<Role> roles;
}
