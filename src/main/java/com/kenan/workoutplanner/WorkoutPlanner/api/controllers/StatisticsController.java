package com.kenan.workoutplanner.WorkoutPlanner.api.controllers;

import com.kenan.workoutplanner.WorkoutPlanner.api.WeekStatistics;
import com.kenan.workoutplanner.WorkoutPlanner.models.ApplicationUser;
import com.kenan.workoutplanner.WorkoutPlanner.models.ScheduledWorkout;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.ScheduledWorkoutsRepository;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.UsersRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class StatisticsController {
    private UsersRepository usersRepository;
    private ScheduledWorkoutsRepository scheduledWorkoutsRepository;

    public StatisticsController(UsersRepository usersRepository, ScheduledWorkoutsRepository scheduledWorkoutsRepository) {
        this.usersRepository = usersRepository;
        this.scheduledWorkoutsRepository = scheduledWorkoutsRepository;
    }

    @GetMapping("/statistics")
    public Map<Long, WeekStatistics> getScheduledWorkouts(@AuthenticationPrincipal String userEmail) {
        ApplicationUser currentUser = usersRepository.findByEmail(userEmail);
        final List<ScheduledWorkout> scheduledWorkouts = scheduledWorkoutsRepository.findBydone(true, currentUser.getId());
        Instant userRegistrationTimestamp = Instant.ofEpochMilli(currentUser.getCreatedAt().getTime());
        Map<Long, WeekStatistics> values = scheduledWorkouts.stream().reduce(
                new HashMap<Long, WeekStatistics>(),
                (map, scheduledWorkout) -> {
                    Instant scheduledWorkoutTime = Instant.ofEpochMilli(scheduledWorkout.getTime().getTime());

                    LocalDateTime startDate = LocalDateTime.ofInstant(userRegistrationTimestamp, ZoneId.of("UTC"));
                    LocalDateTime endDate = LocalDateTime.ofInstant(scheduledWorkoutTime, ZoneId.of("UTC"));

                    long workoutWeek = ChronoUnit.WEEKS.between(startDate, endDate);
                    map.putIfAbsent(workoutWeek, new WeekStatistics(0, 0));
                    WeekStatistics currentValueForWeek = map.get(workoutWeek);
                    WeekStatistics newValueForWeek = new WeekStatistics(
                            currentValueForWeek.getWorkouts() + 1,
                            currentValueForWeek.getExercises() + scheduledWorkout.getWorkout().getExercises().size()
                    );
                    map.put(workoutWeek, newValueForWeek);
                    return map;
                },
                (integerIntegerHashMap, integerIntegerHashMap2) -> null);

        return values;
    }
}
