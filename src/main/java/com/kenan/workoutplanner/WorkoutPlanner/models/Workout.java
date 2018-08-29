package com.kenan.workoutplanner.WorkoutPlanner.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Workout {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToMany(targetEntity=Exercise.class, fetch= FetchType.EAGER)
    private List<Exercise> exercises = new ArrayList<>();

    @OneToMany(mappedBy = "workout", targetEntity=ScheduledWorkout.class, fetch= FetchType.LAZY)
    private List<ScheduledWorkout> scheduledWorkouts = new ArrayList<>();

    public Workout() {}
    public Workout(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
