package com.kenan.workoutplanner.WorkoutPlanner;

import com.google.gson.Gson;
import com.kenan.workoutplanner.WorkoutPlanner.helpers.AuthHelpers;
import com.kenan.workoutplanner.WorkoutPlanner.models.ApplicationUser;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
public class AuthenticationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthHelpers authHelpers;

    @Test
    public void registration() throws Exception {
        // arrange
        ApplicationUser user = new ApplicationUser("user_for_registration@mail.com", "password");

        Gson gson = new Gson();
        String json = gson.toJson(user);

        // act
        this.mvc.perform(
                post("/users/registration", user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk());

        // assert
        ApplicationUser savedUser = usersRepository.findByEmail(user.getEmail());
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualToIgnoringCase(user.getEmail());
    }

    @Test
    public void login() throws Exception {
        // arrange
        ApplicationUser user = new ApplicationUser("user_for_login@mail.com", "password");
        authHelpers.storeUser(user);
        Gson gson = new Gson();
        String json = gson.toJson(user);

        // act && assert
        this.mvc.perform(
                post("/users/login", user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk()).andExpect(header().exists("Authorization"));
    }

    @Test
    public void loginWithIncorrectPassword() throws Exception {
        // arrange
        ApplicationUser user = new ApplicationUser("user_for_incorrect@mail.com", "password");
        authHelpers.storeUser(user);
        user.setPassword("incorrect password");
        Gson gson = new Gson();
        String json = gson.toJson(user);

        // act && assert
        this.mvc.perform(
                post("/users/login", user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().is4xxClientError()).andExpect(header().doesNotExist("Authorization"));
    }
}