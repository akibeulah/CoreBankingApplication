package com.seaico.corebankingapplication.controller;

import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.repositories.ActivityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/activities")
public class ActivityController {
    final ActivityRepository activityRepository;

    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @GetMapping("/")
    public ResponseEntity<String> Home() {
        return ResponseEntity.ok("This is the activities controller home");
    }

    @GetMapping("/activity/{id}")
    public ResponseEntity<Activity> fetchActivity(@PathVariable String id) {
        Optional<Activity> activity = activityRepository.findById(id);
        return ResponseEntity.ok(activity.orElse(null));
    }

    @GetMapping("/activity")
    public ResponseEntity<List<Activity>> fetchActivities() {
        return ResponseEntity.ok(activityRepository.findAll());
    }

    @GetMapping("/activity/user")
    public ResponseEntity<List<Activity>> fetchUserActivities(String userId) {
        return ResponseEntity.ok(activityRepository.findAllByUserId(userId));
    }
}
