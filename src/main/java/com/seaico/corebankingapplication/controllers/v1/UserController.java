package com.seaico.corebankingapplication.controllers.v1;

import com.seaico.corebankingapplication.models.User;
import com.seaico.corebankingapplication.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public ResponseEntity<User> getAuthenticatedUserProfile() {
        User user = userRepository.findByUsername(getUsername()).orElseThrow();
        return ResponseEntity.ok(user);
    }
}
