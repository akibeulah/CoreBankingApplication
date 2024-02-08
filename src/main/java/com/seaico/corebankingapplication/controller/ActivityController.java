package com.seaico.corebankingapplication.controller;

import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.repositories.ActivityRepository;
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
    public String Home() {
        return "This is the activities controller home";
    }

    @GetMapping("/activity/{id}")
    public Activity fetchActivity(@PathVariable String id) {
        Optional<Activity> activity = activityRepository.findById(id);
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
