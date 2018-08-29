package com.kenan.workoutplanner.WorkoutPlanner.api.errors;

import com.kenan.workoutplanner.WorkoutPlanner.api.ResponseData;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestError.class)
    protected ResponseEntity<Object> handleBadRequestError(BadRequestError badRequestError) {
        final ResponseData<Object> apiError = new ResponseData<>(null);

        apiError.setError(badRequestError.getMessage());
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}