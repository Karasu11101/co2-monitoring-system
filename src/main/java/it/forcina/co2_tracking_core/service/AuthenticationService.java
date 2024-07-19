package it.forcina.co2_tracking_core.service;

import it.forcina.co2_tracking_core.dto.LoginUserDto;
import it.forcina.co2_tracking_core.dto.RegisteredUserDto;
import it.forcina.co2_tracking_core.persistence.entity.User;
import it.forcina.co2_tracking_core.persistence.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    @Autowired
    public AuthenticationService(UserMapper mapper, PasswordEncoder passwordEncoder, AuthenticationManager authManager) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
    }

    public User signUp(RegisteredUserDto dto) {
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(dto.getRoles());

        mapper.insertNewUser(user);
        for(GrantedAuthority auth : user.getAuthorities()) {
            mapper.insertUserRole(auth.getAuthority(), user.getId());
        }

        return user;
    }

    public User authenticate(LoginUserDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(username, password);

        authManager.authenticate(
                userAuth
        );

        return mapper.getUserByUsername(dto.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
    }
}
