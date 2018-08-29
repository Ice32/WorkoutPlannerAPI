package com.kenan.workoutplanner.WorkoutPlanner.repositories;

import com.kenan.workoutplanner.WorkoutPlanner.models.ScheduledWorkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledWorkoutsRepository extends JpaRepository<ScheduledWorkout, Long> {
}
