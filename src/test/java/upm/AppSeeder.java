package upm;

import upm.data.models.ActivitiesTypes.Cinema;
import upm.data.models.ActivitiesTypes.Generic;
import upm.data.models.ActivitiesTypes.Theatre;
import upm.data.models.Activity;
import upm.data.models.Plan;
import upm.data.models.User;
import upm.data.repositories.ActivitiyUsages;
import upm.data.repositories.ActivityRepository;
import upm.data.repositories.PlanRepository;
import upm.data.repositories.UserRepository;
import upm.data.repositories.repositories_map.ActivityRepositoryMap;
import upm.data.repositories.repositories_map.PlanRepositoryMap;
import upm.data.repositories.repositories_map.UserRepositoryMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AppSeeder {
    private final ActivityRepository activityRepository;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    public AppSeeder() {
        this.activityRepository = new ActivityRepositoryMap();
        this.planRepository = new PlanRepositoryMap();
        this.userRepository = new UserRepositoryMap();
    }

    public void seed() {
        Activity[] activities = {
                new Cinema("Actividad1", ActivitiyUsages.CINEMA, "muy divertida", 110, BigDecimal.ONE, 1),
                new Cinema("Actividad2", ActivitiyUsages.CINEMA, "divertida", 20, BigDecimal.ZERO, -1),
                new Generic("Actividad3", ActivitiyUsages.GENERIC, "aburrida", 130, BigDecimal.valueOf(23.59), 2),
                new Theatre("Actividad4", ActivitiyUsages.THEATRE, "mala", 140, BigDecimal.TEN, 3),
                new Cinema("Actividad5", ActivitiyUsages.CINEMA, "esta bien", 140, BigDecimal.valueOf(100), -1)
        };
        for (Activity activity : activities) {
            this.activityRepository.create(activity);
        }
        User[] users = {
                new User("user1", 17, 554758987, "48df"),
                new User("user2", 48, 154654286, "4548ttgf"),
                new User("user3", 16, 754987566, "123"),
                new User("user4", 27, 458766478, "qwertyuiop"),
                new User("user5", 87, 458798588, "upm")
        };


        Plan[] plans = {
                new Plan("plan1", "salir de cañas", LocalDateTime.of(2023, 12, 14, 19, 0), "Madrid", -1),
                new Plan("plan2", "jugar juegos", LocalDateTime.of(2024, 1, 27, 14, 0), "Madrid", 100),
                new Plan("plan3", "ir de fiesta", LocalDateTime.of(2024, 3, 30, 22, 30), "Madrid", -1),
                new Plan("plan4", "ir al museo", LocalDateTime.of(2024, 2, 7, 9, 0), "Madrid", -1),
                new Plan("plan5", "comer sushi", LocalDateTime.of(2024, 2, 25, 13, 45), "Madrid", 2),
                new Plan("plan6", "ir al teatro", LocalDateTime.of(2023, 10, 14, 19, 0), "Madrid", 43),
                new Plan("plan7", "ir al teatro", LocalDateTime.of(2027, 10, 14, 19, 0), "Madrid", 43)
        };
        plans[0].setPropietary(users[1]);
        plans[1].setPropietary(users[0]);
        plans[2].setPropietary(users[2]);
        plans[3].setPropietary(users[2]);
        plans[4].setPropietary(users[4]);
        plans[5].setPropietary(users[3]);
        plans[6].setPropietary(users[3]);

        users[1].getRating().getValue().add(2.0);
        users[1].getRating().getValue().add(4.0);
        users[1].getRating().getValue().add(5.0);
        users[1].getRating().getValue().add(4.0);
        users[1].getRating().getValue().add(1.0);
        users[3].getRating().getValue().add(5.0);
        users[3].getRating().getValue().add(4.0);
        users[3].getRating().getValue().add(5.0);

        plans[0].getRating().getValue().add(2.0);
        plans[0].getRating().getValue().add(4.0);
        plans[0].getRating().getValue().add(5.0);
        plans[0].getRating().getValue().add(4.0);
        plans[0].getRating().getValue().add(1.0);
        plans[5].getRating().getValue().add(5.0);
        plans[5].getRating().getValue().add(4.0);
        plans[5].getRating().getValue().add(5.0);

        plans[0].getSubscribedUsers().add(users[4]);
        plans[1].getSubscribedUsers().add(users[4]);
        plans[2].getSubscribedUsers().add(users[4]);
        plans[1].getSubscribedUsers().add(users[1]);
        plans[2].getSubscribedUsers().add(users[1]);
        plans[1].getSubscribedUsers().add(users[2]);
        plans[4].getSubscribedUsers().add(users[3]);
        plans[5].getSubscribedUsers().add(users[0]);
        plans[5].getSubscribedUsers().add(users[1]);

        plans[0].addActivity(activities[0], users[1]);
        plans[0].addActivity(activities[2], users[1]);
        plans[0].addActivity(activities[3], users[1]);
        plans[1].addActivity(activities[4], users[0]);
        plans[1].addActivity(activities[1], users[0]);
        plans[1].addActivity(activities[3], users[0]);
        plans[4].addActivity(activities[3], users[4]);
        plans[2].addActivity(activities[3], users[2]);
        plans[5].addActivity(activities[3], users[3]);
        plans[5].addActivity(activities[1], users[3]);
        plans[5].addActivity(activities[2], users[3]);

        for (User user : users) {
            this.userRepository.create(user);
        }
        for (Plan plan : plans) {
            this.planRepository.create(plan);
        }
    }


    public ActivityRepository getActivityRepository() {
        return this.activityRepository;
    }

    public PlanRepository getPlanRepository() {
        return this.planRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
