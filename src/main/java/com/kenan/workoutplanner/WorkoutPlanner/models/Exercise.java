package com.kenan.workoutplanner.WorkoutPlanner.models;

import javax.persistence.*;

@Entity
@Table(name = "exercise")
public class Exercise {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private int sets;
    private int reps;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workoutId")
    private Workout workout;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
