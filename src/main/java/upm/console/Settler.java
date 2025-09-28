package upm.console;

import upm.data.models.ActivitiesTypes.Cinema;
import upm.data.models.ActivitiesTypes.Generic;
import upm.data.models.ActivitiesTypes.Theatre;
import upm.data.models.Activity;
import upm.data.models.Plan;
import upm.data.models.User;
import upm.data.repositories.ActivitiyUsages;
import upm.services.ActivityService;
import upm.services.PlanService;
import upm.services.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Settler {
    private final ActivityService activityService;
    private final PlanService planService;
    private final UserService userService;

    public Settler(ActivityService activityService, PlanService planService, UserService userService) {
        this.activityService = activityService;
        this.planService = planService;
        this.userService = userService;
    }

    public void run() {
        User user = this.userService.create(new User("user1", 17, 554758987, "48df"));
        User user1 = this.userService.create(new User("user2", 48, 154654286, "4548ttgf"));
        User user2 = this.userService.create(new User("user3", 16, 754987566, "123"));
        User user3 = this.userService.create(new User("user4", 27, 458766478, "qwertyuiop"));
        User user4 = this.userService.create(new User("user5", 87, 458798588, "upm"));


        Activity activity = this.activityService.create(new Cinema("Actividad1", ActivitiyUsages.CINEMA, "muy divertida", 110, BigDecimal.ONE, 5));
        Activity activity1 = this.activityService.create(new Cinema("Actividad2", ActivitiyUsages.CINEMA, "divertida", 20, BigDecimal.ZERO, -1));
        Activity activity2 = this.activityService.create(new Generic("Actividad3", ActivitiyUsages.GENERIC, "aburrida", 130, BigDecimal.valueOf(23.59), 6));
        Activity activity3 = this.activityService.create(new Theatre("Actividad4", ActivitiyUsages.THEATRE, "mala", 140, BigDecimal.TEN, 10));
        Activity activity4 = this.activityService.create(new Cinema("Actividad5", ActivitiyUsages.CINEMA, "esta bien", 140, BigDecimal.valueOf(100), -1));


        Plan plan = new Plan("plan1", "salir de cañas", LocalDateTime.of(2023, 12, 14, 19, 0), "Madrid", -1);
        plan.setPropietary(user);
        plan.getSubscribedUsers().add(user);
        plan.getSubscribedUsers().add(user4);
        plan.addActivity(activity, user);
        plan.addActivity(activity1, user);
        plan.addActivity(activity2, user);
        plan.addActivity(activity3, user);
        this.planService.create(plan, user);
        Plan plan1 = this.planService.create(new Plan("plan2", "jugar juegos", LocalDateTime.of(2025, 1, 27, 14, 0), "Madrid", 100), user);
        Plan plan2 = this.planService.create(new Plan("plan3", "ir de fiesta", LocalDateTime.of(2025, 3, 30, 22, 30), "Madrid", -1), user2);
        Plan plan3 = this.planService.create(new Plan("plan4", "ir al museo", LocalDateTime.of(2025, 2, 7, 9, 0), "Madrid", -1), user3);
        Plan plan4 = this.planService.create(new Plan("plan5", "comer sushi", LocalDateTime.of(2025, 2, 25, 13, 45), "Madrid", 2), user1);
        Plan plan5 = new Plan("plan6", "ir al teatro", LocalDateTime.of(2023, 10, 14, 19, 0), "Madrid", 43);
        plan5.getSubscribedUsers().add(user3);
        plan5.getSubscribedUsers().add(user2);
        plan5.getSubscribedUsers().add(user4);
        plan5.getSubscribedUsers().add(user1);
        this.planService.create(plan5, user3);
        this.planService.create(new Plan("plan7", "pasarla bien", LocalDateTime.of(2025, 1, 3, 12, 15, 41), "Parla", 8), user3);


        this.planService.addActivity(user, activity.getId(), plan1.getId());

        this.planService.addActivity(user2, activity.getId(), plan2.getId());
        this.planService.addActivity(user2, activity2.getId(), plan2.getId());
        this.planService.addActivity(user2, activity3.getId(), plan2.getId());

        this.planService.addActivity(user1, activity1.getId(), plan4.getId());
        this.planService.addActivity(user1, activity4.getId(), plan4.getId());


        this.userService.subscribeToPlan(user, plan1.getId());
        this.userService.subscribeToPlan(user1, plan1.getId());

        this.userService.subscribeToPlan(user, plan2.getId());
        this.userService.subscribeToPlan(user1, plan2.getId());
        this.userService.subscribeToPlan(user2, plan2.getId());
        this.userService.subscribeToPlan(user3, plan2.getId());
        this.userService.subscribeToPlan(user4, plan2.getId());

        this.userService.subscribeToPlan(user, plan3.getId());
        this.userService.subscribeToPlan(user3, plan3.getId());
        this.userService.subscribeToPlan(user4, plan3.getId());

        this.userService.subscribeToPlan(user, plan4.getId());


        this.planService.ratePlan(user, plan.getId(), 1.0);

        this.planService.ratePlan(user3, plan5.getId(), 3.0);
        this.planService.ratePlan(user2, plan5.getId(), 5.0);
        this.planService.ratePlan(user4, plan5.getId(), 2.0);
        this.planService.ratePlan(user1, plan5.getId(), 2.0);
    }
}
