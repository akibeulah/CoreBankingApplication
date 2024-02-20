package com.seaico.corebankingapplication.controllers.v1;

import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.models.User;
import com.seaico.corebankingapplication.repositories.UserRepository;
import com.seaico.corebankingapplication.services.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController extends BaseController {
    private final UserRepository userRepository;
    private final ActivityService activityService;

    @GetMapping("/user")
    public ResponseEntity<User> getAuthenticatedUserProfile() {
        User user = userRepository.findByUsername(getUsername()).orElseThrow();
        activityService.createActivity(new Activity(
                UUID.randomUUID().toString(),
                "User fetched own profile",
                LocalDateTime.now(),
                user
        ));
        return ResponseEntity.ok(user);
    }
}
