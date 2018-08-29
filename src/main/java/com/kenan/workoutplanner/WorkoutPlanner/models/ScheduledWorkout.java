package com.kenan.workoutplanner.WorkoutPlanner.models;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ScheduledWorkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    private Workout workout;

    @NotNull
    private Date time;

    public ScheduledWorkout() {}
    public ScheduledWorkout(@NotNull Workout workout, @NotNull Date time) {
        this.workout = workout;
        this.time = time;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
