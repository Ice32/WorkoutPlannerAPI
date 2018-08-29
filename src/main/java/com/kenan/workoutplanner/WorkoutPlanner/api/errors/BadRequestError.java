package com.kenan.workoutplanner.WorkoutPlanner.api.errors;

public class BadRequestError extends Throwable {
    public BadRequestError(String message) {
        super(message);
    }
}
