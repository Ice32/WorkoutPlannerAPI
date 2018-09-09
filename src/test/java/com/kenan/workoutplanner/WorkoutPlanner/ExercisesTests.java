package com.kenan.workoutplanner.WorkoutPlanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.kenan.workoutplanner.WorkoutPlanner.helpers.AuthHelpers;
import com.kenan.workoutplanner.WorkoutPlanner.models.ApplicationUser;
import com.kenan.workoutplanner.WorkoutPlanner.models.Exercise;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.ExercisesRepository;
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
public class ExercisesTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthHelpers authHelpers;

    @Autowired
    private ExercisesRepository exercisesRepository;

    @Test
    public void getUserExercises() throws Exception {
        // arrange
        ApplicationUser user = new ApplicationUser("user_workouts@mail.com", "password");
        authHelpers.storeUser(user);
        String jwtToken = authHelpers.generateJWTToken(user);

        // act && assert
        final MvcResult result = this.mvc.perform(
                get("/exercises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
        ).andExpect(status().isOk()).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Exercise>>() {});
    }

    @Test
    public void createExercise() throws Exception {
        // arrange
        ApplicationUser user = new ApplicationUser("user_exercisess@mail.com", "password");
        authHelpers.storeUser(user);
        String jwtToken = authHelpers.generateJWTToken(user);

        Exercise exercise = new Exercise("exercise");
        Gson gson = new Gson();
        String exerciseJson = gson.toJson(exercise);

        // act && assert
        final MvcResult result = this.mvc.perform(
                post("/exercises")
                        .content(exerciseJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
        ).andExpect(status().isOk()).andReturn();

        ObjectMapper mapper = new ObjectMapper();
        Exercise savedExercise = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Exercise>() {});

        Exercise insertedExercise = exercisesRepository.getOne(savedExercise.getId());
        assertEquals(insertedExercise.getUser().getEmail(), user.getEmail());
    }
}
