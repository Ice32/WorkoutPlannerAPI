package com.kenan.workoutplanner.WorkoutPlanner.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(targetEntity=Exercise.class, fetch= FetchType.EAGER)
    private List<Exercise> exercises = new ArrayList<>();

    @OneToMany(mappedBy = "workout", targetEntity=ScheduledWorkout.class, fetch= FetchType.LAZY)
    private List<ScheduledWorkout> scheduledWorkouts = new ArrayList<>();

    @ManyToOne(fetch=FetchType.LAZY)
    private ApplicationUser user;

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

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
