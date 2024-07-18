package it.forcina.co2_tracking_core.service;

import it.forcina.co2_tracking_core.dto.LoginUserDto;
import it.forcina.co2_tracking_core.dto.RegisteredUserDto;
import it.forcina.co2_tracking_core.persistence.entity.Role;
import it.forcina.co2_tracking_core.persistence.entity.User;
import it.forcina.co2_tracking_core.persistence.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
        List<SimpleGrantedAuthority> roles = dto.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRole())).toList();
        String username = dto.getUsername();
        String password = dto.getPassword();

        UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(username, password, roles);

        authManager.authenticate(
                userAuth
        );

        return mapper.getUserByUsername(dto.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
    }
}
