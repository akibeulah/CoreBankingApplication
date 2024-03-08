package com.seaico.corebankingapplication.repositories;

import com.seaico.corebankingapplication.models.Activity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;
    private List<Activity> testActivitiesList;

    @BeforeEach
    public void setupMocks() {
        testActivitiesList = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            testActivitiesList.add(
                    new Activity(
                            UUID.randomUUID().toString(),
                            UUID.randomUUID().toString(),
                            "Test Activity, delete if found!",
                            LocalDateTime.now()
                    )
            );
        }

        activityRepository.saveAll(testActivitiesList);
    }

    @AfterEach
    public void destroyMocks() {
        activityRepository.deleteAll();
    }

    @Test
    public void testFindAllActivitiesForUser() {
        List<Activity> activities = activityRepository.findAllByUserId(testActivitiesList.get(1).getUserId());

        assertThat(activities).isEqualTo(
                testActivitiesList.stream().filter(c ->
                                Objects.equals(c.getUserId(), testActivitiesList.get(1).getUserId())
                        )
                        .toList()
        );
    }

    @Test
    public void testFindAllActivitiesForUserWithNoActivities() {
        List<Activity> activities = activityRepository.findAllByUserId("f47ac10b-58cc-4372-a567-0e02b2c3d479");

        assertThat(activities).isEmpty();
    }
}
