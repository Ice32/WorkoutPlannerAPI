package com.kenan.workoutplanner.WorkoutPlanner.repositories;

import com.kenan.workoutplanner.WorkoutPlanner.models.ScheduledWorkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduledWorkoutsRepository extends JpaRepository<ScheduledWorkout, Long> {
    @Query("SELECT sw FROM ScheduledWorkout sw JOIN sw.workout w JOIN w.user u where u.id = :userId")
    List<ScheduledWorkout> findAllByUser(@Param("userId") Integer userId);

    @Query("SELECT sw FROM ScheduledWorkout sw JOIN sw.workout w JOIN w.user u where u.id = :userId and sw.done = :done")
    List<ScheduledWorkout> findBydone(@Param("done") boolean done, @Param("userId") Integer userId);
}
