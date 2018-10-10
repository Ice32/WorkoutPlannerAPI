package com.kenan.workoutplanner.WorkoutPlanner.api;

public class SimpleValueContainer<T> {
    public SimpleValueContainer() {}
    private T value;

    public SimpleValueContainer(T value) {
        this.value = value;
    }
    public T getValue() {
        return value;
    }

}
