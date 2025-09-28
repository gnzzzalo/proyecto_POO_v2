package upm.data.repositories.repositories_map;

import upm.data.models.Plan;
import upm.data.models.User;
import upm.data.repositories.PlanRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlanRepositoryMap extends GeneralRepositoryMap<Plan> implements PlanRepository {
    @Override
    public Integer getId(Plan entity) {
        return entity.getId();
    }

    @Override
    public void setId(Plan entity, Integer id) {
        entity.setId(id);
    }

    @Override
    public Optional<List<Plan>> readFuturePlans() {
        List<Plan> allPlans = this.findAll();
        List<Plan> futurePlans = new ArrayList<>();
        for (Plan plan : allPlans) {
            if (plan.getDateTime().isAfter(LocalDateTime.now())) {
                futurePlans.add(plan);
            }
        }
        if (futurePlans.isEmpty())
            return Optional.empty();
        else
            return Optional.of(futurePlans);
    }

    @Override
    public Optional<List<Plan>> readFuturePlansByDate() {
        if (this.readFuturePlans().isEmpty())
            return Optional.empty();
        List<Plan> futurePlans = this.readFuturePlans().get();
        List<Plan> plansByDate = new ArrayList<>();
        while (!futurePlans.isEmpty()) {
            LocalDateTime earlierDateTime = LocalDateTime.MAX;
            Plan earlierPlan = null;
            for (Plan plan : futurePlans) {
                if (plan.getDateTime().isBefore(earlierDateTime)) {
                    earlierPlan = plan;
                    earlierDateTime = plan.getDateTime();
                }
            }
            plansByDate.add(earlierPlan);
            futurePlans.remove(earlierPlan);
        }
        return Optional.of(plansByDate);
    }

    @Override
    public Optional<List<Plan>> readSubscribedPlans(User user) {
        List<Plan> plans = this.findAll();
        List<Plan> subscribedPlans = new ArrayList<>();
        for (Plan plan : plans) {
            if (plan.getSubscribedUsers().contains(user)) {
                subscribedPlans.add(plan);
            }
        }
        if (subscribedPlans.isEmpty())
            return Optional.empty();
        else
            return Optional.of(subscribedPlans);
    }

    @Override
    public Optional<List<Plan>> readFuturePlansByUserName(String userName) {
        if (this.readFuturePlans().isEmpty()) {
            return Optional.empty();
        }
        List<Plan> userPlans = new ArrayList<>();
        List<Plan> futurePlans = this.readFuturePlans().get();
        for (Plan plan : futurePlans) {
            if (plan.getPropietary().getName().equals(userName)) {
                userPlans.add(plan);
            }
        }
        return Optional.of(userPlans);
    }

    @Override
    public Optional<List<Plan>> readPastPlansByLoggedUser(User user) {
        List<Plan> allPlans = this.findAll();
        List<Plan> pastUserPlans = new ArrayList<>();
        for (Plan plan : allPlans) {
            if (plan.getPropietary().equals(user) && plan.getDateTime().isBefore(LocalDateTime.now())) {
                pastUserPlans.add(plan);
            }
        }
        if (pastUserPlans.isEmpty())
            return Optional.empty();
        else
            return Optional.of(pastUserPlans);
    }
}
