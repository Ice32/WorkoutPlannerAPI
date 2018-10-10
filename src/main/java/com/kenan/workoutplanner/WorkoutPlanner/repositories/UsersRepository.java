package com.kenan.workoutplanner.WorkoutPlanner.repositories;

import com.kenan.workoutplanner.WorkoutPlanner.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<ApplicationUser, Integer> {
    ApplicationUser findByEmail(String email);
    ApplicationUser findByRefreshToken(String refreshToken);
}
