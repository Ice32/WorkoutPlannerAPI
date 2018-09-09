package com.kenan.workoutplanner.WorkoutPlanner.repositories;

import com.kenan.workoutplanner.WorkoutPlanner.models.ScheduledWorkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduledWorkoutsRepository extends JpaRepository<ScheduledWorkout, Long> {

    public List<ScheduledWorkout> findBydone(@Param("done") boolean done);
}
