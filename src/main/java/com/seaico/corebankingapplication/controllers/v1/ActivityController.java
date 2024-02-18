package com.seaico.corebankingapplication.controllers.v1;

import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.repositories.ActivityRepository;
import com.seaico.corebankingapplication.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/activities/")
public class ActivityController {
    final ActivityRepository activityRepository;
    final UserRepository userRepository;

    public ActivityController(ActivityRepository activityRepository, UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public ResponseEntity<String> Home() {
        return ResponseEntity.ok("This is the activities controller home");
    }

    @GetMapping("activity/{id}")
    public ResponseEntity<Activity> fetchActivity(@PathVariable String id) {
        Optional<Activity> activity = activityRepository.findById(id);
        return ResponseEntity.ok(activity.orElse(null));
    }

    @GetMapping("get-all")
    public ResponseEntity<List<Activity>> fetchActivities() {
        return ResponseEntity.ok(activityRepository.findAll());
    }

    @GetMapping("activity/user/{userId}")
    public ResponseEntity<List<Activity>> fetchUserActivities(@PathVariable String userId) {
        return ResponseEntity.ok(activityRepository.findAllByUserId(userRepository.findById(userId).orElseThrow()));
    }
}
