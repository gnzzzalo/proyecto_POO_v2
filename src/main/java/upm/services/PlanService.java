package upm.services;

import upm.data.models.Activity;
import upm.data.models.InvalidAttributeException;
import upm.data.models.Plan;
import upm.data.models.User;
import upm.data.repositories.ActivityRepository;
import upm.data.repositories.PlanRepository;
import upm.data.repositories.UserRepository;
import upm.services.exceptions.DuplicateException;
import upm.services.exceptions.NotFoundException;
import upm.services.exceptions.NotSubscribedException;
import upm.services.exceptions.OverlapException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlanService {
    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    public PlanService(PlanRepository planRepository, UserRepository userRepository, ActivityRepository activityRepository) {
        this.planRepository = planRepository;
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    public Plan create(Plan plan, User user) {
        plan.setPropietary(user);
        return this.planRepository.create(plan);
    }

    public Plan delete(Integer planId, User user) {
        Plan planDeleted = this.planRepository.read(planId).orElseThrow(() -> new NotFoundException("El plan que quieres eliminar no existe: " + planId));
        if (!planDeleted.getPropietary().equals(user)) {
            throw new InvalidAttributeException("No puedes eliminar el plan si no eres el propietario: " + user);
        }
        this.planRepository.deleteById(planId);
        return planDeleted;
    }

    public Plan addActivity(User user, Integer activityId, Integer planId) {
        Activity activity = this.activityRepository.read(activityId).orElseThrow(() -> new NotFoundException("El activityId no existe: " + activityId));
        Plan plan = this.planRepository.read(planId).orElseThrow(() -> new NotFoundException("El planId no existe: " + planId));
        List<Plan> futurePlans = this.planRepository.readFuturePlans().orElseThrow(() -> new NotFoundException("No existen planes futuros"));
        if (!futurePlans.contains(plan)) {
            throw new NotFoundException("No existe el plan futuro al que le quieres añadir la actividad");
        }
        plan.addActivity(activity, user);
        this.planRepository.update(plan);
        return plan;
    }

    public Integer getTotalTime(Integer planId) {
        Plan plan = this.planRepository.read(planId).orElseThrow(() -> new NotFoundException("El plan del que quieres ver el tiempo no existe: " + planId));
        return plan.getTotalTime();
    }

    public String getUserAndGenericPrice(User user, Integer planId) {
        if (this.planRepository.read(planId).isEmpty()) {
            throw new NotFoundException("El plan del que quieres ver el precio no existe: " + planId);
        }
        Plan planToShowPrice = this.planRepository.read(planId).get();
        if (!planToShowPrice.getSubscribedUsers().contains(user)) {
            throw new NotSubscribedException("No puedes ver el precio del plan si no estas suscrito a el: " + planId);
        }
        if (planToShowPrice.getDiscountedTotalPrice(user.getAge()).equals(planToShowPrice.getGenericTotalPrice())) {
            return planToShowPrice.getGenericTotalPrice().toString() + "€";
        } else
            return planToShowPrice.getDiscountedTotalPrice(user.getAge()).toString() + "€ (" + planToShowPrice.getGenericTotalPrice().toString() + "€ sin descuento)";
    }

    public List<Plan> getSubscribedPlans(User user) {
        return this.planRepository.readSubscribedPlans(user).orElseThrow(() -> new NotSubscribedException("No estás suscrito a ningún plan"));
    }

    public List<Plan> getFuturePlans() {
        return this.planRepository.readFuturePlans().orElseThrow(() -> new NotFoundException("No existen planes futuros"));
    }

    public Double ratePlan(User user, Integer planId, Double rate) {
        Plan plan = this.planRepository.read(planId).orElseThrow(() -> new NotFoundException("El plan no existe: " + planId));
        if (!plan.getSubscribedUsers().contains(user)) {
            throw new NotSubscribedException("No puedes valorar un plan si no has participado en el: " + user.getName());
        }
        if (plan.getDateTime().isAfter(LocalDateTime.now())) {
            throw new OverlapException("No puedes valorar un plan que todavía no ha finalizado: " + plan.getDateTime());
        }
        if (plan.getUsersThatRated().contains(user)) {
            throw new DuplicateException("No puedes valorar un plan que ya has valorado: " + user.getName());
        }
        if (rate < 1 || rate > 5) {
            throw new InvalidAttributeException("Valoración errónea, tiene que estar entre 1 y 5: " + rate);
        }

        plan.getRating().getValue().add(rate);
        plan.getUsersThatRated().add(user);
        this.planRepository.update(plan);
        return rate;
    }

    public List<Plan> getFuturePlansByRate() {
        List<Plan> allPlans = this.planRepository.findAll();
        if (allPlans.isEmpty()) {
            throw new NotFoundException("No existe ningun plan creado");
        }
        this.userRepository.updatingRatesOfPlans(allPlans);
        List<User> allUsers = this.userRepository.findAll();
        List<User> usersByRate = this.userList(allUsers);
        List<Plan> futurePlans = this.planRepository.readFuturePlans().orElseThrow(() -> new NotFoundException("No existen planes futuros"));
        List<Plan> futurePlansByRate = new ArrayList<>();
        for (User user : usersByRate) {
            for (Plan plan : futurePlans) {
                if (plan.getPropietary().equals(user)) {
                    futurePlansByRate.add(plan);
                }
            }
        }
        return futurePlansByRate;
    }

    private List<User> userList(List<User> allUsers) {
        List<User> users = new ArrayList<>();
        while (!allUsers.isEmpty()) {
            User user = this.userRepository.usersByRate(allUsers);
            users.add(user);
            allUsers.remove(user);
        }
        return users;
    }

    public List<Plan> getFuturePlansByDate() {
        return this.planRepository.readFuturePlansByDate().orElseThrow(() -> new NotFoundException("No existen planes a futuro"));
    }

    public List<Plan> getFuturePlansByUserName(String userName) {
        if (this.userRepository.findByName(userName).isEmpty()) {
            throw new NotFoundException("No se ha encontrado el usuario dado");
        }
        return this.planRepository.readFuturePlansByUserName(userName).orElseThrow(() -> new NotFoundException("No existen planes a futuro creados por este usuario"));
    }

    public List<Plan> getPastPlansByLoggedUser(User user) {
        return this.planRepository.readPastPlansByLoggedUser(user).orElseThrow(() -> new NotFoundException("No has creado ningún plan anteriormente"));
    }

    public List<Plan> getMostRatedUserFuturePlans() {
        List<Plan> futurePlans = this.planRepository.readFuturePlans().orElseThrow(() -> new NotFoundException("No hay planes futuros"));
        List<Plan> allPlans = this.planRepository.findAll();
        this.userRepository.updatingRatesOfPlans(allPlans);
        List<User> allUsers = this.userRepository.findAll();
        User mostRatedUser = this.userRepository.usersByRate(allUsers);
        List<Plan> mostRatedUserPlans = new ArrayList<>();
        for (Plan plan : futurePlans) {
            if (plan.getPropietary().getName().equals(mostRatedUser.getName())) {
                mostRatedUserPlans.add(plan);
            }
        }
        return mostRatedUserPlans;
    }
}
