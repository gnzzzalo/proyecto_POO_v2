package upm.data.models.ActivitiesTypes;

import upm.data.models.Activity;
import upm.data.repositories.ActivitiyUsages;

import java.math.BigDecimal;

public class Cinema extends Activity {
    public Cinema(String name, String activityType, String description, Integer lengthMin, BigDecimal cost, Integer capacity) {
        super(name, activityType, description, lengthMin, cost, capacity);
    }

    @Override
    public BigDecimal applyDiscount(Integer age) {
        if (this.getActivityType().equalsIgnoreCase(ActivitiyUsages.CINEMA) && age <= 21)
            return this.getCost().multiply(BigDecimal.valueOf(0.50));
        return this.getCost();
    }
}
