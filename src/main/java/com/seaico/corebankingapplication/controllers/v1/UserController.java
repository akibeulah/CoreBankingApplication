package com.seaico.corebankingapplication.controllers.v1;

import com.seaico.corebankingapplication.dto.GenericResponse;
import com.seaico.corebankingapplication.dto.authentication.ResetPasswordRequest;
import com.seaico.corebankingapplication.dto.user.NewPinRequest;
import com.seaico.corebankingapplication.dto.user.UserBasicProfileDto;
import com.seaico.corebankingapplication.dto.user.UserProfileDto;
import com.seaico.corebankingapplication.dto.user.UserProfileUpdateRequest;
import com.seaico.corebankingapplication.mapper.UserMapper;
import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.models.Pin;
import com.seaico.corebankingapplication.models.User;
import com.seaico.corebankingapplication.services.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.seaico.corebankingapplication.services.JwtService.getUsername;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final ActivityService activityService;
    private final PinService pinService;
    private final AuthenticationService authenticationService;

    @GetMapping("/user")
    public ResponseEntity<UserProfileDto> getAuthenticatedUserProfile() {
        User user = userService.fetchUserByUsername(getUsername()).orElseThrow();
        activityService.createActivity(new Activity(
                UUID.randomUUID().toString(),
                "User fetched own profile",
                LocalDateTime.now(),
                user
        ));
//        return ResponseEntity.ok(userMapper.userToUserProfileDto(user));
        return ResponseEntity.ok(UserMapper.userToUserProfileDto(user));
    }

    @PutMapping("/user")
    public ResponseEntity<UserBasicProfileDto> updateUserProfile(@Valid @RequestBody UserProfileUpdateRequest request) {
        User user = userService.fetchUserByUsername(getUsername()).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        if (request.hasValidChanges())
            throw new IllegalStateException("No valid changes found");

        if (request.getFullName() != null && !request.getFullName().isEmpty()) {
            user.setFullName(request.getFullName());
        }
        user.setDateUpdated(LocalDateTime.now());
        userService.saveUser(user);
        activityService.createActivity(new Activity(
                UUID.randomUUID().toString(),
                "User updated own profile\n ",
                LocalDateTime.now(),
                user
        ));

        return ResponseEntity.ok(UserMapper.userToUserBasicProfileDto(user));
    }

    @PostMapping("/create-new-pin")
    public ResponseEntity<GenericResponse<?>> createNewPin(@RequestBody NewPinRequest request) {
        User user = userService.fetchUserByUsername(getUsername()).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        List<Pin> pins = user.getPins();

        if (pins.isEmpty()) {
            if (request.getPassword() == null || request.getPassword().isEmpty())
                return ResponseEntity.badRequest().body(new GenericResponse<>(
                        400,
                        "Password is required"
                ));

            if (authenticationService.confirmPassword(user, request.getPassword()))
                pinService.createPin(
                        user,
                        request.getNewPin()
                );
            else
                return ResponseEntity.badRequest().body(new GenericResponse<>(
                        400,
                        "Password is incorrect"
                ));

        } else {
            if (PinService.checkPassword(request.getOldPin(), user.getPins().get(0).getHashedPinCode())) {
                pinService.createPin(
                        user,
                        request.getNewPin()
                );
            } else {
                throw new IllegalStateException("Password incorrect, cannot create pin!");
            }
        }

        return ResponseEntity.ok(new GenericResponse<>(
                200,
                "Pin successfully created"
        ));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<GenericResponse<?>> resetPassword(ResetPasswordRequest request) {
        User user = userService.fetchUserByUsername(JwtService.getUsername()).orElseThrow(() -> new EntityNotFoundException("User not Found!"));

        if (Objects.equals(request.getNewPassword(), request.getConfirmPassword())) {
            user.setPassword(request.getNewPassword());
            userService.saveUser(user);

            return ResponseEntity.ok(new GenericResponse<>(
                    200,
                    "Updated password",
                    null
            ));
        }

        return ResponseEntity.badRequest().body(new GenericResponse<>(
                404,
                "Failed to update password",
                null
        ));
    }
}
