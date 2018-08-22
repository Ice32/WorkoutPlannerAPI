package com.kenan.workoutplanner.WorkoutPlanner;

import com.kenan.workoutplanner.WorkoutPlanner.errors.BadRequestError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class WorkoutsController {

    @GetMapping("/workouts")
    public void getCreatedWorkouts() throws BadRequestError {
        throw new BadRequestError("Teeest");
//        ApplicationUser user = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
