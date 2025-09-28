package upm.data.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upm.AppSeeder;
import upm.data.repositories.ActivityRepository;
import upm.data.repositories.PlanRepository;
import upm.data.repositories.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PlanTest {
    private User user;
    private UserRepository userRepository;
    private ActivityRepository activityRepository;
    private PlanRepository planRepository;
    private Plan plan;

    @BeforeEach
    public void setUp() {
        AppSeeder appSeeder = new AppSeeder();
        appSeeder.seed();
        planRepository = appSeeder.getPlanRepository();
        this.userRepository = appSeeder.getUserRepository();
        assertTrue(userRepository.read(1).isPresent());
        this.user = userRepository.read(1).get();
        this.activityRepository = appSeeder.getActivityRepository();
        this.plan = new Plan("plan10", "descripcion10", LocalDateTime.of(2023, 12, 31, 23, 59), "Getafe", 1000);
        this.plan.setPropietary(this.user);
    }

    @Test
    public void testGetTotalTime() {
        assertTrue(this.planRepository.read(1).isPresent());
        assertEquals(420, this.planRepository.read(1).get().getTotalTime());
    }

    @Test
    public void testGetGenericTotalPrice() {
        assertTrue(this.planRepository.read(1).isPresent());
        assertEquals(BigDecimal.valueOf(34.59), this.planRepository.read(1).get().getGenericTotalPrice());
    }

    @Test
    public void testGetDiscountedTotalPrice() {
        assertTrue(this.planRepository.read(1).isPresent());
        assertTrue(this.userRepository.read(1).isPresent());
        assertEquals(BigDecimal.valueOf(29.09), this.planRepository.read(1).get().getDiscountedTotalPrice(this.userRepository.read(1).get().getAge()));
    }

    @Test
    public void testGetEndDatePlan() {
        assertTrue(this.planRepository.read(1).isPresent());
        assertEquals(LocalDateTime.of(2023, 12, 14, 19, 0).plusMinutes(420), this.planRepository.read(1).get().getEndDatePlan());
    }

    @Test
    public void testGetAverageRating() {
        assertTrue(this.planRepository.read(1).isPresent());
        assertEquals(3.2, this.planRepository.read(1).get().getAverageRating());
    }

    @Test
    public void testAddActivity() {
        assertTrue(this.activityRepository.read(4).isPresent());
        assertFalse(this.plan.getActivityList().contains(this.activityRepository.read(4).get()));
        this.plan.addActivity(this.activityRepository.read(4).get(), user);
        assertTrue(this.plan.getActivityList().contains(this.activityRepository.read(4).get()));
    }

    @Test
    public void testSubscribeUserToPlan() {
        assertTrue(this.userRepository.read(3).isPresent());
        assertTrue(this.userRepository.read(2).isPresent());
        assertTrue(this.userRepository.read(1).isPresent());

        this.plan.subscribeUserToPlan(this.userRepository.read(3).get());
        this.plan.subscribeUserToPlan(this.userRepository.read(2).get());
        this.plan.subscribeUserToPlan(this.userRepository.read(1).get());

        assertTrue(this.plan.getSubscribedUsers().contains(this.userRepository.read(2).get()));
        assertEquals(997, this.plan.getVacantPositions());
    }

    @Test
    public void testUnsubscribeUserToPlan() {
        assertTrue(this.userRepository.read(2).isPresent());
        assertTrue(this.planRepository.read(2).isPresent());

        User user1 = this.userRepository.read(2).get();

        assertTrue(this.planRepository.read(2).get().getSubscribedUsers().contains(user1));
        this.planRepository.read(2).get().unsubscribeUserToPlan(user1);
        assertFalse(this.planRepository.read(2).get().getSubscribedUsers().contains(user1));
    }
}
