package com.seaico.corebankingapplication.controllers.v1;

import com.seaico.corebankingapplication.dto.GenericResponse;
import com.seaico.corebankingapplication.dto.authentication.AuthenticationRequest;
import com.seaico.corebankingapplication.dto.authentication.AuthenticationResponse;
import com.seaico.corebankingapplication.dto.authentication.RegisterRequest;
import com.seaico.corebankingapplication.dto.authentication.ResetPasswordRequest;
import com.seaico.corebankingapplication.models.User;
import com.seaico.corebankingapplication.services.AuthenticationService;
import com.seaico.corebankingapplication.services.JwtService;
import com.seaico.corebankingapplication.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

@RestController
@RequestMapping("/public/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
