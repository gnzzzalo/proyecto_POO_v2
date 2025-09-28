package upm.data.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Valuation {
    private List<Double> value;

    public Valuation() {
        this.value = new ArrayList<>();
    }

    public List<Double> getValue() {
        return value;
    }

    public void setValue(List<Double> value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    public double getAverageRating() {
        double average = 0;
        if (!this.isEmpty()) {
            for (Double rating : this.value) {
                average += rating;
            }
            average = average / this.value.size();
        }
        return new BigDecimal(average).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
