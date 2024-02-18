package com.seaico.corebankingapplication.services;

import com.seaico.corebankingapplication.config.JwtService;
import com.seaico.corebankingapplication.daos.AuthenticationRequest;
import com.seaico.corebankingapplication.daos.AuthenticationResponse;
import com.seaico.corebankingapplication.daos.RegisterRequest;
import com.seaico.corebankingapplication.enums.Role;
import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.models.User;
import com.seaico.corebankingapplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ActivityService activityService;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent() || userRepository.findByUsername(registerRequest.getUsername()).isPresent())
            throw new ResponseStatusException(HttpStatus.OK, "Username or Email already exists!");


        User user = User.builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .dateCreated(LocalDateTime.now())
                .dateUpdated(LocalDateTime.now())
                .role(Role.USER)
                .build();

        userRepository.save(user);
        activityService.createActivity(new Activity(
                UUID.randomUUID().toString(),
                "New User Created",
                LocalDateTime.now(),
                user
        ));

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.OK, "Invalid Credentials!");
        }

        User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow();
        activityService.createActivity(new Activity(
                UUID.randomUUID().toString(),
                "User Logged In",
                LocalDateTime.now(),
                user
        ));

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
