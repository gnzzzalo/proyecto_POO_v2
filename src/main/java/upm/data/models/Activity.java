package upm.data.models;


import java.math.BigDecimal;
import java.util.Objects;

public abstract class Activity {
    private static final Integer SIN_LIMITE = -1;
    private final String name;
    private final String activityType;
    private String description;
    private Integer id;
    private Integer lengthMin;
    private BigDecimal cost;
    private Integer capacity;

    public Activity(String name, String activityType, String description, Integer lengthMin, BigDecimal cost, Integer capacity) {//poner en el CLI -1 en Integer aforo
        this.name = name;
        this.activityType = activityType;
        this.description = description;
        this.setLengthMin(lengthMin);
        this.setCost(cost);
        this.setCapacity(capacity);
    }

    public String getName() {
        return name;
    }


    public String getActivityType() {
        return activityType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLengthMin() {
        return lengthMin;
    }

    public void setLengthMin(Integer lengthMin) {
        if (lengthMin < 0)
            throw new InvalidAttributeException("El tiempo es en positivo: " + lengthMin);
        this.lengthMin = lengthMin;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        if (cost.compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidAttributeException("El coste es en positivo: " + cost);
        this.cost = cost;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        if (capacity < SIN_LIMITE)
            throw new InvalidAttributeException("El aforo es en positivo: " + capacity);
        this.capacity = capacity;
    }

    public abstract BigDecimal applyDiscount(Integer age);

    @Override
    public String toString() {
        return "Activity{" +
                "nombre='" + name + '\'' +
                ", tipoDeActividad='" + activityType + '\'' +
                ", descripcion='" + description + '\'' +
                ", duracionMin=" + lengthMin +
                ", coste=" + cost +
                ", aforo=" + capacity +
                '}';
    }

    @Override
    public boolean equals(Object activity) {
        return this == activity || activity != null && getClass() == activity.getClass() && (this.equals(((Activity) activity).id));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
