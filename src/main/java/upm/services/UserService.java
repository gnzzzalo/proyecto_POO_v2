package upm.services;

import upm.data.models.Plan;
import upm.data.models.User;
import upm.data.repositories.PlanRepository;
import upm.data.repositories.UserRepository;
import upm.services.exceptions.DuplicateException;
import upm.services.exceptions.NotFoundException;
import upm.services.exceptions.NotSubscribedException;
import upm.services.exceptions.OverlapException;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    public UserService(UserRepository userRepository, PlanRepository planRepository) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }

    public User findUserByName(String name) {
        return this.userRepository.findByName(name).orElseThrow(() -> new NotFoundException("El usuario no existe " + name));
    }

    public User create(User user) {
        if (this.userRepository.findByMobile(user.getMobile()).isPresent()) {
            throw new DuplicateException("El número de teléfono ya existe, y debe ser único: " + user.getMobile());
        } else if (this.userRepository.findByName(user.getName()).isPresent()) {
            throw new DuplicateException("El nombre ya existe, y debe ser único: " + user.getName());
        }
        return this.userRepository.create(user);
    }

    public Plan subscribeToPlan(User user, Integer planId) {
        if (this.planRepository.read(planId).isEmpty()) {
            throw new NotFoundException("El plan no existe" + planId);
        }
        if (this.planRepository.read(planId).get().getSubscribedUsers().contains(user)) {
            throw new DuplicateException("No te puedes suscribir dos veces al mismo plan");
        }
        if (this.checkCoincidencesSubscriptionPlan(user, this.planRepository.read(planId).get())) {
            throw new OverlapException("El plan al que te intentas suscribir se solapa con otros planes que te has suscrito");
        }
        List<Plan> futurePlans = this.planRepository.readFuturePlans().orElseThrow(() -> new NotFoundException("No existen planes futuros"));
        if (!futurePlans.contains(this.planRepository.read(planId).get())) {
            throw new NotFoundException("El plan al que te intentas suscribir no es un plan futuro");
        }
        this.planRepository.read(planId).get().subscribeUserToPlan(user);
        return this.planRepository.read(planId).get();
    }

    public Plan unsubscribeToPlan(User user, Integer planId) {
        if (this.planRepository.read(planId).isEmpty()) {
            throw new NotFoundException("El plan no existe " + planId);
        }
        List<Plan> subscribedPlans = this.planRepository.readSubscribedPlans(user).orElseThrow(() -> new NotFoundException("No te has suscrito a ningun plan"));
        if (!subscribedPlans.contains(this.planRepository.read(planId).get())) {
            throw new NotSubscribedException("No estas suscrito al plan del cual te intentas desuscribir");
        }
        List<Plan> futurePlans = this.planRepository.readFuturePlans().orElseThrow(() -> new NotFoundException("No existen planes futuros"));
        if (!futurePlans.contains(this.planRepository.read(planId).get())) {
            throw new NotFoundException("El plan del que te quieres quitar está en el pasado");
        }
        this.planRepository.read(planId).get().unsubscribeUserToPlan(user);
        return this.planRepository.read(planId).get();
    }

    private boolean checkCoincidencesSubscriptionPlan(User user, Plan planToCheck) {
        List<Plan> subscribedPlans = this.planRepository.readSubscribedPlans(user).orElse(new ArrayList<>());
        boolean isOverlap = false;
        for (Plan subscribedPlan : subscribedPlans) {
            if (!isOverlap)
                isOverlap = this.isOverlap(planToCheck, subscribedPlan);
        }
        return isOverlap;
    }

    private boolean isOverlap(Plan planToCheck, Plan subscribedPlan) {
        return planToCheck.getDateTime().isBefore(subscribedPlan.getEndDatePlan())
                && planToCheck.getEndDatePlan().isAfter(subscribedPlan.getDateTime());
    }
}
