package upm.data.repositories.repositories_map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upm.AppSeeder;
import upm.data.models.Plan;
import upm.data.models.User;
import upm.data.repositories.PlanRepository;
import upm.data.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PlanRepositoryMapTest {
    private PlanRepository planRepository;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        AppSeeder appSeeder = new AppSeeder();
        appSeeder.seed();
        this.planRepository = appSeeder.getPlanRepository();
        this.userRepository = appSeeder.getUserRepository();
    }

    @Test
    public void testCreateAndRead() {
        Optional<Plan> Plan = this.planRepository.read(1);
        assertTrue(Plan.isPresent());
        assertEquals("plan1", Plan.get().getName());
    }

    @Test
    public void testUpdate() {
        Plan Plan = this.planRepository.read(3).get();
        Plan.setDescription("PROBANDING");
        this.planRepository.update(Plan);
        Optional<Plan> optionalPlan = this.planRepository.read(3);
        assertTrue(optionalPlan.isPresent());
        assertEquals("PROBANDING", optionalPlan.get().getDescription());
    }

    @Test
    public void testDeleteById() {
        Plan Plan = this.planRepository.read(3).get();
        this.planRepository.deleteById(Plan.getId());
        assertFalse(this.planRepository.read(Plan.getId()).isPresent());
    }

    @Test
    public void testReadFuturePlans() {
        assertFalse(this.planRepository.readFuturePlans().isEmpty());
        LocalDateTime time = LocalDateTime.of(2024, 3, 30, 22, 30);
        assertEquals(this.planRepository.readFuturePlans().get().get(1).getDateTime(), time);
    }

    @Test
    public void testReadFuturePlansByDate() {
        List<Plan> plansByDate = this.planRepository.readFuturePlansByDate().get();
        assertFalse(plansByDate.isEmpty());
        assertEquals(this.planRepository.read(4).get(), plansByDate.get(1));
        assertEquals(4, plansByDate.size());
    }

    @Test
    public void testReadSubscribedPlans() {
        User user1 = this.userRepository.read(2).get();
        User user2 = this.userRepository.read(3).get();
        User user3 = this.userRepository.read(5).get();
        Plan plan1 = this.planRepository.read(1).get();
        Plan plan2 = this.planRepository.read(2).get();
        Plan plan3 = this.planRepository.read(3).get();
        assertTrue(this.planRepository.readSubscribedPlans(user1).get().contains(plan2) && this.planRepository.readSubscribedPlans(user1).get().contains(plan3));
        assertTrue(this.planRepository.readSubscribedPlans(user2).get().contains(plan2));
        assertTrue(this.planRepository.readSubscribedPlans(user3).get().contains(plan1) && this.planRepository.readSubscribedPlans(user3).get().contains(plan2) && this.planRepository.readSubscribedPlans(user3).get().contains(plan3));
    }

    @Test
    public void testReadFuturePlansByUserName() {
        assertEquals(2, this.planRepository.readFuturePlansByUserName("user3").get().size());
        assertEquals(1, this.planRepository.readFuturePlansByUserName("user4").get().size());
        assertEquals(0, this.planRepository.readFuturePlansByUserName("us").orElse(new ArrayList<>()).size());
    }

    @Test
    public void testReadPastPlansByLoggedUser() {
        assertEquals(1, this.planRepository.readPastPlansByLoggedUser(this.userRepository.read(4).get()).get().size());
        Plan plan = new Plan("plantouse", "salir de cañas", LocalDateTime.of(2023, 12, 14, 19, 0), "Madrid", -1);
        plan.setPropietary(this.userRepository.read(4).get());
        this.planRepository.create(plan);
        assertEquals(2, this.planRepository.readPastPlansByLoggedUser(this.userRepository.read(4).get()).get().size());
        assertEquals(0, this.planRepository.readPastPlansByLoggedUser(this.userRepository.read(3).get()).orElse(new ArrayList<>()).size());
    }


}
