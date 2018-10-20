package com.kenan.workoutplanner.WorkoutPlanner.api.controllers;

import com.kenan.workoutplanner.WorkoutPlanner.api.errors.ApiErrors;
import com.kenan.workoutplanner.WorkoutPlanner.api.errors.BadRequestError;
import com.kenan.workoutplanner.WorkoutPlanner.models.ApplicationUser;
import com.kenan.workoutplanner.WorkoutPlanner.models.ScheduledWorkout;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.ScheduledWorkoutsRepository;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ScheduledWorkoutsController {
    private ScheduledWorkoutsRepository scheduledWorkoutsRepository;
    private UsersRepository usersRepository;
    private final Logger logger = LoggerFactory.getLogger(ScheduledWorkoutsController.class);

    public ScheduledWorkoutsController(ScheduledWorkoutsRepository scheduledWorkoutsRepository, UsersRepository usersRepository) {
        this.scheduledWorkoutsRepository = scheduledWorkoutsRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/scheduled_workouts")
    public List<ScheduledWorkout> getScheduledWorkouts() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        ApplicationUser currentUser = usersRepository.findByEmail((String) authentication.getPrincipal());
        final List<ScheduledWorkout> scheduledWorkouts = scheduledWorkoutsRepository.findAllByUser(currentUser.getId());
        return scheduledWorkouts;
    }

    @GetMapping("/scheduled_workouts/{finishedStatus}")
    public List<ScheduledWorkout> getDoneScheduledWorkouts(@PathVariable("finishedStatus") String finishedStatus) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        ApplicationUser currentUser = usersRepository.findByEmail((String) authentication.getPrincipal());
        return scheduledWorkoutsRepository.findBydone(finishedStatus.equals("done"), currentUser.getId());
    }

    @PostMapping("/scheduled_workouts")
    public ScheduledWorkout scheduleWorkout(@RequestBody ScheduledWorkout scheduledWorkout) {
        return scheduledWorkoutsRepository.save(scheduledWorkout);
    }

    @PostMapping("/scheduled_workouts/{id}/finish")
    public ScheduledWorkout scheduleWorkout(@PathVariable("id") Long scheduledWorkoutId) throws BadRequestError {
        final Optional<ScheduledWorkout> scheduledWorkoutOptional = scheduledWorkoutsRepository.findById(scheduledWorkoutId);
        if (scheduledWorkoutOptional.isPresent()) {
            ScheduledWorkout scheduledWorkout = scheduledWorkoutOptional.get();
            scheduledWorkout.setDone(true);
            scheduledWorkoutsRepository.save(scheduledWorkout);
            return scheduledWorkout;
        } else {
            throw new BadRequestError(ApiErrors.SCHEDULED_WORKOUT_NOT_FOUND);
        }
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(Exception e) {
        logger.warn("Returning HTTP 400 Bad Request", e);
    }
}