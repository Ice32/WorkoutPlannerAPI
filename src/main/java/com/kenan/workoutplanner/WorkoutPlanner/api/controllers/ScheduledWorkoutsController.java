package com.kenan.workoutplanner.WorkoutPlanner.api.controllers;

import com.kenan.workoutplanner.WorkoutPlanner.models.ScheduledWorkout;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.ScheduledWorkoutsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ScheduledWorkoutsController {
    private ScheduledWorkoutsRepository scheduledWorkoutsRepository;
    private final Logger logger = LoggerFactory.getLogger(ScheduledWorkoutsController.class);

    public ScheduledWorkoutsController(ScheduledWorkoutsRepository scheduledWorkoutsRepository) {
        this.scheduledWorkoutsRepository = scheduledWorkoutsRepository;
    }

    @GetMapping("/scheduled_workouts")
    public List<ScheduledWorkout> getScheduledWorkouts() {
        final List<ScheduledWorkout> scheduledWorkouts = scheduledWorkoutsRepository.findAll();
        return scheduledWorkouts;
    }

    @GetMapping("/scheduled_workouts/done")
    public List<ScheduledWorkout> getDoneScheduledWorkouts() {
        final List<ScheduledWorkout> scheduledWorkouts = scheduledWorkoutsRepository.findBydone(true);
        return scheduledWorkouts;
    }
    @PostMapping("/scheduled_workouts")
    public ScheduledWorkout scheduleWorkout(@RequestBody ScheduledWorkout scheduledWorkout) {
        return scheduledWorkoutsRepository.save(scheduledWorkout);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(Exception e) {
        logger.warn("Returning HTTP 400 Bad Request", e);
    }
}