package com.seaico.corebankingapplication.controller;

import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.repositories.ActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(ActivityController.class)
@WebMvcTest(ActivityController.class)
@ComponentScan(basePackages = {"com.seaico.corebankingapplication.controllers"})
public class ActivityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private ActivityRepository activityRepository;
    @InjectMocks
    private ActivityController activityController;

    @BeforeEach
    public void setupMocks() {
        Activity activity = new Activity();
        activity.setId("f47ac13b-58cc-4372-a567-0e12b2c3d479");
        activity.setUserId("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        activity.setDescription("Description");

//        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
    }

    @Test
    public void testFetchActivity() throws Exception {
        mockMvc.perform(get("/activity/1"))
                .andExpect(status().isOk())
                .andExpect(System.out::println);
    }

    @Test
    public void testFetchActivities() throws Exception {
        mockMvc.perform(get("/activity"))
                .andExpect(status().isOk())
                .andExpect(System.out::println);
    }

    @Test
    public void testFetchUserActivities() throws Exception {
        mockMvc.perform(get("/activity/user"))
                .andExpect(status().isOk())
                .andExpect(System.out::println);
    }
}
