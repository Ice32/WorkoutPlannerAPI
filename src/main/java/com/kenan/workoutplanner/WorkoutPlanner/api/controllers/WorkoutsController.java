package com.kenan.workoutplanner.WorkoutPlanner.api.controllers;

import com.kenan.workoutplanner.WorkoutPlanner.models.ApplicationUser;
import com.kenan.workoutplanner.WorkoutPlanner.models.ScheduledWorkout;
import com.kenan.workoutplanner.WorkoutPlanner.models.Workout;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.ScheduledWorkoutsRepository;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.UsersRepository;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.WorkoutsRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class WorkoutsController {
    private WorkoutsRepository workoutsRepository;
    private UsersRepository usersRepository;
    private ScheduledWorkoutsRepository scheduledWorkoutsRepository;

    public WorkoutsController(WorkoutsRepository workoutsRepository, UsersRepository usersRepository, ScheduledWorkoutsRepository scheduledWorkoutsRepository) {
        this.workoutsRepository = workoutsRepository;
        this.usersRepository = usersRepository;
        this.scheduledWorkoutsRepository = scheduledWorkoutsRepository;
    }

    @GetMapping("/workouts")
    public List<ScheduledWorkout> getCreatedWorkouts() {
        final Workout some_workout = workoutsRepository.save(new Workout("Some workout"));
        scheduledWorkoutsRepository.save(new ScheduledWorkout(some_workout, new Date()));
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal();
        ApplicationUser currentUser = usersRepository.findByEmail((String) authentication.getPrincipal());
        return scheduledWorkoutsRepository.findAll();
//        ApplicationUser user = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
