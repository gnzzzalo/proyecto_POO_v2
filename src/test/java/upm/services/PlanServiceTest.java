package upm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upm.AppSeeder;
import upm.data.models.InvalidAttributeException;
import upm.data.models.Plan;
import upm.data.models.User;
import upm.data.repositories.ActivityRepository;
import upm.data.repositories.PlanRepository;
import upm.data.repositories.UserRepository;
import upm.data.repositories.repositories_map.PlanRepositoryMap;
import upm.services.exceptions.NotFoundException;
import upm.services.exceptions.NotSubscribedException;
import upm.services.exceptions.OverlapException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlanServiceTest {
    private UserRepository userRepository;
    private PlanRepository planRepository;
    private ActivityRepository activityRepository;
    private PlanService planService;

    @BeforeEach
    public void setUp() {
        AppSeeder appSeeder = new AppSeeder();
        appSeeder.seed();
        this.userRepository = appSeeder.getUserRepository();
        this.planRepository = appSeeder.getPlanRepository();
        this.activityRepository = appSeeder.getActivityRepository();
        this.planService = new PlanService(this.planRepository, this.userRepository, this.activityRepository);
    }

    @Test
    public void testDeleteException() {
        assertThrows(InvalidAttributeException.class, () -> this.planService.delete(1, this.userRepository.read(1).get()));
    }

    @Test
    public void testAddActivityException() {
        PlanService planService1 = new PlanService(new PlanRepositoryMap(), userRepository, activityRepository);
        assertTrue(this.userRepository.read(1).isPresent());
        planService1.create(new Plan("plan1", "salir de cañas", LocalDateTime.of(2023, 12, 14, 19, 0), "Madrid", -1), this.userRepository.read(1).get());
        try {
            this.planService.addActivity(this.userRepository.read(1).get(), 7, 1);
        } catch (NotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        try {
            this.planService.addActivity(this.userRepository.read(1).get(), 2, 7);
        } catch (NotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        try {
            this.planService.addActivity(this.userRepository.read(1).get(), 2, 1);
        } catch (NotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        try {
            planService1.addActivity(this.userRepository.read(1).get(), 2, 1);
        } catch (NotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        assertThrows(InvalidAttributeException.class, () -> this.planService.addActivity(this.userRepository.read(1).get(), 2, 3));
    }

    @Test
    public void testGetTotalTimeException() {
        assertThrows(NotFoundException.class, () -> this.planService.getTotalTime(7));
    }

    @Test
    public void testGetUserAndGenericPrice() {
        assertEquals("28.59€ (33.59€ sin descuento)", this.planService.getUserAndGenericPrice(this.userRepository.read(1).get(), 6));
        assertEquals("33.59€", this.planService.getUserAndGenericPrice(this.userRepository.read(2).get(), 6));
    }

    @Test
    public void testGetUserAndGenericPriceExceptions() {
        assertThrows(NotFoundException.class, () -> this.planService.getUserAndGenericPrice(this.userRepository.read(2).get(), 7));
        assertThrows(NotSubscribedException.class, () -> this.planService.getUserAndGenericPrice(this.userRepository.read(3).get(), 6));
    }

    @Test
    public void testGetSubscribedPlanException() {
        assertThrows(NotSubscribedException.class, () -> this.planService.getSubscribedPlans(new User("userTest", 47, 458798538, "up3m")));
    }

    @Test
    public void testGetFuturePlansException() {
        PlanRepository planRepository1 = new PlanRepositoryMap();
        PlanService planService1 = new PlanService(planRepository1, userRepository, activityRepository);
        planService1.create(new Plan("plan1", "salir de cañas", LocalDateTime.of(2023, 12, 14, 19, 0), "Madrid", -1), this.userRepository.read(1).get());
        assertThrows(NotFoundException.class, () -> planService1.getFuturePlans());
    }

    @Test
    public void testRatePlan() {
        User user1 = this.userRepository.read(1).get();
        User user2 = this.userRepository.read(2).get();
        Double rate1 = this.planService.ratePlan(user1, 6, 4.0);
        Double rate2 = this.planService.ratePlan(user2, 6, 2.4);
        assertEquals(4, rate1);
        assertEquals(2.4, rate2);
        assertTrue(this.planRepository.read(6).get().getRating().getValue().contains(rate1));
        assertTrue(this.planRepository.read(6).get().getRating().getValue().contains(rate2));
        assertTrue(this.planRepository.read(6).get().getUsersThatRated().contains(user1));
        assertTrue(this.planRepository.read(6).get().getUsersThatRated().contains(user2));
    }

    @Test
    public void testRatePlanException() {
        assertThrows(NotFoundException.class, () -> this.planService.ratePlan(this.userRepository.read(2).get(), 9, 2.0));
        assertThrows(NotSubscribedException.class, () -> this.planService.ratePlan(new User("userTest", 47, 458798538, "up3m"), 1, 3.2));
        assertThrows(OverlapException.class, () -> this.planService.ratePlan(this.userRepository.read(5).get(), 3, 4.0));
        assertThrows(InvalidAttributeException.class, () -> this.planService.ratePlan(this.userRepository.read(5).get(), 1, 7.0));
    }

    @Test
    public void testGetFuturePlansByRate() {
        Plan planToUse = new Plan("plantouse", "salir", LocalDateTime.of(2025, 12, 14, 19, 0), "Madrid", 11);
        planToUse.setPropietary(this.userRepository.read(4).get());
        this.planRepository.create(planToUse);
        Plan planToUse1 = new Plan("plantouse1", "entrar", LocalDateTime.of(2029, 12, 14, 19, 0), "Madrid", 11);
        planToUse1.setPropietary(this.userRepository.read(2).get());
        this.planRepository.create(planToUse1);
        this.planService = new PlanService(this.planRepository, this.userRepository, this.activityRepository);
        List<Plan> planList = this.planService.getFuturePlansByRate();
        for (Plan plan : planList)
            System.out.println(plan.getName());
        assertEquals("plan7", planList.get(0).getName());
    }

    @Test
    public void testGetFuturePlansByDateException() {
        PlanRepository planRepository1 = new PlanRepositoryMap();
        PlanService planService1 = new PlanService(planRepository1, userRepository, activityRepository);
        planService1.create(new Plan("plan1", "salir de cañas", LocalDateTime.of(2023, 12, 14, 19, 0), "Madrid", -1), this.userRepository.read(1).get());
        assertThrows(NotFoundException.class, planService1::getFuturePlansByDate);
    }

    @Test
    public void testGetPastPlansByLoggedUserException() {
        User userPrueba = new User("user6", 87, 458967458, "upm");
        assertThrows(NotFoundException.class, () -> this.planService.getPastPlansByLoggedUser(userPrueba));
    }

    @Test
    public void testGetFuturePlansByUserNameExceptions() {
        User userPrueba = new User("user6", 87, 458967458, "upm");
        assertThrows(NotFoundException.class, () -> this.planService.getFuturePlansByUserName("Pedro"));
        assertThrows(NotFoundException.class, () -> this.planService.getFuturePlansByUserName(userPrueba.getName()));
    }

    @Test
    public void testGetMostRatedUserFuturePlans() {
        assertTrue(this.planService.getMostRatedUserFuturePlans().contains(this.planRepository.read(7).get()));
    }
}
