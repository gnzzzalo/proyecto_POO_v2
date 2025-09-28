package upm.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upm.AppSeeder;
import upm.data.models.Plan;
import upm.data.models.User;
import upm.data.repositories.PlanRepository;
import upm.data.repositories.UserRepository;
import upm.data.repositories.repositories_map.PlanRepositoryMap;
import upm.services.exceptions.DuplicateException;
import upm.services.exceptions.NotFoundException;
import upm.services.exceptions.NotSubscribedException;
import upm.services.exceptions.OverlapException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {
    private UserRepository userRepository;
    private PlanRepository planRepository;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        AppSeeder appSeeder = new AppSeeder();
        appSeeder.seed();
        this.userRepository = appSeeder.getUserRepository();
        this.planRepository = appSeeder.getPlanRepository();

        this.userService = new UserService(this.userRepository, this.planRepository);

    }

    @Test
    public void testCreateExceptions() {
        User userPrueba1 = new User("user1", 17, 589758987, "48df");
        User userPrueba2 = new User("user444", 17, 554758987, "48df");
        try {
            this.userService.create(userPrueba1);
        } catch (DuplicateException ex) {
            System.err.println(ex.getMessage());
        }
        try {
            this.userService.create(userPrueba2);
        } catch (DuplicateException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    public void testFindUserByNameAndException() {
        assertEquals(this.userService.findUserByName("user1"), this.userRepository.read(1).get());
        assertThrows(NotFoundException.class, () -> this.userService.findUserByName("Nombre de prueba"));
    }

    @Test
    public void testSubscribeToPlan() {
        Plan subscribedPlan = this.userService.subscribeToPlan(this.userRepository.read(5).get(), 4);
        assertEquals(subscribedPlan, this.planRepository.read(4).get());
    }

    @Test
    public void testSubscribeToPlanExceptions() {
        Plan plan = new Plan("planPrueba", "cosas", LocalDateTime.of(2024, 1, 27, 15, 0), "Madrid", 100);
        PlanRepository planRepository1 = new PlanRepositoryMap();
        planRepository1.create(new Plan("plan1", "salir de cañas", LocalDateTime.of(2023, 12, 14, 19, 0), "Madrid", -1));
        UserService userService1 = new UserService(userRepository, planRepository1);
        plan.setPropietary(this.userRepository.read(1).get());
        this.planRepository.create(plan);
        try {
            this.userService.subscribeToPlan(this.userRepository.read(5).get(), 8);
        } catch (NotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        assertThrows(DuplicateException.class, () -> this.userService.subscribeToPlan(this.userRepository.read(5).get(), 2));
        assertThrows(OverlapException.class, () -> this.userService.subscribeToPlan(this.userRepository.read(5).get(), 7));
        try {
            userService1.subscribeToPlan(this.userRepository.read(5).get(), 1);
        } catch (NotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        try {
            this.userService.subscribeToPlan(this.userRepository.read(5).get(), 6);
        } catch (NotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    public void testUnsubscribeToPlan() {
        Plan unsubscribedPlan = this.userService.unsubscribeToPlan(this.userRepository.read(5).get(), 2);
        assertEquals(unsubscribedPlan, this.planRepository.read(2).get());
    }

    @Test
    public void testUnsubscribeToPlanExceptions() {
        PlanRepository planRepository1 = new PlanRepositoryMap();
        planRepository1.create(new Plan("plan1", "salir de cañas", LocalDateTime.of(2023, 12, 14, 19, 0), "Madrid", -1));
        UserService userService1 = new UserService(userRepository, planRepository1);
        try {
            this.userService.unsubscribeToPlan(this.userRepository.read(5).get(), 8);
        } catch (NotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        assertThrows(NotSubscribedException.class, () -> this.userService.unsubscribeToPlan(this.userRepository.read(4).get(), 2));
        try {
            userService1.unsubscribeToPlan(this.userRepository.read(5).get(), 1);
        } catch (NotFoundException ex) {
            System.err.println(ex.getMessage());
        }

        planRepository1.read(1).get().getSubscribedUsers().add(this.userRepository.read(5).get());
        userService1 = new UserService(userRepository, planRepository1);

        try {
            userService1.unsubscribeToPlan(this.userRepository.read(5).get(), 1);
        } catch (NotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        try {
            this.userService.unsubscribeToPlan(this.userRepository.read(5).get(), 1);
        } catch (NotFoundException ex) {
            System.err.println(ex.getMessage());
        }

    }


}
