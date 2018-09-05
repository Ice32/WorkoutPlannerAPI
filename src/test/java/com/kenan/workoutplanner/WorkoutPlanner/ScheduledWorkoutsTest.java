package com.kenan.workoutplanner.WorkoutPlanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.kenan.workoutplanner.WorkoutPlanner.helpers.AuthHelpers;
import com.kenan.workoutplanner.WorkoutPlanner.models.ApplicationUser;
import com.kenan.workoutplanner.WorkoutPlanner.models.ScheduledWorkout;
import com.kenan.workoutplanner.WorkoutPlanner.models.Workout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
public class ScheduledWorkoutsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthHelpers authHelpers;
    private ApplicationUser user;
    private String jwtToken;

    @BeforeEach
    public void beforeEach() {
        user = new ApplicationUser("user_workouts@mail.com", "password");
        authHelpers.storeUser(user);
        jwtToken = authHelpers.generateJWTToken(user);
    }

    @Test
    public void getUserScheduledWorkouts() throws Exception {
        // act && assert
        final MvcResult result = this.mvc.perform(
                get("/scheduled_workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
        ).andExpect(status().isOk()).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<ScheduledWorkout>>() {});
    }

    @Test
    public void scheduleWorkout() throws Exception {
        // arrange
        final Workout workout = new Workout("Workout for schedule");
        final Date time = new Date();
        final ScheduledWorkout scheduledWorkout = new ScheduledWorkout(workout, time);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, (JsonSerializer<Date>) (date, type, jsonSerializationContext) -> new JsonPrimitive(date.getTime()))
                .create();
        String scheduledWorkoutJson = gson.toJson(scheduledWorkout);

        // act && assert
        final MvcResult result = this.mvc.perform(
                post("/scheduled_workouts")
                        .content(scheduledWorkoutJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
        ).andExpect(status().isOk()).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        ScheduledWorkout savedScheduledWorkout = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<ScheduledWorkout>() {});

        assertEquals(savedScheduledWorkout.getWorkout().getName(), workout.getName());
        assertEquals(savedScheduledWorkout.getTime(), time);
    }
}
