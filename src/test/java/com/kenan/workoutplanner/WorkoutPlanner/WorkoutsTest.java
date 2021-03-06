package com.kenan.workoutplanner.WorkoutPlanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kenan.workoutplanner.WorkoutPlanner.helpers.AuthHelpers;
import com.kenan.workoutplanner.WorkoutPlanner.models.ApplicationUser;
import com.kenan.workoutplanner.WorkoutPlanner.models.Workout;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.WorkoutsRepository;
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
public class WorkoutsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthHelpers authHelpers;

    @Autowired
    private WorkoutsRepository workoutsRepository;

    @Test
    public void getUserWorkouts() throws Exception {
        // arrange
        ApplicationUser user = new ApplicationUser("user_workouts@mail.com", "password");
        authHelpers.storeUser(user);
        String jwtToken = authHelpers.generateJWTToken(user);

        // act && assert
        final MvcResult result = this.mvc.perform(
                get("/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
        ).andExpect(status().isOk()).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Workout>>() {});
    }

    @Test
    public void createWorkout() throws Exception {
        // arrange
        ApplicationUser user = new ApplicationUser("user_workouts@mail.com", "password");
        authHelpers.storeUser(user);
        String jwtToken = authHelpers.generateJWTToken(user);

        Workout workout = new Workout("workout");
        Gson gson = new Gson();
        String workoutJson = gson.toJson(workout);

        // act && assert
        final MvcResult result = this.mvc.perform(
                post("/workouts")
                        .content(workoutJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
        ).andExpect(status().isOk()).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        Workout savedWorkout = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Workout>() {});

        Workout insertedWorkout = workoutsRepository.getOne(savedWorkout.getId());
        assertEquals(insertedWorkout.getUser().getEmail(), user.getEmail());
    }
}
