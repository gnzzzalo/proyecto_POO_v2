package upm.data.repositories;


import upm.data.models.Plan;
import upm.data.models.User;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends GeneralRepository<Plan> {

    Optional<List<Plan>> readFuturePlans();

    Optional<List<Plan>> readFuturePlansByDate();

    Optional<List<Plan>> readSubscribedPlans(User user);

    Optional<List<Plan>> readFuturePlansByUserName(String userName);

    Optional<List<Plan>> readPastPlansByLoggedUser(User userName);


}
