package com.seaico.corebankingapplication.controllers.v1;

import com.seaico.corebankingapplication.daos.AuthenticationRequest;
import com.seaico.corebankingapplication.daos.AuthenticationResponse;
import com.seaico.corebankingapplication.daos.RegisterRequest;
import com.seaico.corebankingapplication.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/public/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
