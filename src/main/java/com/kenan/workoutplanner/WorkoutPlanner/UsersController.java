package com.kenan.workoutplanner.WorkoutPlanner;

import com.kenan.workoutplanner.WorkoutPlanner.errors.ApiErrors;
import com.kenan.workoutplanner.WorkoutPlanner.errors.BadRequestError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@CrossOrigin(origins = "*")
public class UsersController {
    private UsersRepository usersRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersController(final UsersRepository usersRepository, final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/users/registration")
    public ResponseData<Void> register(@RequestBody ApplicationUser user) throws BadRequestError {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
            usersRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestError(ApiErrors.DUPLICATE_EMAIL);
        }
        return new ResponseData<>((Void)null);
    }
}
