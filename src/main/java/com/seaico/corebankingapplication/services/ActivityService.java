package com.seaico.corebankingapplication.services;

import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.repositories.ActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public Activity createActivity(Activity activity) {
        activityRepository.save(activity);
        return activity;
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public List<Activity> getAllActivitiesByUserId(String userId) {
        return activityRepository.findAllByUserId(userId);
    }

    public Optional<Activity> getActivity(String activityId) {
        return activityRepository.findById(activityId);
    }
}
