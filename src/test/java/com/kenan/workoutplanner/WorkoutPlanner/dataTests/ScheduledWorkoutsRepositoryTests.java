package com.kenan.workoutplanner.WorkoutPlanner.dataTests;

import com.kenan.workoutplanner.WorkoutPlanner.models.ApplicationUser;
import com.kenan.workoutplanner.WorkoutPlanner.models.ScheduledWorkout;
import com.kenan.workoutplanner.WorkoutPlanner.models.Workout;
import com.kenan.workoutplanner.WorkoutPlanner.repositories.ScheduledWorkoutsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
public class ScheduledWorkoutsRepositoryTests {
    private Date date1;

    @BeforeEach
    void setup() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1988);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        date1 = cal.getTime();
    }

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ScheduledWorkoutsRepository repository;

    @Test
    @DisplayName("findAllDoneScheduledWorkouts_oneDoneAndOneNotDoneWorkoutExists_returnsOneWorkout")
    public void testFind() throws Exception {
        // arrange
        ApplicationUser user = new ApplicationUser("neki@mail.com", "");
        Workout workout = new Workout();
        workout.setUser(user);

        ScheduledWorkout doneScheduledWorkout = new ScheduledWorkout(workout, new Date());
        doneScheduledWorkout.setDone(true);

        ScheduledWorkout notDoneScheduledWorkout = new ScheduledWorkout(workout, date1);
        notDoneScheduledWorkout.setDone(false);

        entityManager.persist(user);
        entityManager.persist(workout);
        entityManager.persist(doneScheduledWorkout);
        entityManager.persist(notDoneScheduledWorkout);


        // act
        List<ScheduledWorkout> scheduledWorkouts = repository.findBydone(true, user.getId());

        assertThat(scheduledWorkouts).hasSize(1);
        ScheduledWorkout foundDoneWorkout = scheduledWorkouts.get(0);
        assertThat(foundDoneWorkout.getTime()).isEqualTo(doneScheduledWorkout.getTime());
    }
}
