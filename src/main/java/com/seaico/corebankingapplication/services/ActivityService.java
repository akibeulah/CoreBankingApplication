package com.seaico.corebankingapplication.services;

import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.models.User;
import com.seaico.corebankingapplication.repositories.ActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public void createActivity(Activity activity) {
        try {
            activityRepository.save(activity);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createActivity(String description, User user) {
        try {
            activityRepository.save(new Activity(
                    UUID.randomUUID().toString(),
                    description,
                    LocalDateTime.now(),
                    user
            ));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

//    public List<Activity> getAllActivitiesByUserId(String userId) {
//        return activityRepository.findAllByUserId(userId);
//    }

    public Optional<Activity> getActivity(String activityId) {
        return activityRepository.findById(activityId);
    }
}
