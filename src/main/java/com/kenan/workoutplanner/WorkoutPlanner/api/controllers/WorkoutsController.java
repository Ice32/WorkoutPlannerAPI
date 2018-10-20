package com.kenan.workoutplanner.WorkoutPlanner.api.controllers;

import com.kenan.workoutplanner.WorkoutPlanner.models.ApplicationUser;
import com.kenan.workoutplanner.WorkoutPlanner.models.Workout;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.UsersRepository;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.WorkoutsRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public List<Workout> getCreatedWorkouts() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ApplicationUser currentUser = usersRepository.findByEmail((String) authentication.getPrincipal());
        return workoutsRepository.findAllByUser(currentUser.getId());
    }
    @PostMapping("/workouts")
    public Workout createWorkout(@RequestBody Workout workout) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ApplicationUser currentUser = usersRepository.findByEmail((String) authentication.getPrincipal());
        workout.setUser(currentUser);
        return workoutsRepository.save(workout);
    }
}
