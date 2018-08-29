package com.kenan.workoutplanner.WorkoutPlanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenan.workoutplanner.WorkoutPlanner.helpers.AuthHelpers;
import com.kenan.workoutplanner.WorkoutPlanner.models.ApplicationUser;
import com.kenan.workoutplanner.WorkoutPlanner.models.ScheduledWorkout;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
public class WorkoutsTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthHelpers authHelpers;

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
        mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<ScheduledWorkout>>() {});
    }
}
