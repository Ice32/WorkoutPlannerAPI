package com.kenan.workoutplanner.WorkoutPlanner.api.controllers;

import com.kenan.workoutplanner.WorkoutPlanner.models.ApplicationUser;
import com.kenan.workoutplanner.WorkoutPlanner.models.Exercise;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.ExercisesRepository;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ExercisesController {
    private UsersRepository usersRepository;
    private ExercisesRepository exercisesRepository;

    public ExercisesController(UsersRepository usersRepository, ExercisesRepository exercisesRepository) {
        this.usersRepository = usersRepository;
        this.exercisesRepository = exercisesRepository;
    }


    @GetMapping("/exercises")
    public List<Exercise> getCreatedExercises() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        ApplicationUser currentUser = usersRepository.findByEmail((String) authentication.getPrincipal());
        final List<Exercise> exercisesForUser = exercisesRepository.findExercisesForUser(currentUser.getId());
        return exercisesForUser;
    }

    @PostMapping("/exercises")
    public Exercise createExercise(@RequestBody Exercise exercise) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ApplicationUser currentUser = usersRepository.findByEmail((String) authentication.getPrincipal());
        exercise.setUser(currentUser);
        return exercisesRepository.save(exercise);
    }
}
