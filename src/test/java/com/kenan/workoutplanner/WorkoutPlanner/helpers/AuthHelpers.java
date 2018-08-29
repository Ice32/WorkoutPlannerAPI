package com.kenan.workoutplanner.WorkoutPlanner.helpers;

import com.auth0.jwt.JWT;
import com.kenan.workoutplanner.WorkoutPlanner.models.ApplicationUser;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.UsersRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.kenan.workoutplanner.WorkoutPlanner.api.security.SecurityConstants.EXPIRATION_TIME;
import static com.kenan.workoutplanner.WorkoutPlanner.api.security.SecurityConstants.SECRET;

@Component
public class AuthHelpers {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApplicationUser storeUser(@NotNull ApplicationUser user) {
        ApplicationUser userCopy = new ApplicationUser(user);
        userCopy.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return usersRepository.save(userCopy);
    }
    public String generateJWTToken(@NotNull ApplicationUser user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }
}
