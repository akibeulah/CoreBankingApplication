package com.seaico.corebankingapplication.controller;

import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.repositories.ActivityRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ActivityController {
    final ActivityRepository activityRepository;

    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @GetMapping("/activity/{id}")
    public Activity fetchActivity(@PathVariable String id) {
        Optional<Activity> activity = activityRepository.findById(Long.valueOf(id));
        return activity.orElse(null);
    }

    @GetMapping("/activity")
    public List<Activity> fetchActivities() {
        return activityRepository.findAll();
    }

    @GetMapping("/activity/user")
    public List<Activity> fetchUserActivities(String userId) {
        return activityRepository.findAllByUserId(userId);
    }
}
