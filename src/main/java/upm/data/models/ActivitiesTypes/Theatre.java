package upm.data.models.ActivitiesTypes;

import upm.data.models.Activity;
import upm.data.repositories.ActivitiyUsages;

import java.math.BigDecimal;

public class Theatre extends Activity {
    public Theatre(String name, String activityType, String description, Integer lengthMin, BigDecimal cost, Integer capacity) {
        super(name, activityType, description, lengthMin, cost, capacity);
    }

    @Override
    public BigDecimal applyDiscount(Integer age) {
        if ((this.getActivityType().equalsIgnoreCase(ActivitiyUsages.THEATRE) && age <= 25)) {
            return this.getCost().multiply(BigDecimal.valueOf(0.50));
        }
        if (this.getActivityType().equalsIgnoreCase(ActivitiyUsages.THEATRE) && age >= 65) {
            return this.getCost().multiply(BigDecimal.valueOf(0.30));
        }
        return this.getCost();
    }
}
