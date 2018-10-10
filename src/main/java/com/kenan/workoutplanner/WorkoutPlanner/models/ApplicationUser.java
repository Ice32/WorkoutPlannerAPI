package com.kenan.workoutplanner.WorkoutPlanner.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    @Column(unique=true, nullable = false)
    private String email;
    @Column(unique=true, nullable = false)
    private String password;
    private String refreshToken;

    @JsonBackReference
    @OneToMany(mappedBy = "user", targetEntity=Workout.class, fetch= FetchType.LAZY)
    private List<Workout> workouts = new ArrayList<>();

    public ApplicationUser() {}
    public ApplicationUser(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public ApplicationUser(ApplicationUser original) {
        this.id = original.id;
        this.fullName = new String(original.fullName != null ? original.fullName : "");
        this.email = new String(original.email != null ? original.email : "");
        this.password = new String(original.password != null ? original.password : "");
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(@NotNull String refreshToken) {
        Assert.notNull(refreshToken, "refresh token has to exist");
        this.refreshToken = refreshToken;
    }
}
