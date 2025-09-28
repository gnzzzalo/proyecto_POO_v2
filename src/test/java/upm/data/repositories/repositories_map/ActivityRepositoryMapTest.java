package upm.data.repositories.repositories_map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import upm.AppSeeder;
import upm.data.models.Activity;
import upm.data.repositories.ActivityRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityRepositoryMapTest {
    private ActivityRepository activityRepository;

    @BeforeEach
    public void setUp() {
        AppSeeder appSeeder = new AppSeeder();
        appSeeder.seed();
        this.activityRepository = appSeeder.getActivityRepository();
    }

    @Test
    public void testCreateAndRead() {
        Optional<Activity> activity = this.activityRepository.read(1);
        assertTrue(activity.isPresent());
        assertEquals("Actividad1", activity.get().getName());
    }

    @Test
    public void testUpdate() {
        assertTrue(this.activityRepository.read(3).isPresent());
        Activity activity = this.activityRepository.read(3).get();
        activity.setDescription("ABUSIVO TIEMPO");
        this.activityRepository.update(activity);
        Optional<Activity> optionalActivity = this.activityRepository.read(3);
        assertTrue(optionalActivity.isPresent());
        assertEquals("ABUSIVO TIEMPO", optionalActivity.get().getDescription());
    }

    @Test
    public void testDeleteById() {
        assertTrue(this.activityRepository.read(3).isPresent());
        Activity activity = this.activityRepository.read(3).get();
        this.activityRepository.deleteById(activity.getId());
        assertFalse(this.activityRepository.read(activity.getId()).isPresent());
    }

}
