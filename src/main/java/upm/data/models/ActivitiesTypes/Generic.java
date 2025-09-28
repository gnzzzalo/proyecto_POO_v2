package upm.data.models.ActivitiesTypes;

import upm.data.models.Activity;

import java.math.BigDecimal;

public class Generic extends Activity {
    public Generic(String name, String activityType, String description, Integer lengthMin, BigDecimal cost, Integer capacity) {
        super(name, activityType, description, lengthMin, cost, capacity);
    }

    @Override
    public BigDecimal applyDiscount(Integer age) {
        return getCost();
    }
}
