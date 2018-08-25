package com.kenan.workoutplanner.WorkoutPlanner;


import javax.persistence.*;

@Entity
@Table(name = "users")
public class ApplicationUser {
    @Id
    @GeneratedValue
    private Integer id;
    private String fullName;
    @Column(unique=true)
    private String email;
    private String password;

    public ApplicationUser() {}
    public ApplicationUser(String email, String password) {
        this.email = email;
        this.password = password;
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
}
