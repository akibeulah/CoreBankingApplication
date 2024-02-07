package com.seaico.corebankingapplication.repositories;

import com.seaico.corebankingapplication.models.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;

    @BeforeEach
    public void setUpMocks() {
        Activity activity1 = new Activity();
        activity1.setId("f47ac13b-58cc-4372-a567-0e12b2c3d129");
        activity1.setUserId("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        activity1.setDescription("Description");

        Activity activity2 = new Activity();
        activity2.setId("f47ac13b-58cc-4372-a567-0e12b2c3d239");
        activity2.setUserId("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        activity2.setDescription("Description");

        Activity activity3 = new Activity();
        activity3.setId("f47ac13b-58cc-4372-a567-0e12b2c3d479");
        activity3.setUserId("f47ac10b-58cc-4372-a567-0e02b2c3d478");
        activity3.setDescription("Description");
    }

    @Test
    public void testFindAllByUserId() {
        List<Activity> activities = activityRepository.findAllByUserId("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        assertThat(activities).hasSize(2);
        assertThat(activities.get(0).getUserId()).isEqualTo("f47ac10b-58cc-4372-a567-0e02b2c3d479");
    }
}
