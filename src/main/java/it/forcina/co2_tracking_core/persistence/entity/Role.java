package it.forcina.co2_tracking_core.persistence.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

@Data
@NoArgsConstructor
@JsonDeserialize(using = RoleDeserializer.class)
@Setter
public class Role {
    private String role;
    private User user;

    public Role(String role) {
        this.role = role;
    }
}

class RoleDeserializer extends JsonDeserializer<Role> {
    @Override
    public Role deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        return new Role(value);
    }
}
