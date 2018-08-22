package com.kenan.workoutplanner.WorkoutPlanner;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<ApplicationUser, Integer> {
    ApplicationUser findByEmail(String email);
}
