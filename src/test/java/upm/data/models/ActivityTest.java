package upm.data.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upm.AppSeeder;
import upm.data.repositories.ActivityRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActivityTest {
    private ActivityRepository activityRepository;

    @BeforeEach
    public void setUp() {
        AppSeeder appSeeder = new AppSeeder();
        appSeeder.seed();
        this.activityRepository = appSeeder.getActivityRepository();
    }

    @Test
    public void testApplyDiscount() {
        assertTrue(activityRepository.read(1).isPresent());
        assertTrue(activityRepository.read(3).isPresent());
        assertTrue(activityRepository.read(4).isPresent());
        Activity activityCine = activityRepository.read(1).get();
        Activity activityGenerico = activityRepository.read(3).get();
        Activity activityTeatro = activityRepository.read(4).get();
        assertEquals(activityCine.getCost().multiply(BigDecimal.valueOf(1)), activityCine.applyDiscount(65));
        assertEquals(activityCine.getCost().multiply(BigDecimal.valueOf(0.5)), activityCine.applyDiscount(21));
        assertEquals(activityCine.getCost().multiply(BigDecimal.valueOf(1)), activityCine.applyDiscount(22));
        assertEquals(activityCine.getCost().multiply(BigDecimal.valueOf(1)), activityCine.applyDiscount(40));

        assertEquals(activityGenerico.getCost(), activityGenerico.applyDiscount(65));
        assertEquals(activityGenerico.getCost(), activityGenerico.applyDiscount(21));
        assertEquals(activityGenerico.getCost(), activityGenerico.applyDiscount(22));
        assertEquals(activityGenerico.getCost(), activityGenerico.applyDiscount(40));

        assertEquals(activityTeatro.getCost().multiply(BigDecimal.valueOf(0.3)), activityTeatro.applyDiscount(65));
        assertEquals(activityTeatro.getCost().multiply(BigDecimal.valueOf(0.5)), activityTeatro.applyDiscount(21));
        assertEquals(activityTeatro.getCost().multiply(BigDecimal.valueOf(0.5)), activityTeatro.applyDiscount(22));
        assertEquals(activityTeatro.getCost().multiply(BigDecimal.valueOf(1)), activityTeatro.applyDiscount(40));

    }
}
