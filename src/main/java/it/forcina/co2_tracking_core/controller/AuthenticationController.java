package it.forcina.co2_tracking_core.controller;

import it.forcina.co2_tracking_core.dto.LoginUserDto;
import it.forcina.co2_tracking_core.dto.RegisteredUserDto;
import it.forcina.co2_tracking_core.dto.response.LoginResponse;
import it.forcina.co2_tracking_core.persistence.entity.User;
import it.forcina.co2_tracking_core.service.AuthenticationService;
import it.forcina.co2_tracking_core.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisteredUserDto dto) {
        User registeredUser = authenticationService.signUp(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto dto) {
        User authenticatedUser = authenticationService.authenticate(dto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }
}
