package com.kenan.workoutplanner.WorkoutPlanner;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
public class AuthenticationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void registration() throws Exception {
        // arrange
        ApplicationUser user = new ApplicationUser("user@mail.com", "password");

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
        ApplicationUser user = new ApplicationUser("user@mail.com", "password");
        ApplicationUser loginData = new ApplicationUser(
                user.getEmail(),
                user.getPassword()
        );
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
        Gson gson = new Gson();
        String json = gson.toJson(loginData);

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
        ApplicationUser user = new ApplicationUser("user@mail.com", "password");
        ApplicationUser loginData = new ApplicationUser(
                user.getEmail(),
                user.getPassword() + "something"
        );
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
        Gson gson = new Gson();
        String json = gson.toJson(loginData);

        // act && assert
        this.mvc.perform(
                post("/users/login", user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().is4xxClientError()).andExpect(header().doesNotExist("Authorization"));
    }
}