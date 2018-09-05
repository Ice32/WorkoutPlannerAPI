package com.kenan.workoutplanner.WorkoutPlanner.repositories;

import com.kenan.workoutplanner.WorkoutPlanner.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExercisesRepository  extends JpaRepository<Exercise, Integer> {
    @Query("SELECT e FROM Exercise e JOIN e.workout w JOIN w.user u where u.id = :userId")
    public List<Exercise> findExercisesForUser(@Param("userId") Integer userId);
}
