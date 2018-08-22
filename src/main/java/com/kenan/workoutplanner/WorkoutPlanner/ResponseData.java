package com.kenan.workoutplanner.WorkoutPlanner;

public class ResponseData<T> {
    public T data;
    public String error = null;
    public Integer status = 200;

    public ResponseData(T data) {
        this.data = data;
    }
    public void setError(String error) {
        this.error = error;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
}
