package com.kenan.workoutplanner.WorkoutPlanner.repositories;

import com.kenan.workoutplanner.WorkoutPlanner.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkoutsRepository extends JpaRepository<Workout, Integer> {
    @Query("SELECT w FROM Workout w JOIN w.user u where u.id = :userId")
    List<Workout> findAllByUser(@Param("userId") Integer userId);
}
