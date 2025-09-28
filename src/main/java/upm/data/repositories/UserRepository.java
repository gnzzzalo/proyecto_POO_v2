package upm.data.repositories;

import upm.data.models.Plan;
import upm.data.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends GeneralRepository<User> {
    Optional<User> findByMobile(Integer mobile);

    Optional<User> findByName(String name);

    void updatingRatesOfPlans(List<Plan> plans);

    User usersByRate(List<User> plans);

}
