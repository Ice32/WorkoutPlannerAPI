package com.kenan.workoutplanner.WorkoutPlanner.api.controllers;

import com.kenan.workoutplanner.WorkoutPlanner.models.ApplicationUser;
import com.kenan.workoutplanner.WorkoutPlanner.models.Workout;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.UsersRepository;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.WorkoutsRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class WorkoutsController {
    private WorkoutsRepository workoutsRepository;
    private UsersRepository usersRepository;

    public WorkoutsController(WorkoutsRepository workoutsRepository, UsersRepository usersRepository) {
        this.workoutsRepository = workoutsRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/workouts")
    public List<Workout> getCreatedWorkouts(@AuthenticationPrincipal String userEmail) {
        ApplicationUser currentUser = usersRepository.findByEmail(userEmail);
        return workoutsRepository.findAllByUser(currentUser.getId());
    }
    @PostMapping("/workouts")
    public Workout createWorkout(@RequestBody Workout workout, @AuthenticationPrincipal String userEmail) {
        ApplicationUser currentUser = usersRepository.findByEmail(userEmail);
        workout.setUser(currentUser);
        return workoutsRepository.save(workout);
    }
}
