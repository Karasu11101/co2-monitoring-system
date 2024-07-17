package it.forcina.co2_tracking_core.dto.response;

public record LoginResponse(String jwtToken, Long expiresIn) {
}
