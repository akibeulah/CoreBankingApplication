package com.seaico.corebankingapplication.controller;

import com.seaico.corebankingapplication.models.Activity;
import com.seaico.corebankingapplication.repositories.ActivityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(ActivityController.class)
public class ActivityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private ActivityRepository activityRepository;

    private List<Activity> testActivitiesList;

    @BeforeEach
    public void setupMocks() {
        System.out.println("Starting setup mocks");
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
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8000/api/v1/activities/activity/" + testActivitiesList.get(1).getId())
                )
                .andDo(System.out::println)
                .andExpect(status().isOk())
                .andExpect(System.out::println);
    }

    @Test
    public void testFetchActivities() throws Exception {
        mockMvc.perform(get("/api/v1/activities/activity"))
                .andDo(System.out::println)
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchUserActivities() throws Exception {
        mockMvc.perform(get("/api/v1/activities/activity/user"))
                .andDo(System.out::println)
                .andExpect(status().isOk());
    }
}
