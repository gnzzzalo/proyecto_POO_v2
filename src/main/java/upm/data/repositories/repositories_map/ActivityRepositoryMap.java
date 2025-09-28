package upm.data.repositories.repositories_map;

import upm.data.models.Activity;
import upm.data.repositories.ActivityRepository;

public class ActivityRepositoryMap extends GeneralRepositoryMap<Activity> implements ActivityRepository {
    @Override
    public Integer getId(Activity entity) {
        return entity.getId();
    }

    @Override
    public void setId(Activity entity, Integer id) {
        entity.setId(id);
    }
}
