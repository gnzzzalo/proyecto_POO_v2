package upm.services;

import upm.data.models.Activity;
import upm.data.repositories.ActivityRepository;

public class ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Activity create(Activity activity) {
        return this.activityRepository.create(activity);
    }
}
