package com.seaico.corebankingapplication.services;

import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.repositories.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {
    private ActivityService underTest;
    @Mock
    private ActivityRepository activityRepository;
    private List<Activity> testActivitiesList;


    @BeforeEach
    public void setUp() {
        underTest = new ActivityService(activityRepository);

        testActivitiesList = new ArrayList<>();
        String userId = UUID.randomUUID().toString();

        for (int i = 0; i < 12; i++) { // 12 times
            if (i % 4 == 0)
                userId = UUID.randomUUID().toString();

            testActivitiesList.add(
                    new Activity(
                            UUID.randomUUID().toString(),
                            userId,
                            "Test Activity, delete if found!",
                            LocalDateTime.now()
                    )
            );
        }

        activityRepository.saveAll(testActivitiesList);
    }

    @Test
    void testFetchAllActivities() {
        underTest.getAllActivities();
        verify(activityRepository).findAll();
    }

    @Test
    void testCreateActivityWithAllDataFilled() {
        Activity newActivity = new Activity(
                "f47ac10b-58cc-4372-a567-0e02b2c3d479",
                "f47ac10b-58cc-4372-a567-0e02b2c3d479",
                "Test Activity, delete if found!",
                LocalDateTime.now()
        );

        underTest.createActivity(newActivity);

        ArgumentCaptor<Activity> activityArgumentCaptor = ArgumentCaptor.forClass(Activity.class);
        verify(activityRepository).save(activityArgumentCaptor.capture());

        Activity capturedActivity = activityArgumentCaptor.getValue();
        assertEquals(newActivity, capturedActivity);
    }

    @Test
    void testGetAllUserActivitiesByUserId() {
        underTest.getAllActivitiesByUserId(testActivitiesList.get(1).getUserId());
        verify(activityRepository).findAllByUserId(testActivitiesList.get(1).getUserId());
    }

    @Test
    void testGetActivityById() {
        underTest.getActivity(testActivitiesList.get(1).getId());
        verify(activityRepository).findById(testActivitiesList.get(1).getId());
    }
}