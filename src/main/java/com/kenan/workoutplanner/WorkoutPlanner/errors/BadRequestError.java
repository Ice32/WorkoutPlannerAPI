package com.kenan.workoutplanner.WorkoutPlanner.errors;

public class BadRequestError extends Throwable {
    public BadRequestError(String message) {
        super(message);
    }
}
