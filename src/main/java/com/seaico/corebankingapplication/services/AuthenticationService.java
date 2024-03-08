package com.seaico.corebankingapplication.services;

import com.seaico.corebankingapplication.dto.authentication.AuthenticationRequest;
import com.seaico.corebankingapplication.dto.authentication.AuthenticationResponse;
import com.seaico.corebankingapplication.dto.authentication.RegisterRequest;
import com.seaico.corebankingapplication.dto.user.UserAuthenticationPayload;
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

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ActivityService activityService;
    private final AccountService accountService;

    public AuthenticationResponse register(RegisterRequest registerRequest) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
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
        accountService.createAccount(user, "Initial Account", "NGN");

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
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
        UserAuthenticationPayload userAuthenticationPayload = new UserAuthenticationPayload(
                user.getFullName(),
                user.getUsername(),
                user.getEmail(),
                user.getDateCreated(),
                user.getRole(),
                user.getActivities(),
                user.getAccounts(),
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired(),
                user.isEnabled(),
                !Objects.equals(userService.getCurrentPin(user), "")
        );

        activityService.createActivity(new Activity(
                UUID.randomUUID().toString(),
                "User Logged In",
                LocalDateTime.now(),
                user
        ));

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .user(userAuthenticationPayload)
                .build();
    }

    public boolean confirmPassword(User user, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            password
                    )
            );

            return true;
        } catch (BadCredentialsException e) {
            return false;
        }
    }
}
