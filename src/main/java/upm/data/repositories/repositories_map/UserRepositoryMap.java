package upm.data.repositories.repositories_map;

import upm.data.models.Plan;
import upm.data.models.User;
import upm.data.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryMap extends GeneralRepositoryMap<User> implements UserRepository {

    @Override
    public Integer getId(User entity) {
        return entity.getId();
    }

    @Override
    public void setId(User entity, Integer id) {
        entity.setId(id);
    }

    @Override
    public Optional<User> findByMobile(Integer mobile) {
        for (User user : this.findAll()) {
            if (user.getMobile().equals(mobile)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByName(String name) {
        for (User user : this.findAll())
            if (user.getName().equals(name)) {
                return Optional.of(user);
            }
        return Optional.empty();
    }

    @Override
    public void updatingRatesOfPlans(List<Plan> plans) {
        User mostRatedUser = null;
        for (User user : this.findAll()) {
            List<Double> doubleList = new ArrayList<>();
            for (Plan plan : plans) {
                if (plan.getPropietary().getName().equals(user.getName()) && !plan.getRating().isEmpty()) {
                    doubleList.add(plan.getAverageRating());
                }
            }
            user.getRating().setValue(doubleList);
            this.update(user);
        }
    }

    @Override
    public User usersByRate(List<User> users) {
        User userfin = null;
        double rating = 0;
        for (User user : users) {
            if (rating <= user.getAverageRating()) {
                rating = user.getAverageRating();
                userfin = user;
            }
        }
        return userfin;
    }


}