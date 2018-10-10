package com.kenan.workoutplanner.WorkoutPlanner.api.controllers;

import com.kenan.workoutplanner.WorkoutPlanner.api.ResponseData;
import com.kenan.workoutplanner.WorkoutPlanner.api.SimpleValueContainer;
import com.kenan.workoutplanner.WorkoutPlanner.api.errors.ApiErrors;
import com.kenan.workoutplanner.WorkoutPlanner.api.errors.BadRequestError;
import com.kenan.workoutplanner.WorkoutPlanner.api.security.JWTAuthenticationFilter;
import com.kenan.workoutplanner.WorkoutPlanner.api.security.SecurityConstants;
import com.kenan.workoutplanner.WorkoutPlanner.models.ApplicationUser;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.UsersRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class UsersController {
    private UsersRepository usersRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersController(final UsersRepository usersRepository, final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/users/registration")
    public ResponseData<Void> register(@RequestBody ApplicationUser user) throws BadRequestError {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
            usersRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestError(ApiErrors.DUPLICATE_EMAIL);
        }
        return new ResponseData<>(null);
    }
    @PostMapping("/users/refresh")
    public ResponseEntity<ResponseData<Void>> refreshToken(@RequestBody SimpleValueContainer<String> refreshToken) {
        ApplicationUser user = usersRepository.findByRefreshToken(refreshToken.getValue());
        if (user != null) {
            HttpHeaders responseHeaders = new HttpHeaders();
            String token = JWTAuthenticationFilter.generateToken(user.getEmail());
            responseHeaders.set(SecurityConstants.HEADER_STRING, token);

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .build();
        }

        return ResponseEntity.ok()
                .build();
    }
}
