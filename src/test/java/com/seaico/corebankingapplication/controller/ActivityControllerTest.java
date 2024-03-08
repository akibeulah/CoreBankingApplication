package com.seaico.corebankingapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seaico.corebankingapplication.controllers.v1.ActivityController;
import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.models.User;
import com.seaico.corebankingapplication.repositories.ActivityRepository;
import com.seaico.corebankingapplication.services.ActivityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(ActivityController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ActivityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private ActivityRepository activityRepository;
    @MockBean
    private ActivityService activityService;
    @Autowired
    private ObjectMapper objectMapper;
    private Activity activity;
    private List<Activity> testActivitiesList;

    @BeforeEach
    public void setupMocks() {
        System.out.println("Starting setup mocks");
        testActivitiesList = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            testActivitiesList.add(
                    new Activity(
                            UUID.randomUUID().toString(),
                            "Test Activity, delete if found!",
                            LocalDateTime.now(),
                            new User()
                    )
            );


            when(activityRepository.findById(testActivitiesList.get(i).getId())).thenReturn(Optional.of(testActivitiesList.get(i)));
            int finalI = i;
            when(activityRepository.findAllByUserId(
                    testActivitiesList.get(i).getUserId())
            ).thenReturn(
                    testActivitiesList.stream()
                            .filter(c -> Objects.equals(c.getUserId(), testActivitiesList.get(finalI).getUserId()))
                            .toList());
        }

        when(activityRepository.findAll()).thenReturn(testActivitiesList);
        activityRepository.saveAll(testActivitiesList);
        System.out.println("Setup mocks completed");
    }

    @AfterEach
    public void destroyMocks() {
        activityRepository.deleteAll(testActivitiesList);
    }

    @Test
    public void testFetchActivity() throws Exception {
        mockMvc.perform(get("/api/v1/activities/activity/{id}", testActivitiesList.get(1).getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchActivities() throws Exception {
        mockMvc.perform(get("/api/v1/activities/activity"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchUserActivities() throws Exception {
        mockMvc.perform(get("/api/v1/activities/activity/user/{userId}", "userId")) // Replace "userId" with a valid user ID
                .andExpect(status().isOk());
    }
}
