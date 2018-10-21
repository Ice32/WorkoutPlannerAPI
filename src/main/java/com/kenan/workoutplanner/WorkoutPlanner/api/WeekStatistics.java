package com.kenan.workoutplanner.WorkoutPlanner.api;

public class WeekStatistics {
    private Integer workouts;
    private Integer exercises;

    public WeekStatistics() {
    }
    public WeekStatistics(Integer workouts, Integer exercises) {
        this.workouts = workouts;
        this.exercises = exercises;
    }

    public void setWorkouts(Integer workouts) {
        this.workouts = workouts;
    }
    public Integer getWorkouts() {
        return workouts;
    }

    public void setExercises(Integer exercises) {
        this.exercises = exercises;
    }
    public Integer getExercises() {
        return exercises;
    }
}
